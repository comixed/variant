/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2025, The ComiXed Project
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

package org.comixedproject.variant.android.ui.servers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server

@Composable
fun ServerView(
    serverList: List<Server>,
    currentServer: Server?,
    onSetCurrentServer: (Server?) -> Unit
) {
    ServerListView(serverList, currentServer, onSelectServer = onSetCurrentServer)
}

@Composable
@Preview
fun ServerPreview_noSelection() {
    VariantTheme { ServerView(SERVER_LIST, null, onSetCurrentServer = { _ -> }) }
}

@Composable
@Preview
fun ServerPreview_withSelection() {
    VariantTheme { ServerView(SERVER_LIST, SERVER_LIST.get(0), onSetCurrentServer = { _ -> }) }
}