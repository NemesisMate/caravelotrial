package com.nemesismate.caravelo.trial.survey

import com.nemesismate.caravelo.trial.persistence.JsonConverter
import com.nemesismate.caravelo.trial.survey.condition.Condition

import javax.persistence.Converter

/**
 * Survey condition collection.
 *
 * @author NemesisMate
 */
class SurveyConditions extends ArrayList<Condition> {

    @Converter
    static class SurveyConditionsConverter extends JsonConverter<SurveyConditions> {
        @Override
        protected SurveyConditions fromJsonObject(Object jsonObject) {
            new SurveyConditions() {{ addAll(jsonObject) }}
        }
    }

}
