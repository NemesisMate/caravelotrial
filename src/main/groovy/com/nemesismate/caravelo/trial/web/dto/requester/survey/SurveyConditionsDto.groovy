package com.nemesismate.caravelo.trial.web.dto.requester.survey

import com.nemesismate.caravelo.trial.survey.SurveyConditions
import com.nemesismate.caravelo.trial.survey.condition.Condition
import org.modelmapper.AbstractConverter
import org.modelmapper.ModelMapper

class SurveyConditionsDto {

    private static final String rangeSeparator = ".."

    Long subject

    String country

    Map<String, Object> conditions



    static final class ConditionEntry {

        String name
        Object value

        static ConditionEntry from(String name, Object value) {
            return new ConditionEntry(name: name, value: value)
        }
    }

    static void configure(ModelMapper modelMapper) {
        modelMapper.createTypeMap(SurveyConditionsDto, SurveyConditions)
        modelMapper.createTypeMap(SurveyConditions, SurveyConditionsDto)

        modelMapper.addConverter(new AbstractConverter<SurveyConditions, SurveyConditionsDto>() {
            @Override
            protected SurveyConditionsDto convert(SurveyConditions conditions) {
                return new SurveyConditionsDto(
                        subject: conditions.find { it.name == "subject" }?.value as Long,
                        country: conditions.find { it.name == "country" }?.value,
                        conditions: conditions
                                .findAll { condition ->
                                    condition.name != "subject" && condition.name != "country"
                                }
                                .collect { condition ->
                                    modelMapper.map(condition, ConditionEntry)
                                }
                                .collectEntries { conditionEntry ->
                                    [(conditionEntry.name): conditionEntry.value]
                                }
                )
            }
        })

        modelMapper.addConverter(new AbstractConverter<SurveyConditionsDto, SurveyConditions>() {
            @Override
            protected SurveyConditions convert(SurveyConditionsDto conditionsDto) {
                Condition subjectCondition = modelMapper.map(ConditionEntry.from("subject", conditionsDto.subject), Condition.DefaultCondition)
                Condition countryCondition = modelMapper.map(ConditionEntry.from("country", conditionsDto.country), Condition.DefaultCondition)

                SurveyConditions conditions = new SurveyConditions()

                conditions.addAll(conditionsDto.conditions.collect({ entry ->
                    modelMapper.map(ConditionEntry.from(entry.key, entry.value), Condition.DefaultCondition)
                }))

                conditions << subjectCondition << countryCondition
            }
        })

        modelMapper.addConverter(new AbstractConverter<ConditionEntry, Condition.DefaultCondition>() {
            @Override
            protected Condition.DefaultCondition convert(ConditionEntry conditionEntry) {
                String name = conditionEntry.name
                Object value = conditionEntry.value

                if(value instanceof Map) {
                    List<Condition> subConditions = ((Map) value).collect { entry ->
                        modelMapper.map(ConditionEntry.from((String) entry.key, entry.value), Condition.DefaultCondition)
                    }

                    return new Condition.DefaultCondition(name: name, value: subConditions)
                }

                if(value instanceof String) {
                    String[] split = value.split(rangeSeparator)

                    if(split.size() == 2) {
                        return new Condition.DefaultCondition(name: name, value: new ObjectRange(split[0], split[1]))
                    }
                }

                return new Condition.DefaultCondition(name: name, value: value)
            }
        })


        modelMapper.addConverter(new AbstractConverter<Condition.DefaultCondition, ConditionEntry>() {
            @Override
            protected ConditionEntry convert(Condition.DefaultCondition condition) {
                String conditionString

                // This could be in each Condition class itself. However, separating this allows a full
                // abstraction between the dto mapping and the internal object. However, this
                // is against the Liskov substitution principle (L -> SOLID)
                if(condition.value instanceof Range) {
                    conditionString = """{"${condition.value.from}$rangeSeparator${condition.value.to}"}"""
                } else {
                    conditionString = condition.value.toString()
                }


                return new ConditionEntry(name: condition.name, value: conditionString)
            }
        })


    }

}
