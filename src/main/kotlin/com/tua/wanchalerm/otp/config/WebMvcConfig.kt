package com.tua.wanchalerm.otp.config

import com.tua.wanchalerm.otp.interceptor.PayloadInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    @Autowired
    private lateinit var payloadInterceptor: PayloadInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(payloadInterceptor).addPathPatterns("/**")
    }
}