package com.droidnotes.droidshop.common.kotlin.coroutines

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val droidDispatcher: DroidDispatchers)

enum class DroidDispatchers {
    Default,
    IO,
}