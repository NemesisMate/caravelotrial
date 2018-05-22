package com.nemesismate.caravelo.trial

import com.nemesismate.caravelo.trial.persistence.ActorRepository
import com.nemesismate.caravelo.trial.provider.InformationProvider
import groovy.util.logging.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author NemesisMate
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
class CaraveloTrialApplication {

	static void main(String[] args) {
		SpringApplication.run(CaraveloTrialApplication.class, args)
	}

	@Bean
	@Profile("!test")
	CommandLineRunner actorBootstrap(ActorRepository actorRepo) {{ args ->
        log.info("BootStraping actor sample data")

        List<Actor> actors = [
                Actor.builder('Pr0').name('Provider 0').type(InformationProvider.ProviderType.REST).build(),
                Actor.builder('Req0').name('Requester 0').build(),
		]

		actors.forEach({actorRepo.save(it)})
	}}


}
