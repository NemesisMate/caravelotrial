package com.nemesismate.caravelo.trial.service

import com.nemesismate.caravelo.trial.subscription.Subscription
import com.nemesismate.caravelo.trial.subscription.SubscriptionRequest
import reactor.core.publisher.Mono

/**
 * Any implementation is in charge of handle all the subscriptions logic, working too as an abstraction layer between
 * the persistence of the information subscriptions and the rest of the program.
 *
 * @see Subscription
 * @see com.nemesismate.caravelo.trial.subscription.sender.SubscriptionSender
 * @see com.nemesismate.caravelo.trial.configuration.SubscriptionSchedule
 *
 * @author NemesisMate
 */
interface SubscriptionService {

    /**
     * Persists a subscription
     *
     * @param subscriptionRequest the request to be persisted as subscription
     * @return the created subscription
     *
     * @see Subscription
     * @see SubscriptionRequest
     */
    Mono<Subscription> createSubscription(SubscriptionRequest subscriptionRequest)

    /**
     * Sends all the subscriptions matching the given {@code frequency}
     *
     * @param frequency the frequency to filter the subscriptions with. All persisted subscriptions matching
     * this frequency will be sent.
     *
     * @see com.nemesismate.caravelo.trial.subscription.sender.SubscriptionSender
     */
    void sendSubscriptions(Subscription.Frequency frequency)

}