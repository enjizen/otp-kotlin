package com.tua.wanchalerm.otp.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import kotlin.collections.ArrayList

@Configuration
class HttpClientConfiguration {
    @Value("\${rest.client.readtimeout}")
     val readTimeout: Int? = 0

    @Value("\${rest.client.maxperroute}")
     val maxPerRoute: Int? = 0

    @Value("\${rest.client.maxtotalconnection}")
     val maxTotalConnection: Int? = 0

    @Value("\${otp.host}")
     var otpHost: String? = null

    @Bean
    fun restTemplate(): RestTemplate? {
        val restTemplate = RestTemplate(getClientHttpRequestFactory()!!)
        restTemplate.messageConverters = getForwarderConverter()
        return restTemplate
    }

    @Bean
    fun httpHeaders(): HttpHeaders? {
        return HttpHeaders()
    }

    @Bean
    fun getClientHttpRequestFactory(): ClientHttpRequestFactory? {
        val factory = HttpComponentsClientHttpRequestFactory(getHttpClient()!!)
        factory.setConnectTimeout(readTimeout!!)
        factory.setReadTimeout(readTimeout!!)
        return factory
    }

    @Bean
    fun getHttpClient(): CloseableHttpClient? {
        val connectionManager = PoolingHttpClientConnectionManager()
        connectionManager.defaultMaxPerRoute = maxPerRoute!!
        connectionManager.maxTotal = maxTotalConnection!!
        return HttpClients.custom().setConnectionManager(connectionManager).build()
    }

    fun getForwarderConverter(): List<HttpMessageConverter<Any>> {
        val converterList: ArrayList<HttpMessageConverter<Any>> = ArrayList()
        val converter = MappingJackson2HttpMessageConverter()
        val objectMapper = ObjectMapper()
        converter.supportedMediaTypes = listOf(MediaType.ALL)
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
        objectMapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)
        objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS)
        converter.objectMapper = objectMapper
        converterList.add(converter)
        return converterList
    }

}