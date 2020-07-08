package com.example.reposearchapp.util

import com.apollographql.apollo.ApolloClient
import com.example.reposearchapp.BuildConfig
import com.example.reposearchapp.BuildConfig.SERVER_URL
import okhttp3.OkHttpClient
import javax.inject.Singleton

private const val HEADER_NAME = "Authorization"
private const val HEADER_VALUE = "Bearer "

@Singleton
class ApolloWrapper {

    val apollo: ApolloClient

    init {
        val okHttp = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(
                    original.method(),
                    original.body()
                )
                builder.addHeader(HEADER_NAME, HEADER_VALUE + BuildConfig.TOKEN)
                chain.proceed(builder.build())
            }
            .build()

        apollo = ApolloClient.builder().serverUrl(SERVER_URL).okHttpClient(okHttp).build()
    }
}