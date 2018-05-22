package com.nemesismate.caravelo.trial.service

import com.nemesismate.caravelo.trial.requester.InformationRequester
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Any implementation is in charge of handle all the requester logic, working too as an abstraction layer between
 * the persistence of the information requesters and the rest of the program.
 *
 * @see InformationRequester
 *
 * @author NemesisMate
 */
interface RequesterService {

    Mono<InformationRequester> findRequester(String requesterId)

    Flux<InformationRequester> findRequesterIds(Iterable<String> requesterIds)

    InformationRequester saveRequester(InformationRequester requester)

}