package com.example.myapp.core.data.di

import com.example.myapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Provides
    fun provideKtor(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        useAlternativeNames = true
                        ignoreUnknownKeys = true
                        encodeDefaults = false
                    }
                )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TimeUnit.SECONDS.toMillis(20)
            socketTimeoutMillis = TimeUnit.SECONDS.toMillis(20)
            connectTimeoutMillis = TimeUnit.SECONDS.toMillis(20)
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    if (BuildConfig.DEBUG) Timber.d(message)
                }
            }
            level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.INFO
        }

        defaultRequest {
            header("X-KOLEO-Version", "1")

            url {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)

                protocol = URLProtocol.HTTPS
                host = BuildConfig.BASE_URL
            }
        }
    }
}