package com.ddaps.tamoaqui.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EventViewModel(get()) }
}
