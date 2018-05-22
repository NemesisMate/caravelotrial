package com.nemesismate.caravelo.trial.subscription

import com.nemesismate.caravelo.trial.survey.SurveyConditions

/**
 * @author NemesisMate
 */
class SubscriptionRequest {

    String requester

    Set<String> providers

    Subscription.Frequency frequency

    Set<Subscription.Channel> channels

    SurveyConditions survey


    @Override
    String toString() {
        """{"requester": "$requester", "providers"=$providers, "frequency"="$frequency", "channels"=$channels, "survey"=$survey}"""
    }
}
