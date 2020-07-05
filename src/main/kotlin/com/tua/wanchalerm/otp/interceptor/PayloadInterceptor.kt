package com.tua.wanchalerm.otp.interceptor

import com.tua.wanchalerm.otp.enums.PayloadConstant
import com.tua.wanchalerm.otp.model.Payload
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class PayloadInterceptor : HandlerInterceptorAdapter() {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        request.let {
            Payload(deviceId = it.getHeader(PayloadConstant.DEVICE_ID_KEY.value),
                    clientId = it.getHeader(PayloadConstant.CLIENT_ID_KEY.value)
            ).run {
                it.setAttribute(PayloadConstant.PAYLOAD_KEY.value, this)
            }
        }
        return true
    }
}