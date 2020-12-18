package com.ddaps.tamoaqui.common.domain.usecase

import com.ddaps.tamoaqui.common.domain.models.*
import com.ddaps.tamoaqui.data.repository.EventsRepository
import com.ddaps.tamoaqui.util.mapForView

class EventUseCase(private val eventsRepo: EventsRepository) {

    suspend fun getEventsList(): Resource<List<Event>>{
        val response = eventsRepo.getEventsList()
        return Resource(response.status, response.data?.mapForView(), response.message)
    }

    suspend fun postCheckIn(eventId: Int, userName: String, userEmail: String ): Resource<CheckInDataResponseOfRequest>{
        val checkInDataRequest = CheckInDataRequest(eventId, userName, userEmail)
        val response = eventsRepo.postCheckIn(checkInDataRequest)
        return response
    }
}