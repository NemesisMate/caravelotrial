package com.nemesismate.caravelo.trial.provider.impl

import com.nemesismate.caravelo.trial.provider.InformationProvider
import com.nemesismate.caravelo.trial.provider.ProviderExecutor
import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import com.nemesismate.caravelo.trial.survey.condition.Condition
import groovy.util.logging.Slf4j
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Returns random data based on confuse criteria
 *
 * @see com.nemesismate.caravelo.trial.provider.ProviderExecutorManager
 *
 * @author NemesisMate
 */
@Slf4j
class ProviderExecutorRandomImpl implements ProviderExecutor {

    private Map<String, Collection<Survey>> surveyMap = [
            "Pr0" : [
                    Survey.builder(1001L)
                            .conditions([
                            new Condition.DefaultCondition(name: "age", value: new IntRange(15, 23)),
                            new Condition.DefaultCondition(name: "subject", value: 345345),
                            new Condition.DefaultCondition(name: "country", value: "ES")
                    ]).build(),
                    Survey.builder(1002L)
                            .conditions([
                            new Condition.DefaultCondition(name: "age", value: new IntRange(0, 7)),
                            new Condition.DefaultCondition(name: "subject", value: 345345),
                            new Condition.DefaultCondition(name: "country", value: "ES")
                    ]).build(),
                    Survey.builder(1003L)
                            .conditions([
                            new Condition.DefaultCondition(name: "age", value: new IntRange(50, 70)),
                            new Condition.DefaultCondition(name: "subject", value: 345345),
                            new Condition.DefaultCondition(name: "country", value: "ES")
                    ]).build()
            ]
    ]

    /**
     * {@inheritDoc}
     */
    @Override
    Flux<Survey> getSurveys(SurveyConditions surveyConditions, InformationProvider provider) {
        Collection<Survey> surveys = surveyMap.get(provider.id)

        log.debug('{ "Serving predefined data": {} }', surveys)

        return Flux.fromIterable(surveys)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Mono<Survey> getSurvey(Long surveyId, InformationProvider provider) {
        Mono.justOrEmpty(surveyMap.get(provider.id).find({ it.id == surveyId }))
    }

    /**
     * {@inheritDoc}
     */
    @Override
    boolean executes(InformationProvider provider) { true }

}
