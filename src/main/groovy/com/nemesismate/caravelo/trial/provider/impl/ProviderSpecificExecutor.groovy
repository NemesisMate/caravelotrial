package com.nemesismate.caravelo.trial.provider.impl

import com.nemesismate.caravelo.trial.provider.InformationProvider
import com.nemesismate.caravelo.trial.provider.ProviderExecutor
import com.nemesismate.caravelo.trial.provider.impl.endpoint.ProviderEndpoint
import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Executes a different <b>endpoint</b> logic for every provider <em>(identity-based executor)</em>.
 *
 * @see com.nemesismate.caravelo.trial.provider.ProviderExecutorManager
 *
 * @author NemesisMate
 */
class ProviderSpecificExecutor implements ProviderExecutor {

    Map<String, ProviderEndpoint> endpointMap

    /**
     * {@inheritDoc}
     */
    @Override
    Flux<Survey> getSurveys(SurveyConditions surveyConditions, InformationProvider provider) {
        endpointMap.get(provider.id).getSurveys(surveyConditions)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Mono<Survey> getSurvey(Long surveyId, InformationProvider provider) {
        endpointMap.get(provider.id).getSurvey(surveyId)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    boolean executes(InformationProvider provider) {
        endpointMap.containsKey(provider.id)
    }
}
