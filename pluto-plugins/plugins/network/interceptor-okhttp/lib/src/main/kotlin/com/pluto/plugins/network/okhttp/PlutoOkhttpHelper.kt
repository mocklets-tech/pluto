package com.pluto.plugins.network.okhttp

import okhttp3.OkHttpClient
import javax.net.SocketFactory

fun OkHttpClient.Builder.addPlutoOkhttpInterceptor(): OkHttpClient.Builder {
    // todo add okhttp settings block here
    socketFactory(SocketFactory.getDefault())
    addInterceptor(PlutoInterceptor())
    return this
}
