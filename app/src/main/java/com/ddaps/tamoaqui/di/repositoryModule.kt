package com.ddaps.tamoaqui.di

import com.ddaps.tamoaqui.data.repository.EventsRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { EventsRepository(get(), get()) }
}
