/*
 * Prestige - A digital comic book reader.
 * Copyright (C) 2022, The ComiXed Project
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

package com.comixedproject.prestige.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.comixedproject.prestige.models.OPDSLibrary

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                OPDSLibraryListView(listOf<OPDSLibrary>(
                    OPDSLibrary("Library 1","http://www.comixedproject.org:7171/comics/lib1","admin1","password1"),
                    OPDSLibrary("Library 2","http://www.comixedproject.org:7171/comics/lib2","admin2","password2"),
                    OPDSLibrary("Library 3","http://www.comixedproject.org:7171/comics/lib3","admin3","password3"),
                    OPDSLibrary("Library 4","http://www.comixedproject.org:7171/comics/lib4","admin4","password4"),
                    OPDSLibrary("Library 5","http://www.comixedproject.org:7171/comics/lib5","admin5","password5")
                ))
            }
        }
    }
}

@Composable
fun OPDSLibraryListView(libraries: List<OPDSLibrary>) {
    Column {
        Text(text = "Library List",
            fontSize = 10.em,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )

        libraries.forEach { library -> OPDSLibraryEntryView(library) }
    }
}

@Composable
fun OPDSLibraryEntryView(library: OPDSLibrary) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Column {
            DisableSelection {
                Text(text = library.name, fontSize = 5.em, fontWeight = FontWeight.Bold)
            }
            Text(text = library.url)
            Text(text = "${library.username}/${library.password}")
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        OPDSLibraryListView(
            listOf<OPDSLibrary>(
                OPDSLibrary("Library 1","http://www.comixedproject.org:7171/comics/lib1","admin1","password1"),
                OPDSLibrary("Library 2","http://www.comixedproject.org:7171/comics/lib2","admin2","password2"),
                OPDSLibrary("Library 3","http://www.comixedproject.org:7171/comics/lib3","admin3","password3"),
                OPDSLibrary("Library 4","http://www.comixedproject.org:7171/comics/lib4","admin4","password4"),
                OPDSLibrary("Library 5","http://www.comixedproject.org:7171/comics/lib5","admin5","password5")
            )
        )
    }
}
