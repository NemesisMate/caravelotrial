package com.nemesismate.caravelo.trial.requester

import com.nemesismate.caravelo.trial.subscription.Subscription

/**
 * The requester in the relationship: <em><b>requester <--- middleware ---> provider</b></em>
 *
 * @see com.nemesismate.caravelo.trial.provider.InformationProvider
 * @see Subscription
 *
 * @author NemesisMate
 */
interface InformationRequester {

    String getId()

    String getName()


    Iterable<Subscription> getSubscriptions()

    void addSubscription(Subscription subscription)

    void removeSubscription(Subscription subscription)

}
