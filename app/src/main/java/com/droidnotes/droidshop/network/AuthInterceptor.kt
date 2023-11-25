package com.droidnotes.droidshop.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        val url =
            req.url.newBuilder().addQueryParameter("apiKey", "0fef0ew4425r4t5tjh56605049442n43")
                .build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}