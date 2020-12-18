package com.ddaps.tamoaqui.di

import com.ddaps.tamoaqui.common.domain.ResponseHandler
import com.ddaps.tamoaqui.data.remote.provideEventsApi
import com.ddaps.tamoaqui.data.remote.provideRetrofit
import org.koin.dsl.module

val apiModule = module {
    factory { ResponseHandler() }
    factory { provideRetrofit() }
    factory { provideEventsApi(get()) }
}
