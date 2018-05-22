package com.nemesismate.caravelo.trial.service.impl

import com.nemesismate.caravelo.trial.persistence.SubscriptionRepository
import com.nemesismate.caravelo.trial.service.ProviderService
import com.nemesismate.caravelo.trial.service.RequesterService
import com.nemesismate.caravelo.trial.service.SubscriptionService
import com.nemesismate.caravelo.trial.service.SurveyService
import com.nemesismate.caravelo.trial.subscription.Subscription
import com.nemesismate.caravelo.trial.subscription.SubscriptionRequest
import com.nemesismate.caravelo.trial.subscription.sender.SubscriptionSender
import com.nemesismate.caravelo.trial.survey.Survey
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

import javax.inject.Inject

/**
 * @author NemesisMate
 */
@Service
@Slf4j
class SubscriptionServiceImpl implements SubscriptionService {

    @Inject
    private SurveyService surveyService

    @Inject
    private SubscriptionRepository subscriptionRepository

    @Inject
    ProviderService providerService

    @Inject
    RequesterService requesterService

    private Map<Subscription.Channel, SubscriptionSender> senders = new EnumMap<>(Subscription.Channel)

    /**
     * {@inheritDoc}
     */
    @Override
    Mono<Subscription> createSubscription(SubscriptionRequest request) {
        log.info('{ "Creating subscription" : "{}" }', request)

        requesterService.findRequester(request.requester)
            .map({ requester ->
                providerService.findProviders(request.providers)
                        .collectList()
                        .map({ providers ->
                            Subscription.builder()
                                    .requester(requester)
                                    .providers(providers)
                                    .frequency(request.frequency)
                                    .channels(request.channels.collect({ Subscription.Channel.valueOf(it.name()) }))
                                    .conditions(request.survey)
                                    .build()
                        })
                        .map({ subscription ->
                            log.debug('{ "Saving subscription" : "{}" }', subscription)
                            requester.addSubscription(subscription)

                            requesterService.saveRequester(requester)

                            return subscription
                        })
            }).flatMap({ it })

    }

    /**
     * {@inheritDoc}
     */
    @Override
    void sendSubscriptions(Subscription.Frequency frequency) {
        log.info('{ "message": "Sending subscriptions", "frequency": "{}" }', frequency)

        Flux.fromIterable(subscriptionRepository.findAllByFrequency(frequency))
                .parallel().runOn(Schedulers.elastic())
                .doOnNext( { subscription ->
                    Flux<Survey> surveyPublisher = surveyService.findSurveys(subscription.providers.collect({ it.id }), subscription.conditions)
                    Collection<Survey> surveys = surveyPublisher.collectList().block()

                    log.debug('{ "message": "Sending subscription through all desired channels", "subscription": {}, "channels": {} }', subscription, subscription.channels)

                    // For each channel, we send the subscribed surveys. If there is no sender for the channel, we expect a NPE
                    Flux.fromIterable(subscription.channels)
                            .parallel().runOn(Schedulers.elastic())
                            .subscribe({ channel ->
                                SubscriptionSender sender = senders.get(channel)

                                if(!sender) {
                                    return log.warn('{ "message": "No sender registered for channel", "channel": {} }', channel)
                                }

                                sender.send(surveys, subscription.requester)
                            })
                })
                // We go sequential so we can check when the whole parallel execution is completed. If we don't do that, we'll get
                // a completed call for every used thread
                .sequential().doOnComplete({ log.info('{ "message": "Sending subscriptions ended" }') })
                .subscribe()
    }

    void registerSender(Subscription.Channel channel, SubscriptionSender sender) {
        if(senders.put(channel, sender) != null) {
            throw new IllegalStateException("That channel ($channel) has already a sender ($sender) registered")
        }
    }




}
