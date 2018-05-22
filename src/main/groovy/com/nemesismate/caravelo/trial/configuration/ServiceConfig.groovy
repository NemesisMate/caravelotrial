package com.nemesismate.caravelo.trial.configuration

import com.nemesismate.caravelo.trial.service.SubscriptionService
import com.nemesismate.caravelo.trial.subscription.Subscription
import com.nemesismate.caravelo.trial.subscription.sender.LoggedSubscriptionSender
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct
import javax.inject.Inject

/**
 * @author NemesisMate
 */
@Configuration
@Slf4j
class ServiceConfig {

    @Inject
    SubscriptionService subscriptionService


    @PostConstruct
    void subscriptionServiceConfig() {
        log.info("Configuring the mappingService bean")

        subscriptionService.registerSender(Subscription.Channel.LOG, new LoggedSubscriptionSender())
    }


}
