package com.nemesismate.caravelo.trial.provider

import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Implementation of each executor used by the <b>ProviderExecutorManager</b>.
 *
 * @see ProviderExecutorManager
 *
 * @author NemesisMate
 */
interface ProviderExecutor {

    Flux<Survey> getSurveys(SurveyConditions conditions, InformationProvider provider)

    Mono<Survey> getSurvey(Long surveyId, InformationProvider provider)

    /**
     * Determines if the executor must be used for the given {@code provider}.
     *
     * @param provider the provider
     * @return true if the executor must be used for the given {@code provider}, false otherwise
     */
    boolean executes(InformationProvider provider)

}
