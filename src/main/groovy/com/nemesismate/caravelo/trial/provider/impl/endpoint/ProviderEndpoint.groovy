package com.nemesismate.caravelo.trial.provider.impl.endpoint

import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Provider-specific endpoint
 *
 * @see com.nemesismate.caravelo.trial.provider.impl.ProviderSpecificExecutor
 *
 * @author NemesisMate
 */
interface ProviderEndpoint {

    Flux<Survey> getSurveys(SurveyConditions surveyConditions)

    Mono<Survey> getSurvey(Long surveyId)

}