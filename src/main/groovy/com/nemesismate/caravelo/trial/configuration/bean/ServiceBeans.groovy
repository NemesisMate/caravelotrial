package com.nemesismate.caravelo.trial.configuration.bean

import com.nemesismate.caravelo.trial.provider.ProviderExecutor
import com.nemesismate.caravelo.trial.provider.ProviderExecutorManager
import com.nemesismate.caravelo.trial.provider.impl.ProviderExecutorRandomImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author NemesisMate
 */
@Configuration
class ServiceBeans {

    @Bean
    ProviderExecutor providerExecutor() {
        return new ProviderExecutorRandomImpl()
    }

    @Bean
    ProviderExecutorManager providerManager() {
        ProviderExecutorManager providerManager = new ProviderExecutorManager()
        providerManager.registerExecutor(providerExecutor())
        return providerManager
    }

}
