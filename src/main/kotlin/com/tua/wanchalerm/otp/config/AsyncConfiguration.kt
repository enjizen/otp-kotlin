package com.tua.wanchalerm.otp.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
@EnableAsync
class AsyncConfiguration {

    private val log: Logger = LoggerFactory.getLogger(AsyncConfiguration::class.java)

    @Bean(name = ["taskExecutor"])
    fun taskExecutor(): Executor? {
        log.debug("Creating Async Task Executor")
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 2
        executor.maxPoolSize = 2
        executor.setQueueCapacity(100)
        executor.setThreadNamePrefix("CarThread-")
        executor.initialize()
        return executor
    }
}