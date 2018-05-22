package com.nemesismate.caravelo.trial

import com.nemesismate.caravelo.trial.provider.InformationProvider
import com.nemesismate.caravelo.trial.provider.InformationProvider.ProviderType
import com.nemesismate.caravelo.trial.requester.InformationRequester
import com.nemesismate.caravelo.trial.subscription.Subscription

import javax.persistence.*

/**
 *
 * @see InformationRequester
 * @see InformationProvider
 *
 * @author NemesisMate
 */
@Entity(name="Actor")
class Actor implements InformationRequester, InformationProvider {

    @Id
    String id

    String name

    @Enumerated(EnumType.STRING)
    InformationProvider.ProviderType type

    //TODO: Make it LAZY (eg: use fetch join)
    @ManyToMany(fetch = FetchType.EAGER, cascade = [ CascadeType.ALL ])
    @JoinTable(
            joinColumns = @JoinColumn(nullable = false),
            inverseJoinColumns = @JoinColumn(nullable = false)
    )
    Collection<Subscription> subscriptions = new ArrayList<>()

    @Override
    void addSubscription(Subscription subscription) {
        subscriptions << subscription
    }

    @Override
    void removeSubscription(Subscription subscription) {
        subscriptions.remove(subscription)
    }

    static Builder builder(String id) { Builder.create(id) }


    static class Builder {

        Actor actor


        private Builder(String id) { actor = new Actor(id: id) }

        Builder name(String name) {
            actor.name = name; this
        }

        Builder type(ProviderType type) {
            actor.type = type; this
        }

        Actor build() { actor }


        static Builder create(String id) { new Builder(id) }

    }


}
