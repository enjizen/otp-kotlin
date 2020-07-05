package com.tua.wanchalerm.otp.client.request

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailRequest(
        //@field:JsonProperty("email_to", required=true)
        @get:JsonProperty("email_to", required = true)
        val emailTo: List<String?>,
        @get:JsonProperty("email_cc", required = true)
        val emailCc: List<String>? = null,
        @get:JsonProperty("email_bcc", required = true)
        val emailBcc: List<String>? = null,
        @get:JsonProperty("subject", required = true)
        val subject: String,
        @get:JsonProperty("content", required = true)
        val content: Map<String, String>?
)