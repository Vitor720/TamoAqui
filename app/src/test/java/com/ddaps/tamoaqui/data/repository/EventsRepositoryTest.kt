package com.ddaps.tamoaqui.data.repository

import com.ddaps.tamoaqui.common.domain.ResponseHandler
import com.ddaps.tamoaqui.common.domain.models.CheckInDataRequest
import com.ddaps.tamoaqui.common.domain.models.CheckInDataResponseOfRequest
import com.ddaps.tamoaqui.common.domain.models.EventDataResponse
import com.ddaps.tamoaqui.data.remote.EventsApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response

@RunWith(JUnit4::class)
class EventsRepositoryTest {

    private lateinit var eventsRepo: EventsRepository
    private lateinit var mockEventsApi: EventsApiService
    private lateinit var checkInResponse: CheckInDataResponseOfRequest
    private lateinit var checkInRequest: CheckInDataRequest

    @Before
    fun start(){
        checkInResponse = CheckInDataResponseOfRequest(1)
        checkInRequest = CheckInDataRequest(1, "vitor", "vitor@gmail.com")
        mockEventsApi = mockk()
        eventsRepo = EventsRepository(mockEventsApi, ResponseHandler())
    }

    @Test
    fun `getEventsList - should retrieve expected data type`() = run {
        coEvery { mockEventsApi.getEventsList() } returns listOf<EventDataResponse>()
        runBlocking {
            val response = eventsRepo.getEventsList()
            Assert.assertTrue("Tipo incorreto", response.data is List<EventDataResponse>)
        }
    }

    @Test
    fun `postCheckIn - should return expected data type`() = runBlocking {
        coEvery { mockEventsApi.postCheckIn(checkInRequest.event_id, checkInRequest)} returns checkInResponse
        val response = eventsRepo.postCheckIn(checkInRequest)
        Assert.assertEquals( "Tipo incorreto", checkInResponse, response.data)
    }
}