package com.nemesismate.caravelo.trial.persistence

import com.nemesismate.caravelo.trial.Actor
import org.springframework.data.repository.CrudRepository

/**
 * @author NemesisMate
 */
interface ActorRepository extends CrudRepository<Actor, String> {

}
