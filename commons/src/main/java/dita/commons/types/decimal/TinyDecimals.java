/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.commons.types.decimal;

import java.math.BigDecimal;

import org.springframework.lang.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TinyDecimals {

    /**
     * <pre>
     * bit 56-63: 8 head bits encoding the exponent (unsigned ordinal)
     *      0: 0
     *      1: 3
     *      2: 6
     *      3: 9
     *      4: 12
     *      5: 15
     *      6: 18
     *      7: 21
     *      8: -3
     *      9: -6
     *      10: -9
     *      11: -12
     *      12: -15
     *      13: -18
     *      14: -21
     *      15: not a finite number
     * bit 55: if set then negative
     * bit 0-54: unsigned significand
     * </pre>
     */
    public long toLongExact(@Nullable final BigDecimal bigDecimal) {
        if(bigDecimal==null) return NAN;

        var signum = bigDecimal.signum();
        if(signum==0) return ZERO;

        var abs = bigDecimal.abs().stripTrailingZeros();
        int scale = abs.scale();

        return switch (scale) {
            case 4, 5, 6 -> encode(Head.EM6, signum==-1, abs.scaleByPowerOfTen(6).longValueExact());
            case 1, 2, 3 -> encode(Head.EM3, signum==-1, abs.scaleByPowerOfTen(3).longValueExact());
            case 0, -1, -2 -> encode(Head.E0, signum==-1, abs.longValueExact());
            case -3, -4, -5 -> encode(Head.E3, signum==-1, abs.scaleByPowerOfTen(-3).longValueExact());
            case -6, -7, -8 -> encode(Head.E6, signum==-1, abs.scaleByPowerOfTen(-6).longValueExact());
            default ->
                throw new IllegalArgumentException("scale no handled: " + scale);
        };
    }

    public BigDecimal toBigDecimal(final long data) {
        var head = Head.decode(data);
        if(head == Head.NAN) return null;

        boolean negative = (data & SIGN_MASK)!=0L;

        long absSignificand = SIGNIFICAND_MASK & data;
        var absBd = switch (head) {
            case E0 -> new BigDecimal(absSignificand);
            case E3 -> new BigDecimal(absSignificand).scaleByPowerOfTen(3);
            case EM3 -> new BigDecimal(absSignificand).scaleByPowerOfTen(-3);
            case E6 -> new BigDecimal(absSignificand).scaleByPowerOfTen(6);
            case EM6 -> new BigDecimal(absSignificand).scaleByPowerOfTen(-6);
            default -> throw new IllegalArgumentException("Unexpected head: " + head);
        };
        return negative
                ? absBd.negate()
                : absBd;
    }

    // -- HELPER

    private long HEAD_MASK = 0xff00_0000_0000_0000L;
    private long SIGNIFICAND_MASK = 0x007f_ffff_ffff_ffffL;
    private long SIGN_MASK = 0x0080_0000_0000_0000L;
    private long NAN = encode(Head.NAN, false, 0L);
    private long ZERO = encode(Head.E0, false, 0L);
    //private long ONE = encode(Head.E0, false, 1L);

    private enum Head {
        E0,//0
        E3,//1
        E6,//2
        E9,//3
        E12,//4
        E15,//5
        E18,//6
        E21,//7
        EM3,//8
        EM6,//9
        EM9,//10
        EM12,//11
        EM15,//12
        EM18,//13
        EM21,//14
        NAN;//15

        long headBits() {
            long bits = ((long) ordinal())<<56;
            if((HEAD_MASK & bits)!=bits) {
                throw new IllegalArgumentException("cannot fit 0x%s into 8bit header"
                        .formatted(Integer.toHexString(ordinal())));
            }
            return bits;
        }

        static Head decode(final long data) {
            long ordinal = (HEAD_MASK & data)>>56;
            return Head.values()[(int)ordinal];
        }
    }

    private long encode(final Head head, final boolean negative, final long absSignificand) {
        long significandBits = SIGNIFICAND_MASK & absSignificand;
        if(significandBits!=absSignificand) {
            throw new IllegalArgumentException("cannot fit 0x%s into 51bit significand"
                    .formatted(Long.toHexString(absSignificand)));
        }
        var abs = head.headBits() | significandBits;
        return negative
                ? abs | SIGN_MASK
                : abs;
    }

}
