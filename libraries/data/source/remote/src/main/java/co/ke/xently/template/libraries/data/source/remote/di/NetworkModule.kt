package co.ke.xently.template.libraries.data.source.remote.di

import android.content.Context
import android.content.SharedPreferences
import co.ke.xently.template.libraries.common.Utils
import co.ke.xently.template.libraries.data.source.di.qualifiers.EncryptedSharedPreference
import co.ke.xently.template.libraries.data.source.remote.BuildConfig
import co.ke.xently.template.libraries.data.source.remote.Serialization
import co.ke.xently.template.libraries.data.source.remote.di.qualifiers.CacheInterceptor
import co.ke.xently.template.libraries.data.source.remote.di.qualifiers.RequestHeadersInterceptor
import co.ke.xently.template.libraries.data.source.remote.di.qualifiers.RequestQueriesInterceptor
import co.ke.xently.template.libraries.data.source.utils.SharedPreferenceKey.TOKEN_VALUE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @RequestQueriesInterceptor
    fun provideRequestQueriesInterceptors(): Interceptor = Interceptor { chain ->
        val request = chain.request()

        return@Interceptor chain.proceed(
            request.newBuilder().apply {
                val url = build().url
                if (url.queryParameter("relatedAsId") == null) {
                    // Return IDs of related objects instead of returning a browsable link. This
                    // helps reduce the size of response payload and also makes it easier to
                    // cache object relationships.
                    url(url.newBuilder().addQueryParameter("relatedAsId", "true").build())
                }
                if (url.queryParameter("q").isNullOrBlank()) {
                    url(url.newBuilder().removeAllQueryParameters("q").build())
                }
            }.build(),
        )
    }

    @Provides
    @Singleton
    @RequestHeadersInterceptor
    fun provideRequestHeadersInterceptors(
        @EncryptedSharedPreference
        preferences: SharedPreferences,
    ): Interceptor = Interceptor { chain ->
        val request = chain.request()

        return@Interceptor chain.proceed(
            request.newBuilder().apply {
                // Add the following headers iff they weren't already added by the
                // incoming request

                if (request.header("Accept-Language") == null) {
                    addHeader("Accept-Language", Locale.getDefault().language)
                }

                if (request.header("Accept") == null) {
                    val version = if (BuildConfig.API_VERSION.isNotBlank()) {
                        "; version=${BuildConfig.API_VERSION}"
                    } else ""
                    addHeader("Accept", "application/json${version}")
                }

                if (request.header("Authorization") == null) {
                    preferences.getString(TOKEN_VALUE, null)?.also {
                        addHeader("Authorization", "Bearer $it")
                    }
                }
            }.build(),
        )
    }

    @Provides
    @Singleton
    @CacheInterceptor
    fun provideCacheInterceptors(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        var response = chain.proceed(
            request.newBuilder()
                .cacheControl(CacheControl.parse(request.headers)).build()
        )
        if (response.code == 504 && response.request.cacheControl.onlyIfCached) {
            // See, https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control#other
            response = chain.proceed(
                response.request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK).build()
            )
        }
        return@Interceptor response
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (Utils.isReleaseBuild) {
            redactHeader("Authorization")
            HttpLoggingInterceptor.Level.NONE
        } else {
            HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, (5 * 1024 * 1024).toLong())

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        @CacheInterceptor cacheInterceptor: Interceptor,
        @RequestHeadersInterceptor headerInterceptor: Interceptor,
        @RequestQueriesInterceptor queriesInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(headerInterceptor)
        .addInterceptor(queriesInterceptor)
        .addInterceptor(cacheInterceptor) // maintain order - cache may depend on the headers
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(15L, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Serialization.JSON_CONVERTER))
        .client(okHttpClient)
        .build()
}