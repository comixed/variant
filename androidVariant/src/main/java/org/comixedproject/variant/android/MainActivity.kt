/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2024, The ComiXed Project
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

package org.comixedproject.variant.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.comixedproject.variant.android.ui.home.HomeView
import org.comixedproject.variant.android.viewmodel.SplashScreenViewModel
import org.comixedproject.variant.shared.platform.Log
import org.comixedproject.variant.shared.viewmodel.ComicBookViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MainActivity"

/**
 * <code>MainActivity</code> is the main class for the Android implementation of Variant.
 *
 * @author Darryl L. Pierce
 */
class MainActivity : ComponentActivity() {
    private val screenViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                screenViewModel.isSplashShow.value
            }
        }

        super.onCreate(savedInstanceState)

        setContent {
            val comicBookViewModel: ComicBookViewModel = koinViewModel()

            Log.debug(TAG, "Initializing comic book view model")
            val directory = applicationContext.filesDir.path
            comicBookViewModel.setLibraryDirectory(directory)
            comicBookViewModel.watchDirectory(directory)

            VariantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                }
                HomeView()
            }
        }
    }
}
