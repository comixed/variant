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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server

@Composable
fun ServerDetailView(server: Server) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(server.name, style = MaterialTheme.typography.titleLarge)
        Text(server.url, style = MaterialTheme.typography.titleMedium)
        Text(server.username, style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
@Preview
fun ServerDetailPreview() {
    VariantTheme {
        ServerDetailView(SERVER_LIST.get(0))
    }
}