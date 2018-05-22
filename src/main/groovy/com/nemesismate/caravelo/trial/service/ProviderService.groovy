package com.nemesismate.caravelo.trial.service

import com.nemesismate.caravelo.trial.provider.InformationProvider
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Any implementation is in charge of handle all the provider logic, working too as an abstraction layer between
 * the persistence of the information providers and the rest of the program.
 *
 * @see InformationProvider
 *
 * @author NemesisMate
 */
interface ProviderService {

    Mono<InformationProvider> findProvider(String providerId)

    Flux<InformationProvider> findProviders(Iterable<String> providerIds)

    InformationProvider saveProvider(InformationProvider provider)
}
