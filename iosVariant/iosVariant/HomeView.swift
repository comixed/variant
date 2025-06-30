/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2025, The ComiXed Project
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

import KMPObservableViewModelSwiftUI
import SwiftUI
import Variant

private let TAG = "HomeView"

struct HomeView: View {
    @EnvironmentViewModel var variantViewModel: VariantViewModel
    @State var currentDestination: AppDestination = .comics

    var body: some View {
        TabView(selection: $currentDestination) {
            ComicBooksView()
                .tag(AppDestination.comics)
                .tabItem { Label("Comics", systemImage: "book.fill") }

            ServerView()
                .tag(AppDestination.browseServer)
                .tabItem {
                    Label("Browse", systemImage: "person.crop.circle.fill")
                }

            SettingsView()
                .tag(AppDestination.settings)
                .tabItem { Label("Settings", systemImage: "gearshape.fill") }
        }
        .edgesIgnoringSafeArea(.bottom)
        .onChange(of: currentDestination) { tab in
            if tab == .browseServer {
                Task {
                    try await variantViewModel.loadDirectory(
                        path: variantViewModel.browsingState.currentPath,
                        reload: false
                    )
                }
            }
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
