package co.ke.xently.template.libraries.data.source.remote

import co.ke.xently.template.libraries.data.source.remote.services.AccountService
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class Service @Inject constructor(val retrofit: Retrofit) {
    val account: AccountService = retrofit.create(AccountService::class.java)
}
