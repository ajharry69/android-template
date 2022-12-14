package co.ke.xently.template.libraries.data.source.utils

import kotlin.reflect.KClass

data class RetryError(val retrials: Map<KClass<out Throwable>, Int> = Retry.DEFAULT_RETRYABLE_ERROR_CLASSES)
