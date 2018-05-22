package com.nemesismate.caravelo.trial.provider

/**
 * The provider in the relationship: <em><b>requester <--- middleware ---> provider</b></em>
 *
 * The provider will receive a survey request or a survey catalog request and will return some results according.
 *
 * @see com.nemesismate.caravelo.trial.requester.InformationRequester
 *
 * @author NemesisMate
 */
interface InformationProvider {

    /**
     * Defines the type of a provider (can be used to separate different over-all <em>type-based</em> execution configurations)
     *
     * @see ProviderExecutorManager
     */
    enum ProviderType {
        REST, PERSISTENT
    }

    ProviderType getType()

    String getId()

    String getName()

}
