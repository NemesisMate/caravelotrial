package com.nemesismate.caravelo.trial.web.controller

import com.nemesismate.caravelo.trial.ExceptionHelper
import com.nemesismate.caravelo.trial.service.SubscriptionService
import com.nemesismate.caravelo.trial.subscription.SubscriptionRequest
import com.nemesismate.caravelo.trial.web.dto.requester.subscription.SubscriptionDto
import com.nemesismate.caravelo.trial.web.dto.requester.subscription.SubscriptionRequestDto
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
 * <em><b>api/v0/subscription entry-point</b></em>. Used to create/index/show/delete any subscription.
 *
 * @see SubscriptionService
 *
 * @author NemesisMate
 */
@Slf4j
@RestController
@RequestMapping("api/v0/subscription")
class SubscriptionController {

    @Inject
    SubscriptionService subscriptionService

    @Inject
    ModelMapper modelMapper

    /**
     * TODO: Manage all missing possible problems (eg: 400)
     * @param subscriptionRequestDto
     * @return
     */
    @PostMapping(path = "", consumes = [ MediaType.APPLICATION_JSON_VALUE ])
    Mono<SubscriptionDto> create(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        log.info('{ "Received subscription creation message": {} }', subscriptionRequestDto)

        SubscriptionRequest subscriptionRequest = modelMapper.map(subscriptionRequestDto, SubscriptionRequest)

        subscriptionService.createSubscription(subscriptionRequest)
                .map( { modelMapper.map(it, SubscriptionDto) })
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Flux<?> index() {
        Flux.error(ExceptionHelper.unimplemented())
    }

    //TODO
    @GetMapping("{subscriptionId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<ResponseEntity<?>> show(@PathVariable Long surveyId) {
        Mono.error(ExceptionHelper.unimplemented())
    }

    //TODO
    @DeleteMapping("{subscriptionId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<ResponseEntity<Void>> delete(@PathVariable Long surveyId) {
        Mono.error(ExceptionHelper.unimplemented())
    }



}
