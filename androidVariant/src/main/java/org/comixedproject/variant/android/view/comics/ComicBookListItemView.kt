package org.comixedproject.variant.android.view.comics

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun ComicBookListItemView(
    comicBook: ComicBook,
    onClick: (ComicBook) -> Unit,
    modifier: Modifier = Modifier
) {
    var coverContent by remember { mutableStateOf<ByteArray?>(null) }
    val coroutineScope = rememberCoroutineScope()

    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
    ) {
        val title = MetadataAPI.displayableTitle(comicBook)

        Column(modifier = Modifier.clickable(onClick = { onClick(comicBook) })) {
            comicBook.pages.firstOrNull()?.let { cover ->
                if (coverContent == null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cover_image_placeholder),
                        contentDescription = title
                    )

                    coroutineScope.launch(Dispatchers.IO) {
                        Log.debug(
                            TAG,
                            "Loading content for ${comicBook.filename}:${cover.filename}"
                        )
                        coverContent = ArchiveAPI.loadPage(
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
    VariantTheme { ComicBookListItemView(comicBook = COMIC_BOOK_LIST.get(0), onClick = {}) }
}