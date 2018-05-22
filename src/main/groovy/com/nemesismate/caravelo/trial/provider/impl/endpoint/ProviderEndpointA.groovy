package com.nemesismate.caravelo.trial.provider.impl.endpoint

import com.nemesismate.caravelo.trial.ExceptionHelper
import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author NemesisMate
 */
class ProviderEndpointA implements ProviderEndpoint {

    /**
     * {@inheritDoc}
     */
    @Override
    Flux<Survey> getSurveys(SurveyConditions surveyConditions) {
        throw ExceptionHelper.unimplemented()
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Mono<Survey> getSurvey(Long surveyId) {
        throw ExceptionHelper.unimplemented()
    }
}
