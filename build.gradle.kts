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
import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
  alias(libs.plugins.android.application).apply(false)
  alias(libs.plugins.android.library).apply(false)
  alias(libs.plugins.kotlin.android).apply(false)
  alias(libs.plugins.kotlin.multiplatform).apply(false)
  alias(libs.plugins.compose.compiler).apply(false)
  alias(libs.plugins.sqldelight).apply(false)
  alias(libs.plugins.ksp).apply(false)
  alias(libs.plugins.native.coroutines).apply(false)
  alias(libs.plugins.plugin.serialization).apply(false)

  alias(libs.plugins.sonarqube)
  alias(libs.plugins.spotless).apply(false)
}

sonar {
  properties {
    property("sonar.projectKey", "comixed_variant")
    property("sonar.organization", "comixed")
    property("sonar.host.url", "https://sonarcloud.io")
    property("sonar.exclusions", "**/gradle.build.kts,**/build/**")
  }
}

allprojects {
  apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)
  configure<SpotlessExtension> {
    kotlin {
      ktfmt(libs.versions.ktfmt.get()).googleStyle()
      target("src/**/*.kt")
      targetExclude("${layout.buildDirectory}/**/*.kt")
      licenseHeaderFile(rootProject.file(".spotless/copyright.txt"))
        .onlyIfContentMatches("missingString")
    }
    kotlinGradle {
      ktfmt(libs.versions.ktfmt.get()).googleStyle()
      target("*.kts")
      targetExclude("${layout.buildDirectory}/**/*.kts")
      licenseHeaderFile(rootProject.file(".spotless/copyright.txt"), "(^(?![\\/ ]\\*).*$)")
        .onlyIfContentMatches("missingString")
      toggleOffOn()
    }
    format("xml") {
      target("src/**/*.xml")
      targetExclude("**/build/", ".idea/")
      trimTrailingWhitespace()
      endWithNewline()
    }
  }
}
