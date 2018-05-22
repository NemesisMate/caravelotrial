package com.nemesismate.caravelo.trial.provider.impl

import com.nemesismate.caravelo.trial.provider.InformationProvider
import com.nemesismate.caravelo.trial.provider.ProviderExecutor
import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import org.modelmapper.ModelMapper
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import javax.inject.Inject

/**
 * Hm...
 *
 * @see com.nemesismate.caravelo.trial.provider.ProviderExecutorManager
 *
 * @author NemesisMate
 */
abstract class AbstractMappedProviderExecutor implements ProviderExecutor {

    @Inject
    ModelMapper modelMapper

    /**
     * {@inheritDoc}
     */
    @Override
    Flux<Survey> getSurveys(SurveyConditions conditions, InformationProvider provider) {
        getSurveysFromEndpoint(conditions, provider).map({ dto -> modelMapper.map(dto, Survey.class) })
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Mono<Survey> getSurvey(Long surveyId, InformationProvider provider) {
        return getSurveyFromEndpoint(surveyId, provider).map({ dto -> modelMapper.map(dto, Survey.class) })
    }

    /**
     * {@inheritDoc}
     */
    @Override
    boolean executes(InformationProvider provider) {
        return true
    }

    abstract Flux getSurveysFromEndpoint(SurveyConditions surveyConditions, InformationProvider provider)

    abstract Mono getSurveyFromEndpoint(Long surveyId, InformationProvider provider)

}
