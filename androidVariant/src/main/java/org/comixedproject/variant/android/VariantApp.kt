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

import android.app.Application
import android.content.Context
import org.comixedproject.variant.shared.initKoin
import org.koin.core.Koin
import org.koin.dsl.module

lateinit var koin: Koin

/**
 * <code>VariantApp</code> provides an application class for Variant.
 *
 * @author Darryl L. Pierce
 */
class VariantApp : Application() {
    override fun onCreate() {
        super.onCreate()

        VariantApp.appContext = applicationContext

        koin =
            initKoin(
                appModule =
                    module {
                        single<Context> { this@VariantApp }
                    },
                viewModelsModule = module { },
            ).koin
    }

    companion object {
        lateinit var appContext: Context
    }
}
