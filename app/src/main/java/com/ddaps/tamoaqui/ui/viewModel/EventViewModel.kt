package com.ddaps.tamoaqui.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddaps.tamoaqui.common.domain.models.Event
import com.ddaps.tamoaqui.common.domain.models.Resource
import com.ddaps.tamoaqui.common.domain.usecase.EventUseCase
import kotlinx.coroutines.launch

class EventViewModel(private val useCase: EventUseCase): ViewModel() {

    private var eventsLiveData = MutableLiveData<Resource<List<Event>>>()
    fun getEventsLiveData() = eventsLiveData

    fun loadEventsList() {
        viewModelScope.launch {
            eventsLiveData.postValue(Resource.loading(null))
            val response = useCase.getEventsList()
            eventsLiveData.postValue(response)
        }
    }
}

