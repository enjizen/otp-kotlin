package com.tua.wanchalerm.otp.util

import org.apache.commons.lang3.RandomStringUtils
import java.security.SecureRandom

object GenerateUtil {
    fun generateAlphabeticString(count: Int) : String {
        return RandomStringUtils.randomAlphabetic(count);
    }

    fun generateOtp(count: Int) : String {
        var result = ""
        val rand = SecureRandom()
        for (i in 0 until count) {
            // 0 to 9
            result += rand.nextInt(10)
        }
        return result
    }
}