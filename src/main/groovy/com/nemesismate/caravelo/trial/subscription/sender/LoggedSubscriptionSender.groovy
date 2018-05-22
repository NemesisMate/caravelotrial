package com.nemesismate.caravelo.trial.subscription.sender

import com.nemesismate.caravelo.trial.requester.InformationRequester
import com.nemesismate.caravelo.trial.survey.Survey
import groovy.util.logging.Slf4j

/**
 * Logging-based sender. It will send to the <em>logs (info-level)</em> the subscription results.
 *
 * @author NemesisMate
 */
@Slf4j
class LoggedSubscriptionSender implements SubscriptionSender {

    @Override
    void send(Iterable<Survey> surveys, InformationRequester requester) {
        log.info('{ "message": "Sending surveys to requester", "requester": "{}", "Surveys": {} }', requester.id, surveys)
    }
}
