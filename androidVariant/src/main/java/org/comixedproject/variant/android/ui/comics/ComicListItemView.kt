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

package org.comixedproject.variant.android.ui.comics

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.COMIC_BOOK_LIST
import org.comixedproject.variant.shared.model.comics.ComicBook
import org.comixedproject.variant.shared.platform.Log

private const val TAG = "ComicLisItemView"
private const val TAG_COMIC_COVER = "tag.comic-cover"

@Composable
fun ComicListItemView(
    comicBook: ComicBook
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Log.debug(TAG, "Comic cover clicked")
            }
            .testTag(TAG_COMIC_COVER),
    ) {
        Column {
            comicBook.coverContent?.let { coverContent ->
                Image(
                    bitmap = BitmapFactory.decodeByteArray(coverContent, 0, coverContent.size)
                        .asImageBitmap(),
                    contentDescription = comicBook.displayTitle()
                )
            }

            Text(
                comicBook.displayTitle(),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun ComicBook.displayTitle(): String {
    val series = when (this.metadata.series.length > 0) {
        true -> this.metadata.series
        false -> "Unknown"
    }
    val volume = when (this.metadata.volume.length > 0) {
        true -> this.metadata.volume
        false -> "????"
    }
    val issueNumber = when (this.metadata.issueNumber.length > 0) {
        true -> this.metadata.issueNumber
        false -> "???"
    }

    return "${series} V${volume} #${issueNumber}"
}

@Composable
@Preview
fun ComicListItemPreview() {
    VariantTheme {
        ComicListItemView(COMIC_BOOK_LIST.get(0))
    }
}