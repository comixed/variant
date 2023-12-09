package org.comixedproject.variant.ui.server

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.comixedproject.variant.model.ServerColorOption

@Composable
fun ServerColorOption(
    color: ServerColorOption,
    currentColor: String,
    onColorPicked: (ServerColorOption) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onColorPicked(color)
                }
            )
    ) {
        val fontWeight = if (currentColor == color.hex) FontWeight.Bold else FontWeight.Normal
        ServerColor(
            modifier = Modifier.padding(10.dp),
            color = ServerColorOption.fromHex(color.hex),
            size = 40.dp,
            border = 2.dp
        )
        Text(
            text = color.name,
            fontWeight = fontWeight,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterVertically)
        )

    }
}

@Preview
@Composable
fun ServerColorItemPreview() {
    ServerColorOption(
        color = ServerColorOption.COLORS[0],
        ServerColorOption.COLORS[1].hex,
        onColorPicked = {})
}

@Preview
@Composable
fun ServerColorItemCurrentPreview() {
    ServerColorOption(
        color = ServerColorOption.COLORS[0],
        ServerColorOption.COLORS[0].hex,
        onColorPicked = {})
}