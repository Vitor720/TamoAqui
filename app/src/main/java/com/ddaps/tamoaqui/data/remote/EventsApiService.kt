package com.ddaps.tamoaqui.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApiService {

    @GET("events")
    suspend fun getEventsList(): List<EventDataResponse>

    @POST("checkin")
    suspend fun postCheckIn(
            @Path("event_id") event_id: Int,
            @Body body: CheckInDataRequest): CheckInDataResponseOfRequest

}