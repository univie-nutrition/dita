package dita.commons.format;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.causeway.commons.collections.Can;

class FormatUtilsTest {

    @Test
    void concat() {
        assertEquals("12", FormatUtils.concat("", "1", null, "2"));
    }

    @Test
    void cut() {
        assertEquals(Can.of("0", "01", "89", "9"), FormatUtils.cut(IntStream.of(
                0, 1,
                0, 2,
                8, 10,
                9, 10,
                // all invalid ...
                -1, 2,
                0, 99,
                3, 2,
                99, 99),
                "0123456789"));
    }
}
