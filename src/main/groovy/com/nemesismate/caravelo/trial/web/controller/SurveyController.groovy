package com.nemesismate.caravelo.trial.web.controller

import com.nemesismate.caravelo.trial.ExceptionHelper
import com.nemesismate.caravelo.trial.service.SurveyService
import com.nemesismate.caravelo.trial.survey.SurveyConditions
import com.nemesismate.caravelo.trial.web.dto.requester.survey.AvailableSurveysRequestDto
import com.nemesismate.caravelo.trial.web.dto.requester.survey.SurveyDto
import groovy.util.logging.Slf4j
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import javax.inject.Inject

/**
 * <em><b>api/v0/survey entry-point</b></em>. Used to index/show any survey/catalog. Those are <em>middle-ware entry-points</em>.
 * The information returned by these, will be get by third-party implementations.
 *
 * @see SurveyService
 *
 * @author NemesisMate
 */
@Slf4j
@RestController
@RequestMapping("api/v0/survey")
class SurveyController {

    @Inject
    SurveyService surveyService

    @Inject
    ModelMapper modelMapper


    @PostMapping(headers = [ "X-HTTP-Method-Override=GET" ]
            , consumes = [ MediaType.APPLICATION_JSON_VALUE ]
            , produces = [ MediaType.APPLICATION_JSON_VALUE ]
    )
    Flux<SurveyDto> indexPost(@RequestBody AvailableSurveysRequestDto surveyMessage) {
        return index(surveyMessage)
    }


    @GetMapping(consumes = [ MediaType.APPLICATION_JSON_VALUE ]
            , produces = [ MediaType.APPLICATION_JSON_VALUE ]
    )
    @ResponseStatus(HttpStatus.OK)
    Flux<SurveyDto> index(@RequestBody AvailableSurveysRequestDto surveyMessage) {
        log.info('{ "Received survey request message": {} }', surveyMessage)

        SurveyConditions surveyRequest = modelMapper.map(surveyMessage.survey, SurveyConditions)

        Iterable<String> providerIds = surveyMessage.providers


        surveyService.findSurveys(providerIds, surveyRequest)
            .map({ survey -> modelMapper.map(survey, SurveyDto) })
    }


    @GetMapping("{providerId}/{surveyId}")
    Mono<ResponseEntity<SurveyDto>> show(@PathVariable String providerId, @PathVariable Long surveyId) {
        log.info('{ "Received show message for": { "providerId": "{}", "surveyId": {} } }', providerId, surveyId)

        if(!providerId || !surveyId) {
            return Mono.just(ResponseEntity.unprocessableEntity().build())
        }

        surveyService.getSurvey(providerId, surveyId)
                .map({ survey -> ResponseEntity.ok(modelMapper.map(survey, SurveyDto)) })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND))
    }

    //TODO
    @GetMapping("{providerId}/{surveyId}/results")
    Mono<ResponseEntity<SurveyDto>> showResults(@PathVariable String providerId, @PathVariable Long surveyId) {
        Mono.error(ExceptionHelper.unimplemented())
    }



}
