package com.tua.wanchalerm.otp.service

import com.tua.wanchalerm.otp.model.redis.OtpEntity
import com.tua.wanchalerm.otp.repository.OtpRepository
import com.tua.wanchalerm.otp.util.GenerateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class OtpService {

    @Autowired
    private lateinit var otpRepository: OtpRepository

    fun createOtp(clientId: String?, deviceId: String?, sendTo: String?) : OtpEntity{
        val refId = GenerateUtil.generateAlphabeticString(4)
        val otp = GenerateUtil.generateOtp(6)

        OtpEntity().apply {
            this.refId = refId
            this.otp = otp
            this.clientId = clientId
            this.deviceId = deviceId
            this.sendTo = sendTo
        }.run {
            otpRepository.save(this)
            return this
        }
    }

    fun getOtp(refId: String) : Optional<OtpEntity>? {
        return otpRepository.findById(refId);
    }

    fun delete(refId: String) {
        getOtp(refId)?.ifPresent {
            otpRepository.delete(it)
        }
    }
}