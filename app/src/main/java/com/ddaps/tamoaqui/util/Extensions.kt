package com.ddaps.tamoaqui.util

import com.ddaps.tamoaqui.common.domain.models.Event
import com.ddaps.tamoaqui.common.domain.models.EventDataResponse

fun List<EventDataResponse>.mapForView(): List<Event>{
    val eventList = this.map {
        Event(
            it.id ?: -999,
            it.name ?: "",
            it.image ?: "",
            it.address ?: "",
            it.details ?: "",
            it.date ?:""
            )
        }
        val sortedEvents = eventList.sortedBy { it.date }
        return sortedEvents
}


