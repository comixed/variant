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

package org.comixedproject.variant.android.view.reading

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.adaptor.ArchiveAPI
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.platform.Log

private const val TAG = "ReadingPageView"

@Composable
fun ReadingPageView(
    comicFilename: String,
    pageFilename: String,
    title: String,
    currentPage: Int,
    totalPages: Int,
    onChangePage: (Int) -> Unit,
    onStopReading: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPageContent by remember { mutableStateOf<ByteArray?>(null) }

    Scaffold(
        content = { padding ->
            if (currentPageContent == null) {
                LaunchedEffect(currentPageContent) {
                    currentPageContent =
                        ArchiveAPI.loadPage(comicFilename, pageFilename)
                }
            } else {
                currentPageContent?.let { content ->
                    Image(
                        bitmap = BitmapFactory.decodeByteArray(content, 0, content.size)
                            .asImageBitmap(),
                        contentDescription = title,
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxHeight()
                    )
                }
            }
        },
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        Log.debug(
                            TAG,
                            "Closing comic book"
                        )
                        onStopReading()
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.stopReadingLabel)
                    )
                }

                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {
                        currentPageContent = null
                        onChangePage(currentPage - 1)
                    }, enabled = (currentPage > 0)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.previousPageLabel)
                        )
                    }

                    Slider(
                        value = currentPage.toFloat(),
                        valueRange = 0f..(totalPages - 1).toFloat(),
                        steps = totalPages,
                        onValueChange = {
                            currentPageContent = null
                            onChangePage(it.toInt())
                        },
                        modifier = Modifier.weight(0.9f)
                    )

                    IconButton(onClick = {
                        currentPageContent = null
                        onChangePage(currentPage + 1)
                    }, enabled = (currentPage < (totalPages - 1))) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = stringResource(R.string.nextPageLabel)
                        )
                    }
                }
            }
        },
        modifier = modifier
            .fillMaxSize()
    )
}


@Composable
@Preview
fun ReadingPageViewPreview() {
    val comic = COMIC_BOOK_LIST.get(0)
    VariantTheme {
        ReadingPageView(
            comic.filename,
            comic.pages.get(0).filename,
            "Page Title",
            5,
            10,
            onChangePage = {},
            onStopReading = {}
        )
    }
}