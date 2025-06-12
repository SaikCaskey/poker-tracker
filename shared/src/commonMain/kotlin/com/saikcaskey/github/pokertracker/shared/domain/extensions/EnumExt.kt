@file:Suppress("unused")

package com.saikcaskey.github.pokertracker.shared.domain.extensions

inline fun <reified T : Enum<T>> valueOfOrDefault(name: String, default: T): T {
    return enumValues<T>().firstOrNull { it.name == name } ?: default
}