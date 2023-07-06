/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.samples.petclinic.model.Pet
import org.springframework.samples.petclinic.model.PetType

/**
 * @author Vitaliy Fedoriv
 */
class PetTypeRepositoryImpl(
    @PersistenceContext private val em: EntityManager
) : PetTypeRepositoryOverride {
    override fun delete(petType: PetType) {
        em.remove(if (em.contains(petType)) petType else em.merge(petType))

        val petTypeId = petType.id
        val pets: List<Pet> = em.createQuery("SELECT pet FROM Pet pet WHERE type.id=$petTypeId").resultList as List<Pet>
        for (pet in pets) {
            val visits = pet.getVisits()
            for (visit in visits) {
                em.createQuery("DELETE FROM Visit visit WHERE id=${visit.id}").executeUpdate()
            }
            em.createQuery("DELETE FROM Pet pet WHERE id=${pet.id}").executeUpdate()
        }
        em.createQuery("DELETE FROM PetType pettype WHERE id=$petTypeId").executeUpdate()
    }
}
