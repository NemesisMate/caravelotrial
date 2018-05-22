package com.nemesismate.caravelo.trial.service

import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Any implementation is in charge of handle all the survey logic, working too as an abstraction layer between
 * the survey fetch/send of information from third-party providers and the rest of the program.
 *
 * @author NemesisMate
 */
interface SurveyService {

    Flux<Survey> findSurveys(Iterable<String> providersIds, SurveyConditions surveyConditions)

    Mono<Survey> getSurvey(String providerId, Long surveyId)

}