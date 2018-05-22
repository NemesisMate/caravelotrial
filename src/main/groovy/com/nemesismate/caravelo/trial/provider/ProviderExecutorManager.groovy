package com.nemesismate.caravelo.trial.provider

import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import groovy.util.logging.Slf4j
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import java.util.function.Consumer

/**
 * This 'executor' system allows us to define different provider types with ease (without mattering if it is a
 * local db retrieval, a remote REST call...) from a plug-in way.
 * This may normally be used to separate a lot of different providers types or to allow to have different provider
 * systems, being this object the system orchestrator.
 *
 * To implement different custom implementations for the providers (<em>identity-based</em> executor) it could be better to just use the
 * {@link com.nemesismate.caravelo.trial.provider.impl.ProviderSpecificExecutor} in conjunction with a {@link com.nemesismate.caravelo.trial.provider.impl.endpoint.ProviderEndpoint}
 * implementation.
 *
 * This is mainly intended to configure <em>type-based</em> executor instead of <em>identity-based</em>
 *
 * @see ProviderExecutor
 * @see InformationProvider#getType()
 *
 * @author NemesisMate
 */
@Slf4j
class ProviderExecutorManager {

    private List<ProviderExecutor> executors = new ArrayList<>()

    /**
     * Registers an executor. Only the first executor matching a provider will be executed.
     *
     * @param executor the executor to be registered
     * @return true if the executor wasn't already registered, false otherwise
     */
    boolean registerExecutor(ProviderExecutor executor) {
        if(!executors.contains(executor)) {
            executors.add(executor)
            return true
        }

        return false
    }

//    boolean unregisterExecutor(ProviderExecutor executor) {
//        return executors.remove(executor)
//    }

    /**
     * Uses the first compatible executor for the given provider. If there is no compatible executor available,
     * it throws an exception.
     *
     * @param surveyConditions the conditions to be used by the provider to find surveys (we make a flat-transfer of the request).
     * @param provider the information provider who to ask for the surveys.
     *
     * @return a collection with all the surveys that matched the request conditions.
     */
    Flux<Survey> getSurveys(SurveyConditions surveyConditions, InformationProvider provider) {
        getExecutor(provider).getSurveys(surveyConditions, provider)
            .doOnNext(ensureProvider(provider))
    }

    /**
     * Uses the first compatible executor for the given provider. If there is no compatible executor available,
     * it throws an exception.
     *
     * @param surveyId identifier of the survey (provider-relative)
     * @param provider information provider to be contacted with
     *
     * @return the survey if found, empty otherwise
     */
    Mono<Survey> getSurvey(Long surveyId, InformationProvider provider) {
        getExecutor(provider).getSurvey(surveyId, provider)
            .doOnNext(ensureProvider(provider))
    }


    private getExecutor(InformationProvider provider) { Objects.requireNonNull(executors.find({ it.executes(provider) })) }


    private static Consumer<Survey> ensureProvider(InformationProvider provider) {
        return { if(it && !it.provider) it.provider = provider }
    }

}
