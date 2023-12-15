package com.oskarwiedeweg.cloudwork;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BitUtils {

    public boolean hasBit(Long bits, Long bit) {
        return (bits & bit) == bit;
    }

    public static Long addBit(Long bits, Long bit) {
        return bits | bit;
    }

    @Getter
    public enum SettingBits {
        TWO_FACTOR_AUTH(1);

        private final long bit;

        SettingBits(long bit) {
            this.bit = bit;
        }

    }

}
