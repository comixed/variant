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

package org.comixedproject.variant.android.view.comics

import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.comixedproject.variant.adaptor.ArchiveAPI
import org.comixedproject.variant.adaptor.MetadataAPI
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.platform.Log

private val TAG = "ComicBookListItemView"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComicBookListItemView(
    comicBook: ComicBook,
    selected: Boolean,
    onClick: (ComicBook) -> Unit,
    modifier: Modifier = Modifier
) {
    var coverContent by remember { mutableStateOf<ByteArray?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val borderWidth = when (selected) {
        true -> 5.dp
        false -> 0.dp
    }

    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
            .border(borderWidth, Color.Red)
    ) {
        val title = MetadataAPI.displayableTitle(comicBook)

        Column(
            modifier = Modifier.combinedClickable(
                onClick = { onClick(comicBook) }
            )
        ) {
            comicBook.pages.firstOrNull()?.let { cover ->
                if (coverContent == null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cover_image_placeholder),
                        contentDescription = title
                    )

                    coroutineScope.launch(Dispatchers.Main) {
                        Log.debug(
                            TAG,
                            "Loading content for ${comicBook.filename}:${cover.filename}"
                        )
                        coverContent = ArchiveAPI.loadCover(
                            comicBook.path,
                            cover.filename
                        )
                    }
                } else {
                    coverContent?.let { content ->
                        Image(
                            bitmap = BitmapFactory.decodeByteArray(content, 0, content.size)
                                .asImageBitmap(),
                            contentDescription = title
                        )
                    }
                }
            }

            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun ComicBookListItemViewPreview() {
    VariantTheme {
        ComicBookListItemView(
            comicBook = COMIC_BOOK_LIST.get(0),
            false,
            onClick = {})
    }
}

@Composable
@Preview
fun ComicBookListItemViewSelectedPreview() {
    VariantTheme {
        ComicBookListItemView(
            comicBook = COMIC_BOOK_LIST.get(0),
            true,
            onClick = {})
    }
}