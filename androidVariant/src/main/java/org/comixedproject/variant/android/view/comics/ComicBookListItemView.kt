package org.comixedproject.variant.android.view.comics

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.view.BYTES_PER_MB
import org.comixedproject.variant.model.library.ComicBook

private val TAG = "ComicBookListItemView"

@Composable
fun ComicBookListItemView(comicBook: ComicBook, modifier: Modifier = Modifier) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = comicBook.filename,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
            )

            Text(
                text = stringResource(
                    R.string.comicFileSizeText,
                    String.format(format = "%.1f", (comicBook.size).toDouble() / BYTES_PER_MB)
                ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
            )

            val date = SimpleDateFormat("MM/dd/YYYY HH:mm a").format(comicBook.lastModified)
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
            )
        }
    }
}

@Composable
@Preview
fun ComicBookListItemViewPreview() {
    VariantTheme { ComicBookListItemView(comicBook = COMIC_BOOK_LIST.get(0)) }
}