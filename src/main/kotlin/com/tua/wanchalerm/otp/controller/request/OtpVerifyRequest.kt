package com.tua.wanchalerm.otp.controller.request

import com.fasterxml.jackson.annotation.JsonProperty

data class OtpVerifyRequest (
        @JsonProperty("ref_id")
        val refId: String,

        @JsonProperty("send_to")
        val sendTo: String,

        @JsonProperty("otp")
        val otp: String
)