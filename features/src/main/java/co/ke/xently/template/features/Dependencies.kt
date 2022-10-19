package co.ke.xently.template.features

import co.ke.xently.template.libraries.common.Dispatcher
import co.ke.xently.template.libraries.data.source.local.Database
import co.ke.xently.template.libraries.data.source.local.Preference
import co.ke.xently.template.libraries.data.source.remote.Service
import okhttp3.Cache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class Dependencies @Inject constructor(
    val cache: Cache,
    val service: Service,
    val database: Database,
    val dispatcher: Dispatcher,
    val preference: Preference,
)
