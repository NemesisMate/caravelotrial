package com.nemesismate.caravelo.trial.survey.condition

/**
 * A condition (name + value) that is to be used by the surveys.
 *
 * @param <T> value type of the condition
 *
 * @author NemesisMate
 */
interface Condition<T> {

    /**
     * @author NemesisMate
     */
    class DefaultCondition<T> implements Condition<T> {

        String name
        T value

        @Override
        boolean equals(Object obj) { return name == obj }

        @Override
        int hashCode() { name.hashCode() }

        @Override
        String toString() { """{"name": "$name", "value": "$value"}""" }

    }


    String getName()

    T getValue()

    boolean equals(Object o)

    int hashCode()

}
