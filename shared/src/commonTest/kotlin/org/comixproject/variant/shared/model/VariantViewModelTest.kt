/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2024, The ComiXed Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses>
 */

package org.comixproject.variant.shared.model

import org.comixedproject.variant.shared.initKoin
import org.comixedproject.variant.shared.model.VariantViewModel
import org.comixedproject.variant.shared.model.server.Server
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

val TEST_NAME = "Server Name"
val TEST_URL = "http://www.comixedproject.org:7171/opds"
val TEST_USERNAME = "reader@comixedproject.org"
val TEST_PASSWORD = "p455w0rd!"

class VariantViewModelTest : KoinTest {
    private val viewModel: VariantViewModel by inject()

    @BeforeTest
    fun setUp() {
        initKoin()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    // TODO fix this
    //  @Test
    fun testCreateServer() {
        viewModel.saveServer(Server(null, TEST_NAME, TEST_URL, TEST_USERNAME, TEST_PASSWORD))

        val count = viewModel.servers.count { entry ->
            entry.name == TEST_NAME
            entry.url == TEST_URL
            entry.username == TEST_USERNAME
        }

        assertTrue(actual = count === 1)
    }
}