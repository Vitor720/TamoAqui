package com.ddaps.tamoaqui.common.domain.models

import android.os.Parcel
import android.os.Parcelable
import com.ddaps.tamoaqui.util.FREE_ENTRY

data class Event(val id: Int,
                 val name: String,
                 val image: String,
                 val address: String,
                 val details: String,
                 val date: String,
                 val entryFee: Double,
                 val time: String): Parcelable {

    fun getEventTimeRange(): String = time

    fun getEntryFee(): String{
        return if (entryFee > FREE_ENTRY){
            "R$ $entryFee"
        }else{
            "Gratuito"
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(address)
        parcel.writeString(details)
        parcel.writeString(date)
        parcel.writeDouble(entryFee)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}