package com.nemesismate.caravelo.trial.web.dto.requester.survey

/**
 * @author NemesisMate
 */
class AvailableSurveysRequestDto {

    String requester

    Set<String> providers

    SurveyConditionsDto survey

    @Override
    String toString() {
        """{"requester": "$requester", "providers": $providers, "survey": $survey}"""
    }

}
