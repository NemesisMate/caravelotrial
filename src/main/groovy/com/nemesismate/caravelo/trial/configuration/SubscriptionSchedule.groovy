package com.nemesismate.caravelo.trial.configuration

import com.nemesismate.caravelo.trial.service.SubscriptionService
import com.nemesismate.caravelo.trial.subscription.Subscription
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

import javax.inject.Inject

/**
 * @see Subscription
 * @see SubscriptionService
 *
 * @author NemesisMate
 */
@Configuration
class SubscriptionSchedule {

    @Inject
    SubscriptionService subscriptionService

    /**
     * Per minute (60.000 millis)
     */
    @Scheduled(fixedRate = 60000L)
    void runScheduleMinutely() {
        subscriptionService.sendSubscriptions(Subscription.Frequency.MINUTELY)
    }

    /**
     * Every day at 04:00
     */
    @Scheduled(cron = '0 0 4 * * *')
    void runScheduleDaily() {
        subscriptionService.sendSubscriptions(Subscription.Frequency.DAILY)
    }

    /**
     * Every Sunday at 00:00
     */
    @Scheduled(cron = '0 0 0 ? * SUN')
    void runScheduleWeekly() {
        subscriptionService.sendSubscriptions(Subscription.Frequency.WEEKLY)
    }

}
