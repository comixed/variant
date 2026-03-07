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

package org.comixedproject.variant.android.view.server

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.DIRECTORY_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.model.library.DirectoryEntry
import org.comixedproject.variant.model.state.DownloadingState
import org.comixedproject.variant.platform.Log

private const val TAG = "BrowseServerView"

fun checkFiltering(filterText: String, entry: DirectoryEntry): Boolean {
  return entry.title.toLowerCase(Locale.current).contains(filterText) ||
    entry.filename.toLowerCase(Locale.current).contains(filterText)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseServerView(
  path: String,
  title: String,
  parentPath: String,
  filtering: Boolean,
  filterText: String,
  contents: List<DirectoryEntry>,
  comicBookList: List<ComicBook>,
  downloadingState: List<DownloadingState>,
  loading: Boolean,
  onLoadDirectory: (String, Boolean) -> Unit,
  onDownloadFile: (String, String) -> Unit,
  onToggleFiltering: (Boolean) -> Unit,
  onUpdateFilterText: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val pullToRefreshState = rememberPullToRefreshState()

  Scaffold(
    topBar = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = {
            Log.debug(TAG, "Going back to parent: ${parentPath}")
            onLoadDirectory(parentPath, false)
          },
          enabled = !parentPath.isEmpty(),
        ) {
          Icon(painterResource(R.drawable.ic_back), contentDescription = parentPath)
        }

        if (filtering) {
          TextField(
            value = filterText,
            placeholder = { Text(stringResource(R.string.filter_list_placeholder)) },
            maxLines = 1,
            onValueChange = { text ->
              Log.debug(TAG, "Filter text=${text}")
              onUpdateFilterText(text)
            },
            modifier = Modifier.weight(1f),
          )
        } else {
          val displayedTitle =
            when (title.isEmpty()) {
              false -> title
              true -> stringResource(R.string.rootDirectoryTitle)
            }

          Text(
            displayedTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1f),
          )
        }

        IconButton(
          onClick = {
            Log.debug(TAG, "Toggle filtering")
            onToggleFiltering(!filtering)
          },
          enabled = !parentPath.isEmpty(),
        ) {
          Icon(
            painterResource(R.drawable.ic_filter_text),
            contentDescription = stringResource(R.string.filter_list),
          )
        }
      }
    },
    content = { padding ->
      PullToRefreshBox(
        isRefreshing = loading,
        state = pullToRefreshState,
        onRefresh = { onLoadDirectory(path, true) },
        content = {
          LazyColumn(modifier = Modifier.padding(padding).fillMaxWidth()) {
            items(
              contents.filter {
                !filtering || checkFiltering(filterText.toLowerCase(Locale.current), it)
              }
            ) { entry ->
              when (entry.isDirectory) {
                true ->
                  DirectoryItemView(
                    entry,
                    onLoadDirectory = { path -> onLoadDirectory(path, false) },
                  )

                else ->
                  FileItemView(
                    entry,
                    comicBookList.map { it.filename }.toList(),
                    downloadingState,
                    onDownloadFile = onDownloadFile,
                  )
              }
            }
          }
        },
        modifier = Modifier.padding(padding).fillMaxSize(),
      )
    },
    modifier = modifier.fillMaxSize(),
  )
}

@Composable
@Preview
fun BrowseServerViewPreviewWithFiles() {
  var directory = DIRECTORY_LIST.filter { it.isDirectory }.first()
  VariantTheme {
    BrowseServerView(
      "http://www.comixedproject.org:7171",
      directory.title,
      directory.parent,
      false,
      "",
      DIRECTORY_LIST.filter { !it.isDirectory },
      emptyList(),
      emptyList(),
      false,
      onLoadDirectory = { _, _ -> },
      onDownloadFile = { _, _ -> },
      onToggleFiltering = {},
      onUpdateFilterText = {},
    )
  }
}

@Composable
@Preview
fun BrowseServerViewPreviewWithDirectories() {
  VariantTheme {
    BrowseServerView(
      "http://www.comixedproject.org:7171",
      "",
      "",
      false,
      "",
      DIRECTORY_LIST.filter { it.isDirectory },
      emptyList(),
      emptyList(),
      false,
      onLoadDirectory = { _, _ -> },
      onDownloadFile = { _, _ -> },
      onToggleFiltering = {},
      onUpdateFilterText = {},
    )
  }
}

@Composable
@Preview
fun BrowseServerViewPreviewRefreshing() {
  val directory = DIRECTORY_LIST.get(0)
  VariantTheme {
    BrowseServerView(
      "http://www.comixedproject.org:7171",
      directory.title,
      directory.parent,
      false,
      "",
      DIRECTORY_LIST.filter { !it.isDirectory },
      emptyList(),
      emptyList(),
      true,
      onLoadDirectory = { _, _ -> },
      onDownloadFile = { _, _ -> },
      onToggleFiltering = {},
      onUpdateFilterText = {},
    )
  }
}

@Composable
@Preview
fun BrowseServerViewPreviewFiltering() {
  val directory = DIRECTORY_LIST.get(0)
  val filterText = directory.title.substring(0, 3)
  VariantTheme {
    BrowseServerView(
      "http://www.comixedproject.org:7171",
      directory.title,
      directory.parent,
      true,
      filterText,
      DIRECTORY_LIST,
      emptyList(),
      emptyList(),
      true,
      onLoadDirectory = { _, _ -> },
      onDownloadFile = { _, _ -> },
      onToggleFiltering = {},
      onUpdateFilterText = {},
    )
  }
}
