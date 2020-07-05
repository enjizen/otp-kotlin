package com.tua.wanchalerm.otp.service

import com.tua.wanchalerm.otp.client.NotificationClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailService {

    @Autowired
    private lateinit var notificationClient: NotificationClient

    @Async("taskExecutor")
    fun sentEmail(  emailTo: String,
                    content: Map<String, String>? = null) {

        val refId = content?.get("ref_id")
        val otp = content?.get("otp")

        notificationClient.sendEmailOtp( refId, otp, emailTo)

    }
}