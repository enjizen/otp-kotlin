package com.tua.wanchalerm.otp.controller.request

import com.fasterxml.jackson.annotation.JsonProperty

data class OtpCreateRequest (
        @JsonProperty("send_to")
        val sendTo: String
)