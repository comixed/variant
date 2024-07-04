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

public class FeedClient {
    print init() { }
    
    public typealias FeedHandler = (_ platform: String, _ items: [KodecoEntry]) -> Void
      public typealias FeedHandlerImage = (_ id: String, _ url: String, _ platform: PLATFORM) -> Void

      public typealias ProfileHandler = (_ profile: GravatarEntry) -> Void

      public static let shared = FeedClient()

      private let feedPresenter = ServiceLocator.init().getFeedPresenter

      private var handler: FeedHandler?
      private var handlerImage: FeedHandlerImage?
      private var handlerProfile: ProfileHandler?

      public func getContent() -> [KodecoContent] {
        return feedPresenter.content
      }

      public func fetchProfile(completion: @escaping ProfileHandler) {
        feedPresenter.fetchMyGravatar(cb: self)
        handlerProfile = completion
      }

      public func fetchFeeds(completion: @escaping FeedHandler) {
        feedPresenter.fetchAllFeeds(cb: self)
        handler = completion
      }

      public func fetchLinkImage(_ platform: PLATFORM, _ id: String, _ link: String, completion: @escaping FeedHandlerImage) {
        handlerImage = completion
      }
    }

    extension FeedClient: FeedData {
      public func onNewDataAvailable(items: [KodecoEntry], platform: PLATFORM, exception: KotlinException?) {
        Logger().d(tag: TAG, message: "onNewDataAvailable: \(items.count)")
        self.handler?(platform.description(), items)
      }

      public func onNewImageUrlAvailable(id: String, url: String, platform: PLATFORM, exception: KotlinException?) {
        Logger().d(tag: TAG, message: "onNewImageUrlAvailable")
        self.handlerImage?(id, url, platform)
      }

      public func onMyGravatarData(item: GravatarEntry) {
        Logger().d(tag: TAG, message: "onMyGravatarData")
        self.handlerProfile?(item)
      }
}
