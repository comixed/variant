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
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.adaptor.ArchiveAPI
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.platform.Log

private const val TAG = "PageNavigationView"

@Composable
fun PageNavigationView(
    comicBook: ComicBook,
    onStopReading: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pageFilename by remember { mutableStateOf("") }
    var currentPage by remember { mutableIntStateOf(0) }
    var currentPageContent by remember { mutableStateOf<ByteArray?>(null) }
    var showPageOverlay by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { location ->
                        var handled = false
                        val screenHeightMiddle = context.resources.displayMetrics.heightPixels / 2
                        if (location.y > (screenHeightMiddle * 1.5)) {
                            val screenWidthMiddle = context.resources.displayMetrics.widthPixels / 2
                            if (location.x <= (screenWidthMiddle / 2)) {
                                if (currentPage > 0) {
                                    Log.info(TAG, "Navigating back to page ${currentPage - 1}")
                                    currentPageContent = null
                                    currentPage = currentPage - 1
                                }
                                handled = true
                            } else if (location.x >= (screenWidthMiddle * 3) / 2) {
                                if (currentPage < comicBook.pages.size - 1) {
                                    Log.info(
                                        TAG,
                                        "Navigating forward to page ${currentPage + 1}"
                                    )
                                    currentPageContent = null
                                    currentPage = currentPage + 1
                                }
                                handled = true
                            }
                        }
                        if (!handled) {
                            showPageOverlay = (showPageOverlay == false)
                            Log.info(TAG, "Setting show overlay: ${showPageOverlay}")
                        }
                    }
                )
            }
            .fillMaxSize()) {
        if (showPageOverlay) {
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
                        painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.stopReadingLabel)
                    )
                }

                Text(
                    text = comicBook.filename,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = pageFilename,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = {
                    currentPageContent = null
                    currentPage = currentPage - 1
                }, enabled = (currentPage > 0)) {
                    Icon(
                        painterResource(R.drawable.ic_previous_page),
                        contentDescription = stringResource(R.string.previousPageLabel)
                    )
                }

                Slider(
                    value = currentPage.toFloat(),
                    valueRange = 0f..(comicBook.pages.size - 1).toFloat(),
                    steps = comicBook.pages.size,
                    onValueChange = {
                        currentPageContent = null
                        currentPage = it.toInt()
                    },
                    modifier = Modifier.weight(0.9f)
                )

                IconButton(onClick = {
                    currentPageContent = null
                    currentPage = currentPage + 1
                }, enabled = (currentPage < (comicBook.pages.size - 1))) {
                    Icon(
                        painterResource(R.drawable.ic_next_page),
                        contentDescription = stringResource(R.string.nextPageLabel)
                    )
                }
            }
        }


        if (currentPageContent == null) {
            LaunchedEffect(currentPageContent) {
                pageFilename = comicBook.pages.get(currentPage).filename
                currentPageContent =
                    ArchiveAPI.loadPage(comicBook.path, pageFilename)

            }
        } else {
            currentPageContent?.let { content ->
                Image(
                    bitmap = BitmapFactory.decodeByteArray(content, 0, content.size)
                        .asImageBitmap(),
                    contentDescription = comicBook.pages.get(currentPage).filename,
                    modifier = modifier
                        .fillMaxHeight()
                )
            }
        }
    }
}


@Composable
@Preview
fun PageNavigationPreview() {
    val comic = COMIC_BOOK_LIST.get(0)

    VariantTheme {
        PageNavigationView(
            comic,
            onStopReading = {})
    }
}

@Composable
@Preview
fun PageNavigationPreviewWithOverlay() {
    val comic = COMIC_BOOK_LIST.get(0)

    VariantTheme {
        PageNavigationView(
            comic,
            onStopReading = {})
    }
}
