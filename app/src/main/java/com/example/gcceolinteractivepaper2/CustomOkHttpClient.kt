package com.example.gcceolinteractivepaper2

import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class CustomOkHttpClient {
    private lateinit var client: OkHttpClient
    init {
        val trustAllCertificates = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        }

        // Create an SSLContext with the custom TrustManager to use in your network calls
        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        sslContext.init(
            null,
            arrayOf<TrustManager>(trustAllCertificates),
            java.security.SecureRandom()
        )
        client = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCertificates)
            .hostnameVerifier { _, _ -> true }
            .build()
    }


// Apply the SSLContext to your HTTP client (assuming you're using Retrofit or similar)

    fun getClient(): OkHttpClient{
        return client
    }

}