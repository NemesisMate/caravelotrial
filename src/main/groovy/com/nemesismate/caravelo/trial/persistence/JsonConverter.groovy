package com.nemesismate.caravelo.trial.persistence

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import javax.persistence.AttributeConverter

/**
 * @author NemesisMate
 */
@Slf4j
class JsonConverter<T> implements AttributeConverter<T, String> {

    @Override
    String convertToDatabaseColumn(T object) {

        log.debug("Converting object to jsonString. Object: {}. Class: {}", object)

        String jsonString = JsonOutput.toJson(object)

        log.debug("Converted object to jsonString: {}. Object: {}", jsonString, object)

        return jsonString
    }

    @Override
    T convertToEntityAttribute(String jsonString) {

        log.debug("ReConverting from jsonString: {}", jsonString)

        Object object = new JsonSlurper().parseText(jsonString)

        log.debug("ReConverted object from jsonString: {}. JsonObject: {}", jsonString, object)

        T retypedObject = fromJsonObject(object)

        log.debug("ReTyped/ReCreated object from jsonObject: {}. Final Object: {}", object, retypedObject)

        return retypedObject
    }

    protected T fromJsonObject(Object jsonObject) {
        return (T) jsonObject
    }
}
