package com.nemesismate.caravelo.trial.test.unit

import com.nemesismate.caravelo.trial.service.SubscriptionService
import com.nemesismate.caravelo.trial.subscription.Subscription
import com.nemesismate.caravelo.trial.web.controller.SubscriptionController
import com.nemesismate.caravelo.trial.web.dto.requester.ProviderDto
import com.nemesismate.caravelo.trial.web.dto.requester.RequesterDto
import com.nemesismate.caravelo.trial.web.dto.requester.subscription.SubscriptionDto
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
import reactor.core.publisher.Mono

import javax.inject.Inject

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.BDDMockito.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebFluxTest(SubscriptionController.class)
@AutoConfigureRestDocs
class CaraveloTrialApplicationSubscriptionTests {

    @Inject
    private WebTestClient webTestClient

    @MockBean
    private SubscriptionService subscriptionService

    @MockBean
    private ModelMapper modelMapper


    @Test
    void createSubscription() throws Exception {
        String jsonRequest = "{\"requester\":\"Req0\",\"providers\":[\"Pr0\"],\"frequency\":\"MINUTELY\",\"channels\":[\"LOG\"],\"survey\":{\"subject\":81111600,\"conditions\":{\"gender\":\"M\"},\"country\":\"ES\"}}"

        given(subscriptionService.createSubscription(any()))
                .willReturn(Mono.just( mock(Subscription) ))

        given(modelMapper.map(any(), (Class) eq(SubscriptionDto)))
                .willReturn(
                    new SubscriptionDto(
                            id: 234235234L
                            , requester: new RequesterDto(id: "Req0")
                            , providers: [ new ProviderDto(id: "pr0") ]
                            , frequency: SubscriptionDto.Frequency.MINUTELY
                            , channels: [ SubscriptionDto.Channel.LOG ]
                            , conditions: new SurveyConditionsDto(
                                    subject: 123456789L,
                                    country: "ES",
                                    conditions: [
                                            gender: "M"
                                    ]
                            )
                    )
                )

        this.webTestClient.post().uri("api/v0/subscription")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(jsonRequest), String)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(SurveyDto)
                .consumeWith(document("subscription-post-example"
                , requestFields(
                        fieldWithPath("requester")
                            .description("The requester's identifier")
                            .type(String)
                        , fieldWithPath("providers")
                            .description("The providers that the requester want to look for the subscription")
                            .type(String)
                        , fieldWithPath("frequency")
                            .description("The frequency of the notifications you want to receive")
                        , fieldWithPath("channels")
                            .description("Channel to use for the subscription (Possible values are: SMS, EMAIL, LOG)")
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
                )
                , responseFields(
                        fieldWithPath(".id")
                                .description("Identifier of the subscription")
                        , fieldWithPath("requester")
                            .description("Information about the requester")
                        , fieldWithPath("requester.id")
                            .description("The requester's identifier")
                        , fieldWithPath("providers")
                            .description("All the providers that the subscription is asking for surveys")
                        , fieldWithPath("providers[].id")
                            .description("Providers' identifiers")
                        , fieldWithPath("frequency")
                            .description("The frequency of the notifications you want to receive")
                        , fieldWithPath("channels")
                            .description("Channel to use for the subscription (Possible values are: SMS, EMAIL, LOG)")
                        , fieldWithPath("conditions")
                            .description("Surveys' conditions to be match")
                        , fieldWithPath("conditions.subject")
                            .description("Subject of the survey")
                        , fieldWithPath("conditions.country")
                            .description("Country of the survey")
                        , fieldWithPath("conditions.conditions")
                            .description('Survey\'s "conditions". Can be seen as conditions to be matched by the survey itself')
                        , fieldWithPath("conditions.conditions.gender")
                            .description("A survey condition")
                            .optional()
                )
        ))
    }

    @Test
    void showsSubscriptionById() throws Exception {
    }

    @Test
    void showSubscription() throws Exception {
    }

    @Test
    void deleteSubscription() throws Exception {
    }


}
