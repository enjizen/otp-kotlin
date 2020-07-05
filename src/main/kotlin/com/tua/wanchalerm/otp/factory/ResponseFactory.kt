package com.tua.wanchalerm.otp.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.tua.wanchalerm.otp.constant.Response
import com.tua.wanchalerm.otp.controller.response.GeneralResponse
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ResponseFactory {
    private val logger = LogManager.getLogger()

    @Autowired
    private val objectMapper: ObjectMapper? = null

    fun success(value: Any? = null, clazz: Class<*>? = null) : ResponseEntity<*> {
        GeneralResponse<Any>().apply {
            code = Response.SUCCESS_CODE
            value?.let {
                data = clazz?.cast(value)
            }

        }.run {
            return ResponseEntity.ok(this)
        }
    }

    fun error(httpStatus: HttpStatus, errorCode: String) : ResponseEntity<*> {
        GeneralResponse<Any>().apply {
            code = errorCode
        }.run {
           return ResponseEntity(this, httpStatus)
        }
    }
}