package com.nemesismate.caravelo.trial.subscription.sender

import com.nemesismate.caravelo.trial.requester.InformationRequester
import com.nemesismate.caravelo.trial.survey.Survey

/**
 * @see com.nemesismate.caravelo.trial.subscription.Subscription
 *
 * @author NemesisMate
 */
interface SubscriptionSender {

    void send(Iterable<Survey> surveys, InformationRequester requester)

}
