package com.tua.wanchalerm.otp.repository

import com.tua.wanchalerm.otp.model.redis.OtpEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@EnableRedisRepositories
interface OtpRepository : JpaRepository<OtpEntity, String> {
}