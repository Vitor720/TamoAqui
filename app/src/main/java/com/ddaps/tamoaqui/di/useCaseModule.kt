package com.ddaps.tamoaqui.di

import com.ddaps.tamoaqui.common.domain.usecase.EventUseCase
import org.koin.dsl.module

val useCaseModule = module {
 factory { EventUseCase(get())}
}
