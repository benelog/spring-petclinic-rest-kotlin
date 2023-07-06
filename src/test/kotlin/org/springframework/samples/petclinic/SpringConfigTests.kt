package org.springframework.samples.petclinic

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [PetClinicApplication::class])
class SpringConfigTests {
    @Test
    fun contextLoads() {
        // Test the Spring configuration
    }
}
