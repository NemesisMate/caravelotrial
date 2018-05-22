package com.nemesismate.caravelo.trial.persistence

import com.nemesismate.caravelo.trial.subscription.Subscription
import com.nemesismate.caravelo.trial.subscription.Subscription.Frequency
import org.springframework.data.repository.CrudRepository

/**
 * @author NemesisMate
 */
interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    Iterable<Subscription> findAllByFrequency(Frequency frequency)

}
