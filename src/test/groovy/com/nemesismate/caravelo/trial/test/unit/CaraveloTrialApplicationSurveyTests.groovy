package com.nemesismate.caravelo.trial.test.unit

import com.nemesismate.caravelo.trial.service.SurveyService
import com.nemesismate.caravelo.trial.survey.Survey
import com.nemesismate.caravelo.trial.web.controller.SurveyController
import com.nemesismate.caravelo.trial.web.dto.requester.ProviderDto
import com.nemesismate.caravelo.trial.web.dto.requester.survey.SurveyConditionsDto
import com.nemesismate.caravelo.trial.web.dto.requester.survey.SurveyDto
import org.junit.Test
import org.junit.runner.RunWith
import org.modelmapper.ModelMapper
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import javax.inject.Inject

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.BDDMockito.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebFluxTest(SurveyController.class)
@AutoConfigureRestDocs
class CaraveloTrialApplicationSurveyTests {

    @Inject
    private WebTestClient webTestClient

    @MockBean
    private SurveyService surveyService

    @MockBean
    private ModelMapper modelMapper


    @Test
    void showsSurveyByIdAndProvider() throws Exception {

        given(surveyService.getSurvey(any(), any())).willReturn(Mono.just( mock(Survey) ))

        given(modelMapper.map(any(), (Class) eq(SurveyDto)))
                .willReturn(new SurveyDto(
                        id: 1002L,
                        provider: new ProviderDto(id: "Pr0"),
                        conditions: new SurveyConditionsDto(
                                subject: 123456789L,
                                country: "ES",
                                conditions: [
                                        age: "0..60"
                                ]
                        )
                ))

        this.webTestClient.get().uri("api/v0/survey/{providerId}/{surveyId}", "Pr0", 1002L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody()
                .consumeWith(document("survey-get-example"
//                    //TODO: Find a way to avoid the repeating of the docs between this and the 'index' lookup
                    , responseFields(
                        fieldWithPath("id")
                            .description("Identifier of the survey withing it provider")
                            .type(Long)
                        , fieldWithPath("provider")
                            .description("Information about the survey provider")
                            .type(String)
                        , fieldWithPath("provider.id")
                            .description("The provider identifier")
                        , fieldWithPath("conditions")
                            .description("All the conditions that the survey has")
                        , fieldWithPath("conditions.subject")
                            .description("Subject of the survey")
                        , fieldWithPath("conditions.country")
                            .description("Country of the survey")
                        , fieldWithPath("conditions.conditions")
                            .description("More specific conditions")
                        , fieldWithPath("conditions.conditions.age")
                            .description("A survey condition")
                            .optional()
                    )
            ))
    }

	@Test
	void findAvailableSurveysFromDifferentProviders() throws Exception {

        Iterable<Survey> surveys =  (0..3).collect({ mock(Survey) })

        given(surveyService.findSurveys(any(), any()))
                .willReturn(Flux.fromIterable(surveys))

        surveys.eachWithIndex ({ survey, index ->
            given(modelMapper.map(survey, SurveyDto))
                    .willReturn(new SurveyDto(
                                id: 1000L + index,
                                provider: new ProviderDto(id: "Pr$index"),
                                conditions: new SurveyConditionsDto(
                                    subject: 123456789,
                                    country: "ES",
                                    conditions: [
                                            age: "0..100",
                                            gender: "M"
                                    ]
                            )
                    ))
        })

        String jsonRequest = "{\"requester\":\"Req0\",\"providers\":[\"Pr0\"],\"survey\":{\"subject\":81111600,\"conditions\":{\"gender\":\"M\",\"age\":[30,60],\"income\":{\"currency\":\"EUR\",\"range\":[20000,40000]}},\"country\":\"ES\"}}"

        this.webTestClient.post().uri("api/v0/survey")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-HTTP-Method-Override", "GET")
                .body(Mono.just(jsonRequest), String)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(SurveyDto)
                .consumeWith(document("available-surveys-get-example"
                    , requestFields(
                            fieldWithPath("requester")
                                    .description("The requester identifier")
                            , fieldWithPath("providers")
                                    .description("The providers that the requester want to look for surveys")
                            , fieldWithPath("survey")
                                    .description("Surveys' conditions to be match")
                            , fieldWithPath("survey.subject")
                                    .description("Subject of the survey")
                            , fieldWithPath("survey.country")
                                    .description("Country of the survey")
                            , fieldWithPath("survey.conditions")
                                    .description('Survey\'s "conditions". Can be seen as conditions to be matched by the survey itself')
                            , fieldWithPath("survey.conditions.gender")
                                    .description("A survey condition")
                                    .optional()
                            , fieldWithPath("survey.conditions.age")
                                    .description("Another survey condition")
                                    .optional()
                            , fieldWithPath("survey.conditions.income")
                                    .description("And yet one more condition")
                                    .optional()
                            //TODO: Find a way to ignore an specific field documentation
                            , fieldWithPath("survey.conditions.income.currency")
                                    .description("")
                                    .optional()
                            , fieldWithPath("survey.conditions.income.range")
                                    .description("")
                                    .optional()
                    )
                    , responseFields(
                            fieldWithPath("[].id")
                                    .description("Identifier of the survey withing it provider")
                            , fieldWithPath("[].provider")
                                    .description("Information about the survey provider")
                            , fieldWithPath("[].provider.id")
                                .description("The provider identifier")
                            , fieldWithPath("[].conditions")
                                .description("All the conditions that the survey has")
                            , fieldWithPath("[].conditions.subject")
                                .description("Subject of the survey")
                            , fieldWithPath("[].conditions.country")
                                .description("Country of the survey")
                            , fieldWithPath("[].conditions.conditions")
                                .description("More specific conditions")
                            , fieldWithPath("[].conditions.conditions.age")
                                .description("A survey condition")
                                .optional()
                            , fieldWithPath("[].conditions.conditions.gender")
                                .description("Another condition")
                                .optional()
                    )
                ))
        

	}

}
