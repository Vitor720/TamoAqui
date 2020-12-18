package com.ddaps.tamoaqui.common.domain.usecase

import com.ddaps.tamoaqui.common.domain.models.CheckInDataRequest
import com.ddaps.tamoaqui.common.domain.models.CheckInDataResponseOfRequest
import com.ddaps.tamoaqui.common.domain.models.EventDataResponse
import com.ddaps.tamoaqui.common.domain.models.Resource
import com.ddaps.tamoaqui.data.repository.EventsRepository

class EventUseCase(private val eventsRepo: EventsRepository) {

    suspend fun getEventsList(): Resource<List<EventDataResponse>>{
        val response = eventsRepo.getEventsList()
        return response
    }

    suspend fun postCheckIn(eventId: Int, userName: String, userEmail: String ): Resource<CheckInDataResponseOfRequest>{
        val checkInDataRequest = CheckInDataRequest(eventId, userName, userEmail)
        val response = eventsRepo.postCheckIn(checkInDataRequest)
        return response
    }
}