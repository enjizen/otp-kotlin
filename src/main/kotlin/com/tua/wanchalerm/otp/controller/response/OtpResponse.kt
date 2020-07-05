package com.tua.wanchalerm.otp.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

data class OtpResponse (
    @JsonProperty("ref_id")
    val refId: String
)