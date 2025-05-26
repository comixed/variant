package org.comixedproject.variant

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform