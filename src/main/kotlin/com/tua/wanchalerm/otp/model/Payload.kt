package com.tua.wanchalerm.otp.model

data class Payload (
        val deviceId: String? = null,
        val clientId: String? = null,
        val ipAddress: String? = null,
        val os: String? = null,
        val model: String? = null
)