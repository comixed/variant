/*
 * Variant - A digital comic book reading application for iPad, Android, and desktops.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.variant.ui.server

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.comixedproject.variant.R
import org.comixedproject.variant.model.OPDSServerEntry
import org.comixedproject.variant.ui.AnimatedSwipeDismiss

@Composable
fun OPDSServerListScreen(opdsServerList: SnapshotStateList<OPDSServerEntry>) {
    val state = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = state) {
            items(opdsServerList.size, key = { entry -> entry }) { index ->
                val entry = opdsServerList[index]
                AnimatedSwipeDismiss(
                    item = entry,
                    background = { _ ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .background(Color.Red)
                                .padding(start = 20.dp, end = 20.dp)
                        )
                        {
                            val alpha = 1.0f
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete_label),
                                modifier = Modifier.align(Alignment.CenterEnd),
                                tint = Color.White.copy(alpha = alpha)
                            )
                        }
                    },
                    content = {
                        OPDSServerCard(entry = entry)
                    },
                    onDismiss = { entry ->
                        if (opdsServerList.contains(entry)) {
                            opdsServerList.remove(entry)
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun OPDSServerListScreenAndroidPreview() {
    val list = SnapshotStateList<OPDSServerEntry>()
    list.add(
        OPDSServerEntry(
            "Preview Server 1",
            "http://www.comixedproject.org:7171/opds",
            "comixedreader@localhost",
            "comixedreader",
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    )
    list.add(
        OPDSServerEntry(
            "Preview Server 2",
            "http://www.comixedproject.org:7171/opds",
            "comixedreader@localhost",
            "comixedreader"
        )
    )
    list.add(
        OPDSServerEntry(
            "Preview Server 3",
            "http://www.comixedproject.org:7171/opds",
            "comixedreader@localhost",
            "comixedreader"
        )
    )
    OPDSServerListScreen(list)
}