package com.tua.wanchalerm.otp.client

import com.tua.wanchalerm.otp.client.request.EmailRequest
import com.tua.wanchalerm.otp.config.HttpClientConfiguration
import com.tua.wanchalerm.otp.controller.response.GeneralResponse
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class NotificationClient {

    private val logger = LogManager.getLogger(this.javaClass)

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var httpClientConfiguration: HttpClientConfiguration

    fun sendEmailOtp(refId: String?, otp: String?, email: String?) : ResponseEntity<GeneralResponse<*>?> {
        logger.info("======= Start sent otp to email =========")
        val url = "/notification/v1/email?template=OTP"

        val requestHeader = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val content =  hashMapOf<String, String>().apply {
            refId?.let { this["ref_id"] = it }
            otp?.let {  this["otp"] = it }
        }

        val request = EmailRequest(emailTo = arrayListOf(email), subject = "รหัสยืนยัน", content = content)

        val httpEntity = HttpEntity(request, requestHeader)

        val endpoint = httpClientConfiguration.otpHost+url

        val response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                httpEntity,
                object : ParameterizedTypeReference<GeneralResponse<*>?>() {}
        )

        logger.info("======= End sent otp to email =========")

        return response
    }

}