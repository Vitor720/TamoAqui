package com.ddaps.tamoaqui.data.remote

import com.ddaps.tamoaqui.common.domain.models.CheckInDataRequest
import com.ddaps.tamoaqui.common.domain.models.CheckInDataResponseOfRequest
import com.ddaps.tamoaqui.common.domain.models.EventDataResponse
import retrofit2.http.*

interface EventsApiService {

    @GET("events")
    suspend fun getEventsList(): List<EventDataResponse>

    @POST("checkin/{event_id}")
    suspend fun postCheckIn(
            @Path("event_id") eventid: Int,
            @Body body: CheckInDataRequest
    ): CheckInDataResponseOfRequest

}