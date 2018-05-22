package com.nemesismate.caravelo.trial.survey

import com.nemesismate.caravelo.trial.provider.InformationProvider
import com.nemesismate.caravelo.trial.survey.condition.Condition

/**
 * @author NemesisMate
 */
class Survey {

    InformationProvider provider

    Long id

    SurveyConditions conditions = new SurveyConditions()

    @Override
    String toString() { """{"provider": $provider, "id": $id, "conditions": $conditions}""" }



    static Builder builder(Long id) { Builder.create(id) }

    static class Builder {

        Survey survey


        private Builder(Long id) { survey = new Survey(id: id) }

        Builder conditions(Iterable<Condition> conditions) {
            survey.conditions.addAll(conditions); this
        }

        Survey build() { survey }


        static Builder create(Long id) { new Builder(id) }
    }


}
