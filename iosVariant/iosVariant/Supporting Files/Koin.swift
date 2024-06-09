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

import Variant

final class Koin {
    private var core: Koin_coreKoin?
    
    static let instance = Koin()
    
    static func start() {
        if instance.core == nil {
            let app = KoinIOS.shared.initialize(
                userDefaults: UserDefaults.standard
            )
            instance.core = app.koin
        }
        if instance.core == nil {
            fatalError("Failed to initialize Koin.")
        }
    }
    
    private init() {}
    
    func get<T: AnyObject>() -> T {
        guard let core else {
            fatalError("You should call `start()` before using \(#function)")
        }
        
        guard let result = core.get(objCClass: T.self) as? T else { fatalError("Koin can't provide an instance of type: \(T.self)") }
        
        return result
    }
}
