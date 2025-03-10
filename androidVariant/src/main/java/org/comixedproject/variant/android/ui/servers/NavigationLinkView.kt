package org.comixedproject.variant.android.ui.servers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
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

private val TAG = "NavigationLinkView"

@Composable
fun NavigationLinkView(serverLink: ServerLink, onLoadLink: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.clickable {
            Logger.d(TAG, "Navigation link selected: ${serverLink.downloadLink}")
            onLoadLink(serverLink.downloadLink)
        }) {
            Text("${serverLink.title}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.weight(1.0f))

            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = stringResource(R.string.downloadNavigationLabel)
            )
        }
    }
}

@Composable
@Preview
fun NavigationLinkPreview() {
    VariantTheme {
        NavigationLinkView(SERVER_LINK_LIST.get(0), onLoadLink = { _ -> })
    }
}