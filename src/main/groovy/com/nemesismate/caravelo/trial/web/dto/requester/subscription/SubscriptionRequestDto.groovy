package com.nemesismate.caravelo.trial.web.dto.requester.subscription

import com.nemesismate.caravelo.trial.web.dto.requester.survey.SurveyConditionsDto

/**
 * @author NemesisMate
 */
class SubscriptionRequestDto {

    String requester


    Set<String> providers

    SubscriptionDto.Frequency frequency

    Set<SubscriptionDto.Channel> channels

    SurveyConditionsDto survey

    @Override
    String toString() {
        """{"requester": $requester, "providers"=$providers, "frequency"=$frequency, "channels"=$channels, "survey"=$survey}"""
    }

}
