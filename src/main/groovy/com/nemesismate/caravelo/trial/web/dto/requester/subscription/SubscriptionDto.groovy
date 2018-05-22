package com.nemesismate.caravelo.trial.web.dto.requester.subscription

import com.nemesismate.caravelo.trial.web.dto.requester.ProviderDto
import com.nemesismate.caravelo.trial.web.dto.requester.RequesterDto
import com.nemesismate.caravelo.trial.web.dto.requester.survey.SurveyConditionsDto

/**
 * @author NemesisMate
 */
class SubscriptionDto {

    enum Frequency {
        MINUTELY, DAILY, WEEKLY
    }

    enum Channel {
        POSTAL, MAIL, API, FTP, LOG
    }

    Long id

    RequesterDto requester

    List<ProviderDto> providers

    Frequency frequency

    Set<Channel> channels

    SurveyConditionsDto conditions

}
