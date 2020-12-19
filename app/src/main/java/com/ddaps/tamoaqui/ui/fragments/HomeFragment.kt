package com.ddaps.tamoaqui.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ddaps.tamoaqui.R
import com.ddaps.tamoaqui.common.domain.Status
import com.ddaps.tamoaqui.common.domain.models.Event
import com.ddaps.tamoaqui.common.domain.models.Resource
import com.ddaps.tamoaqui.databinding.FragmentHomeBinding
import com.ddaps.tamoaqui.ui.adapters.EventAdapter
import com.ddaps.tamoaqui.ui.viewModel.EventViewModel
import com.ddaps.tamoaqui.util.EventClickListener
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), EventClickListener {

    private val viewModel: EventViewModel by viewModel()
    private var binding: FragmentHomeBinding? = null
    private var adapter: EventAdapter? = null

    private val observer = Observer<Resource<List<Event>>> {
        when (it.status) {
            Status.SUCCESS -> {
                binding?.loading?.visibility = View.INVISIBLE
                loadRecycler(it.data!!)
            }
            Status.ERROR   -> {
                Snackbar.make(
                    binding?.root!!,
                    "Ops, Somenthing went wrong",
                    Snackbar.LENGTH_SHORT
                )
            }

            Status.LOADING -> { binding?.loading?.visibility = View.VISIBLE }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getEventsLiveData().observe(viewLifecycleOwner, observer)
        viewModel.loadEventsList()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun loadRecycler(list: List<Event>){
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.eventListRecycler?.layoutManager = layoutManager
        adapter = EventAdapter(list, this)
        binding?.eventListRecycler?.adapter = adapter
    }

    override fun onClick(event: Event) {
        val navigateToDisplayEvent = HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment(event)
        findNavController().navigate(navigateToDisplayEvent)
    }
}