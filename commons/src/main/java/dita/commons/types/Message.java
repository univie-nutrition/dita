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
package dita.commons.types;

import java.io.Serializable;
import java.util.function.Consumer;

import org.jspecify.annotations.Nullable;

import org.jspecify.annotations.NonNull;

import dita.commons.format.FormatUtils;

public record Message(Message.@NonNull Severity severity, @NonNull String text)
implements Serializable {

    public static enum Severity {
        ERROR,
        WARNING,
        INFO,
        DEBUG;
    }

    // -- FACTORIES

    public static Message error(final @Nullable String text) {
        return new Message(Severity.ERROR, FormatUtils.safelyFormat(text));
    }
    public static Message error(final @Nullable String format, final Object ...args) {
        return error(FormatUtils.safelyFormat(format, args));
    }

    public static Message warn(final @Nullable String text) {
        return new Message(Severity.WARNING, FormatUtils.safelyFormat(text));
    }
    public static Message warn(final @Nullable String format, final Object ...args) {
        return warn(FormatUtils.safelyFormat(format, args));
    }

    public static Message info(final @Nullable String text) {
        return new Message(Severity.INFO, FormatUtils.safelyFormat(text));
    }
    public static Message info(final @Nullable String format, final Object ...args) {
        return info(FormatUtils.safelyFormat(format, args));
    }

    public static Message debug(final @Nullable String text) {
        return new Message(Severity.DEBUG, FormatUtils.safelyFormat(text));
    }
    public static Message debug(final @Nullable String format, final Object ...args) {
        return debug(FormatUtils.safelyFormat(format, args));
    }

    // -- UTILITY

    public static Consumer<Message> consumerWritingToSyserr() {
        return message->System.err.printf("[%s] %s%n", message.severity(), message.text());
    }

}
