package com.nemesismate.caravelo.trial.service.impl

import com.nemesismate.caravelo.trial.persistence.ActorRepository
import com.nemesismate.caravelo.trial.provider.InformationProvider
import com.nemesismate.caravelo.trial.requester.InformationRequester
import com.nemesismate.caravelo.trial.service.ProviderService
import com.nemesismate.caravelo.trial.service.RequesterService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import javax.inject.Inject

/**
 * @author NemesisMate
 */
@Service
class ActorServiceImpl implements ProviderService, RequesterService {

    @Inject
    ActorRepository actorRepository


    /**
     * {@inheritDoc}
     */
    @Override
    Mono<InformationProvider> findProvider(String providerId) {
        Mono.justOrEmpty(actorRepository.findById(providerId).get())
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Flux<InformationProvider> findProviders(Iterable<String> providerIds) {
        Flux.fromIterable(actorRepository.findAllById(providerIds))
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Mono<InformationRequester> findRequester(String requesterId) {
        Mono.justOrEmpty(actorRepository.findById(requesterId).get())
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Flux<InformationRequester> findRequesterIds(Iterable<String> requesterIds) {
        Flux.fromIterable(actorRepository.findAllById(requesterIds))
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InformationProvider saveProvider(InformationProvider provider) {
        actorRepository.save(provider)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InformationRequester saveRequester(InformationRequester requester) {
        actorRepository.save(requester)
    }
}
