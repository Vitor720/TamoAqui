package com.ddaps.tamoaqui.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ddaps.tamoaqui.R
import com.ddaps.tamoaqui.common.domain.models.Event
import com.ddaps.tamoaqui.common.domain.models.EventDataResponse

fun List<EventDataResponse>.mapForView(): List<Event>{
    val eventList = this.map {
        Event(it.id,
            it.name ?: "",
            it.image ?: "",
            it.address ?: "",
            it.details ?: "",
            it.date ?:"",
            it.entry_fee ?: FREE_ENTRY,
            it.time ?: ""
        )
        }
        val sortedEvents = eventList.sortedBy { it.date }
        return sortedEvents
}


@BindingAdapter("imageUrl")
fun ImageView.load(imagemUrl: String?) {
    val imageView = this
    val errorImage = imageView.context.resources.getDrawable(R.drawable.errorimage)
    val requestOptions = Glide.with(imageView.context)
                              .applyDefaultRequestOptions(RequestOptions().error(errorImage))
                              .load(imagemUrl)
                              .transition(DrawableTransitionOptions.withCrossFade(299))
    requestOptions.into(imageView)
}
