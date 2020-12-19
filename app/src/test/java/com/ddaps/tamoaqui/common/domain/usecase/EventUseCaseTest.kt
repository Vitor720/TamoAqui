package com.ddaps.tamoaqui.common.domain.usecase

import com.ddaps.tamoaqui.common.domain.Status
import com.ddaps.tamoaqui.common.domain.models.*
import com.ddaps.tamoaqui.data.repository.EventsRepository
import com.ddaps.tamoaqui.util.FREE_ENTRY
import com.ddaps.tamoaqui.util.INTERNET_FAILURE
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EventUseCaseTest {

    private lateinit var mockEventsRepository: EventsRepository
    private lateinit var eventUseCase : EventUseCase
    private lateinit var mockEventResponseList : List<EventDataResponse>
    private lateinit var mockCheckInResponse: CheckInDataResponseOfRequest
    private lateinit var mockCheckInRequest: CheckInDataRequest
    private lateinit var mockEventsList: List<Event>

    @Before
    fun setup(){
        mockEventsRepository = mockk()
        eventUseCase = EventUseCase(mockEventsRepository)
        mockEventsList = listOf(Event(2, "Encontro", "image.jpg", "online", "sem detalhes", "12/12/2021", FREE_ENTRY, "11:00 ~ 13:00"))
        mockEventResponseList = listOf(EventDataResponse(2, "Encontro", "image.jpg", "online", "sem detalhes", "12/12/2021", FREE_ENTRY, "11:00 ~ 13:00"))
        mockCheckInResponse = mockk()
        mockCheckInRequest = CheckInDataRequest(1, "Joao Vitor", "jv_2020@gmail.com")
    }

    @Test
    fun `getEventsList - on repository success - should return expected data type`() = runBlocking {
        coEvery { mockEventsRepository.getEventsList() } returns Resource.success(mockEventResponseList)
        val response = eventUseCase.getEventsList()
        Assert.assertEquals("Tipo incorreto", mockEventsList, response.data)
        Assert.assertEquals("Status Incorreto", Status.SUCCESS, response.status)
        coVerify(exactly = 1) { mockEventsRepository.getEventsList() }
    }

    @Test
    fun `getEventsList - on repository failure - should return expected data`() = runBlocking {
        coEvery { mockEventsRepository.getEventsList() } returns Resource.error(INTERNET_FAILURE, null)
        val response = eventUseCase.getEventsList()
        Assert.assertEquals("Status Incorreto", Status.ERROR, response.status)
        coVerify(exactly = 1) { mockEventsRepository.getEventsList() }
    }

    @Test
    fun `postCheckIn - on repository success - should return expected data type`() = runBlocking {
        coEvery { mockEventsRepository.postCheckIn(any()) } returns Resource.success(mockCheckInResponse)
        val response = eventUseCase.postCheckIn(mockCheckInRequest.event_id, mockCheckInRequest.user_name, mockCheckInRequest.email)
        Assert.assertEquals("Tipo incorreto", mockCheckInResponse, response.data)
        Assert.assertEquals("Status Incorreto", Status.SUCCESS, response.status)
    }

    @Test
    fun `postCheckIn - on repository failure - should return expected data`() = runBlocking {
        coEvery { mockEventsRepository.postCheckIn(any()) } returns Resource.error(INTERNET_FAILURE, null)
        val response = eventUseCase.postCheckIn(mockCheckInRequest.event_id, mockCheckInRequest.user_name, mockCheckInRequest.email)
        Assert.assertEquals("Status Incorreto", Status.ERROR, response.status)
    }

}