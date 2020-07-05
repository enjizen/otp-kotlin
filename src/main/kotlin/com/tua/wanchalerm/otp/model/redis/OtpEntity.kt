package com.tua.wanchalerm.otp.model.redis

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("otp")
class OtpEntity {
    @Id
    var refId: String? = null
    var clientId: String? = null
    var deviceId: String? = null
    var otp: String? = null
    var sendTo: String? = null
}