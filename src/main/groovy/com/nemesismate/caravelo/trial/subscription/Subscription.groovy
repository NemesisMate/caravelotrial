package com.nemesismate.caravelo.trial.subscription

import com.fasterxml.jackson.annotation.JsonIgnore
import com.nemesismate.caravelo.trial.Actor
import com.nemesismate.caravelo.trial.provider.InformationProvider
import com.nemesismate.caravelo.trial.requester.InformationRequester
import com.nemesismate.caravelo.trial.survey.SurveyConditions

import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * A requester subscription. A requester can subscribe to a previous <em>'catalog search'</em>, which can be scheduled
 * to be executed in a desired <b>frequency</b>. When a subscription get executed, it will send to the <i>requester</i> a new search
 * based on the catalog conditions using the configured <b>channels</b>.
 *
 * @see InformationRequester
 * @see com.nemesismate.caravelo.trial.subscription.Subscription.Channel
 * @see com.nemesismate.caravelo.trial.subscription.Subscription.Frequency
 *
 * @see com.nemesismate.caravelo.trial.subscription.sender.SubscriptionSender
 *
 * @author NemesisMate
 */
@Entity
@Table(indexes = [ @Index(columnList = "frequency") ])
class Subscription {

    /**
     * Frequency to use as sending criteria
     */
    enum Frequency {
        MINUTELY, DAILY, WEEKLY
    }

    /**
     * Channel to be used each time the subscription must be sent
     */
    enum Channel {
        POSTAL, MAIL, API, FTP, LOG
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(targetEntity = Actor.class, optional = false)
    @JsonIgnore // We need to ignore this field serialization as it is already being serialized on the other side of the relation
    InformationRequester requester

    @ElementCollection(targetClass = Actor.class)
    @ManyToMany(mappedBy = "subscriptions", targetEntity = Actor.class, fetch = FetchType.EAGER)
    @NotEmpty
    List<InformationProvider> providers

    @Enumerated(EnumType.STRING)
    @NotNull
    Frequency frequency

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @NotEmpty
    Set<Channel> channels

    @Convert(converter = SurveyConditions.SurveyConditionsConverter)
    @NotNull
    SurveyConditions conditions



    static Builder.SubscriptionBuilderRequester builder() { Builder.create() }

    /**
     * It's a guided-builder, to ease the creation and do not forget all obligatory values
     */
    static class Builder implements SubscriptionBuilderRequester, SubscriptionBuilderProviders, SubscriptionBuilderFrequency, SubscriptionBuilderChannels, SubscriptionBuilderRequest {

        interface SubscriptionBuilderRequester {
            SubscriptionBuilderProviders requester(InformationRequester id)
        }

        interface SubscriptionBuilderProviders {
            SubscriptionBuilderFrequency providers(Iterable<InformationProvider> ids)
        }

        interface SubscriptionBuilderFrequency {
            SubscriptionBuilderChannels frequency(Frequency frequency)
        }

        interface SubscriptionBuilderChannels {
            SubscriptionBuilderRequest channels(Iterable<Channel> channels)
        }

        interface SubscriptionBuilderRequest {
            Builder conditions(SurveyConditions conditions)
        }


        Subscription subscription


        private Builder() { subscription = new Subscription() }


        @Override
        Builder requester(InformationRequester actor) {
            subscription.requester = actor; this
        }

        @Override
        Builder providers(Iterable<InformationProvider> actors) {
            subscription.providers = actors; this
        }

        @Override
        Builder frequency(Frequency frequency) {
            subscription.frequency = frequency; this
        }

        @Override
        Builder channels(Iterable<Channel> channels) {
            subscription.channels = channels; this
        }

        @Override
        Builder conditions(SurveyConditions conditions) {
            subscription.conditions = conditions; this
        }

        Subscription build() { subscription }


        static SubscriptionBuilderRequester create() { new Builder() }
    }

}
