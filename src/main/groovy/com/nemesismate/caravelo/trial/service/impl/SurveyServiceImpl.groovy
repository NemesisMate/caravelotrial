package com.nemesismate.caravelo.trial.service.impl

import com.nemesismate.caravelo.trial.provider.ProviderExecutorManager
import com.nemesismate.caravelo.trial.service.ProviderService
import com.nemesismate.caravelo.trial.service.SurveyService
import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

import javax.inject.Inject

/**
 * @author NemesisMate
 */
@Service
class SurveyServiceImpl implements SurveyService {

    @Inject
    ProviderExecutorManager providerManager

    @Inject
    ProviderService providerService

    /**
     * {@inheritDoc}
     */
    @Override
    Flux<Survey> findSurveys(Iterable<String> providersIds, SurveyConditions surveyConditions) {
        providerService.findProviders(providersIds)
                // We expect IO operations, so the elastic scheduler is the recommended
                .parallel().runOn(Schedulers.elastic())

                // Then, we ask to the providers for the requested surveys
                .map({ providerManager.getSurveys(surveyConditions, it) })

                .flatMap({ it }).sequential()
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Mono<Survey> getSurvey(String providerId, Long surveyId) {
        providerService.findProvider(providerId)
            .map({ provider -> providerManager.getSurvey(surveyId, provider) })
            .flatMap({ it })
    }

}
