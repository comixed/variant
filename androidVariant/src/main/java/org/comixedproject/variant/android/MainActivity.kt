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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.net.loadServerLinks
import org.comixedproject.variant.android.ui.home.HomeView
import org.comixedproject.variant.android.viewmodel.SplashScreenViewModel
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.viewmodel.ServerLinkViewModel
import org.comixedproject.variant.shared.viewmodel.ServerViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

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
            VariantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    val serverViewModel: ServerViewModel = getViewModel()
                    val serverLinkViewModel: ServerLinkViewModel = getViewModel()
                    val serverList by serverViewModel.serverList.collectAsState()
                    val serverLinkList by serverLinkViewModel.serverLinkList.collectAsState()

                    HomeView(
                        serverList, serverLinkList,
                        onSaveServer = { server ->
                            Logger.d(TAG, "Saving server: name=${server.name}")
                            serverViewModel.saveServer(server)
                        },
                        onDeleteServer = { server ->
                            Logger.d(TAG, "Deleting server: name=${server.name}")
                            serverViewModel.deleteServer(server)
                        },
                        onLoadLinks = { server, directory, reload ->
                            Logger.d(
                                TAG,
                                "Loading directory: name=${server.name} directory=${directory}"
                            )

                            if (reload || !serverLinkViewModel.hasLinks(server, directory)) {
                                coroutineScope.launch {
                                    loadServerLinks(
                                        server,
                                        directory,
                                        onSuccess = { links ->
                                            serverLinkViewModel.saveLinks(
                                                server,
                                                directory,
                                                links
                                            )
                                        },
                                        onFailure = { })
                                }
                            } else {
                                serverLinkViewModel.loadLinks(server, directory)
                            }
                        }
                    )
                }
            }
        }
    }
}
