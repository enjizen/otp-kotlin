package com.tua.wanchalerm.otp.controller

import com.tua.wanchalerm.otp.common.Base
import com.tua.wanchalerm.otp.constant.Response
import com.tua.wanchalerm.otp.controller.request.OtpCreateRequest
import com.tua.wanchalerm.otp.controller.request.OtpVerifyRequest
import com.tua.wanchalerm.otp.controller.response.OtpResponse
import com.tua.wanchalerm.otp.controller.response.OtpVerifyResponse
import com.tua.wanchalerm.otp.enums.Channel
import com.tua.wanchalerm.otp.factory.ResponseFactory
import com.tua.wanchalerm.otp.model.Payload
import com.tua.wanchalerm.otp.service.EmailService
import com.tua.wanchalerm.otp.service.OtpService
import com.tua.wanchalerm.otp.util.GenerateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OtpController : Base() {

    @Autowired
    private lateinit var otpService: OtpService

    @Autowired
    private lateinit var emailService: EmailService

    @Autowired
    private lateinit var responseFactory: ResponseFactory

    @PostMapping(value = ["/v1/create-otp"])
    fun createOtp(@RequestAttribute payload: Payload, @RequestParam(name = "channel") channel: Channel, @RequestBody request: OtpCreateRequest) : ResponseEntity<*> {
        log.info("========== Start create otp =======")


        if (request.sendTo.isBlank()) {
            log.info("Send To is blank")
            return  responseFactory.error(HttpStatus.BAD_REQUEST, Response.INVALID_REQUEST)
        }

        val otpEntity = otpService.createOtp(payload.clientId, payload.deviceId, request.sendTo)

        if (Channel.EMAIL == channel) {
            val content = HashMap<String, String>().apply {
                otpEntity.refId?.let { put("ref_id", it) }
                otpEntity.otp?.let { put("otp", it) }
            }
            emailService.sentEmail(emailTo = request.sendTo, content = content)
        }

        val otpResponse = otpEntity.refId?.let { OtpResponse(it) }
        log.info("========== End create otp =======")
        return responseFactory.success(otpResponse, OtpResponse::class.java)
    }

    @PostMapping(value = ["/v1/verify-otp"])
    fun verifyOtp(@RequestAttribute payload: Payload, @RequestBody request: OtpVerifyRequest) : ResponseEntity<*>? {
        log.info("========== Start verify otp =======")
        val otpEntityOptional = otpService.getOtp(request.refId)

        if (!otpEntityOptional?.isPresent!!){
            return responseFactory.error(HttpStatus.BAD_REQUEST, Response.INVALID_REQUEST)
        }

        if (otpEntityOptional.get().clientId != payload.clientId) {
            return responseFactory.error(HttpStatus.BAD_REQUEST, Response.INVALID_REQUEST)
        }

        if (otpEntityOptional.get().deviceId != payload.deviceId) {
            return responseFactory.error(HttpStatus.BAD_REQUEST, Response.INVALID_REQUEST)
        }

        if (otpEntityOptional.get().otp != request.otp) {
            return responseFactory.error(HttpStatus.BAD_REQUEST, Response.INVALID_REQUEST)
        }

        if (otpEntityOptional.get().sendTo != request.sendTo) {
            return responseFactory.error(HttpStatus.BAD_REQUEST, Response.INVALID_REQUEST)
        }

        otpService.delete(request.refId)

        val ticket = GenerateUtil.generateAlphabeticString(13)
        val responseEntity = responseFactory.success(OtpVerifyResponse(ticket), OtpVerifyResponse::class.java)

        log.info("========== End verify otp =======")
        return responseEntity
    }

}