package com.nemesismate.caravelo.trial.configuration.bean

import com.nemesismate.caravelo.trial.web.dto.requester.survey.SurveyConditionsDto
import groovy.util.logging.Slf4j
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author NemesisMate
 */
@Configuration
@Slf4j
class MapperBeans {

    @Bean
    ModelMapper modelMapper() {
        log.debug("Creating and configuring ModelMapper bean")

        ModelMapper mapper = ModelMapper.newInstance()

        SurveyConditionsDto.configure(mapper)

        return mapper
    }
}
