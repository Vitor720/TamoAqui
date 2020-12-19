package com.ddaps.tamoaqui.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ddaps.tamoaqui.R
import com.ddaps.tamoaqui.common.domain.models.Event
import com.ddaps.tamoaqui.databinding.RowEventBinding
import com.ddaps.tamoaqui.util.EventClickListener

class EventAdapter(val eventsList: List<Event>, private val clickListener: EventClickListener): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: RowEventBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = eventsList[position]
        holder.bind(item)
    }

    inner class ViewHolder internal constructor(private val binding: RowEventBinding): RecyclerView.ViewHolder(binding.root){

        private val rowLayout = binding.rowLayout

        fun bind(item: Event){
            binding.event = item
            binding.executePendingBindings()
        }

        init {
            rowLayout.setOnClickListener {
                clickListener.onClick(eventsList[adapterPosition])
            }
        }
    }

}
