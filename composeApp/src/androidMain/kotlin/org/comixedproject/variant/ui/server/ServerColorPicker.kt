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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.model.ServerColorOption

@Composable
fun ServerColorPicker(
    modifier: Modifier = Modifier,
    currentColor: String,
    onColorPicked: (ServerColorOption) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(ServerColorOption.COLORS.size) { itemIndex ->
            val color = ServerColorOption.COLORS[itemIndex]
            ServerColorOption(
                color = color,
                currentColor = currentColor,
                onColorPicked = onColorPicked
            )
        }
    }
}

@Preview
@Composable
fun ServerColorPickerPreview() {
    VariantTheme {
        ServerColorPicker(currentColor = ServerColorOption.COLORS[0].hex, onColorPicked = {})
    }
}