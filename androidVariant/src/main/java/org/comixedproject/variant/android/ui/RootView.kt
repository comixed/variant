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

package org.comixedproject.variant.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.comixedproject.variant.shared.model.VariantViewModel
import org.comixedproject.variant.shared.model.server.AcquisitionLink
import org.comixedproject.variant.shared.model.server.Server
import org.koin.androidx.compose.getViewModel

@Composable
fun RootView(viewModel: VariantViewModel = getViewModel()) {
    var serverList by remember {
        mutableStateOf(listOf<Server>(), policy = neverEqualPolicy())
    }

    viewModel.onServerListUpdated = {
        serverList = it
    }

    var allLinks by remember {
        mutableStateOf(listOf<AcquisitionLink>())
    }

    viewModel.onAllLinksUpdated = {
        allLinks = it
    }

    var displayLinks by remember {
        mutableStateOf(listOf<AcquisitionLink>())
    }

    viewModel.onDisplayLinksUpdated = {
        displayLinks = it
    }

    HomeScreen(
        serverList,
        displayLinks,
        allLinks,
        onSaveServer = { server ->
            viewModel.saveServer(server)
        },
        onLoadDirectory = { server, directory, reload ->
            viewModel.loadServerFeed(server, directory, reload)
        }
    )
}