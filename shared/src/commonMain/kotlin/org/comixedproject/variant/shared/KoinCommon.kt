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

package org.comixedproject.variant.shared

import org.comixedproject.variant.shared.manager.FileContentManager
import org.comixedproject.variant.shared.repositories.DatabaseHelper
import org.comixedproject.variant.shared.repositories.ServerLinkRepository
import org.comixedproject.variant.shared.repositories.ServerRepository
import org.comixedproject.variant.shared.viewmodel.VariantViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

object Modules {
    val core =
        module {
            factory { DatabaseHelper(get()) }
            factory { FileContentManager() }
        }

    val repositories =
        module {
            factory {
                ServerRepository(get())
            }
            factory {
                ServerLinkRepository(get(), get(), get())
            }
        }

    val viewModels = module {
        factory {
            VariantViewModel(get())
        }
    }
}

expect val platformModule: Module

fun initKoin(
    appModule: Module = module { },
    coreModule: Module = Modules.core,
    repositoriesModule: Module = Modules.repositories,
    viewModelsModule: Module = Modules.viewModels,
): KoinApplication =
    startKoin {
        modules(
            appModule,
            coreModule,
            repositoriesModule,
            viewModelsModule,
            platformModule,
        )
    }
