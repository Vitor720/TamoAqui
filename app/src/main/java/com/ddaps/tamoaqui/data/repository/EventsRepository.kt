package com.ddaps.tamoaqui.data.repository

import com.ddaps.tamoaqui.common.domain.ResponseHandler
import com.ddaps.tamoaqui.common.domain.models.CheckInDataRequest
import com.ddaps.tamoaqui.common.domain.models.CheckInDataResponseOfRequest
import com.ddaps.tamoaqui.common.domain.models.EventDataResponse
import com.ddaps.tamoaqui.common.domain.models.Resource
import com.ddaps.tamoaqui.data.remote.EventsApiService

class EventsRepository(private val api: EventsApiService, private val responseHandler: ResponseHandler) {

    suspend fun getEventsList(): Resource<List<EventDataResponse>>{
        return try {
            val response = api.getEventsList()
            responseHandler.handleSuccess(response)
        } catch (e: Throwable){
            responseHandler.handleThrowable(e)
        }
    }

    suspend fun postCheckIn(checkIn: CheckInDataRequest): Resource<CheckInDataResponseOfRequest>{
        return try {
            val response = api.postCheckIn(checkIn.event_id, checkIn)
            responseHandler.handleSuccess(response)
        } catch (e: Throwable){
            responseHandler.handleThrowable(e)
        }
    }
}