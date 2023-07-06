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
package org.springframework.samples.petclinic.rest.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.samples.petclinic.mapper.UserMapper
import org.springframework.samples.petclinic.rest.api.UsersApi
import org.springframework.samples.petclinic.rest.dto.UserDto
import org.springframework.samples.petclinic.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(exposedHeaders = ["errors, content-type"])
@RequestMapping("api")
class UserRestController(
    private val userService: UserService,
) : UsersApi {

    @PreAuthorize("hasRole(@roles.ADMIN)")
    override fun addUser(userDto: UserDto): ResponseEntity<UserDto> {
        val headers = HttpHeaders()
        val user = UserMapper.toUser(userDto)
        userService.saveUser(user)
        return ResponseEntity(UserMapper.toUserDto(user), headers, HttpStatus.CREATED)
    }
}
