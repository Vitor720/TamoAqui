package com.ddaps.tamoaqui.data.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EventsRepositoryTest {

    private lateinit var eventsRepo: EventsRepository
    private lateinit var mockEventsApi: EventsApiService

    @Before
    fun start(){
        mockEventsApi = mockk()
        eventsRepo = EventsRepository(mockEventsApi)
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
    fun `postCheckIn - should return expected data type`() = run {
        coEvery { mockEventsApi.postCheckIn(any()) } returns listOf<CheckInDataResponseOfRequest>()
        runBlocking {
            val response = eventsRepo.getEventsList()
            Assert.assertTrue("Tipo incorreto", response.data is EventDataResponse)
        }
    }

}