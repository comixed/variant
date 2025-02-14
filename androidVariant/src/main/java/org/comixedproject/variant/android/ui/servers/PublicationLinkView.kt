package org.comixedproject.variant.android.ui.servers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Logger

private val TAG = "PublicationLinkView"

@Composable
fun PublicationLinkView(
    serverLink: ServerLink,
    onLoadLink: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = {
            Logger.d(TAG, "Publication link selected: ${serverLink.downloadLink}")
            onLoadLink(serverLink.downloadLink)
        }) {
            Icon(
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = stringResource(
                    R.string.downloadPublicationLabel
                )
            )
        }
        Column {
            Text("${serverLink.title}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
@Preview
fun PublicationLinkPreview() {
    VariantTheme {
        PublicationLinkView(SERVER_LINK_LIST.get(0), onLoadLink = { _ -> })
    }
}