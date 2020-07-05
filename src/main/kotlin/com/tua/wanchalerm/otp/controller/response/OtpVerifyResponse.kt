package com.tua.wanchalerm.otp.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

data class OtpVerifyResponse(
        @JsonProperty("ticket")
        val ticket: String
)