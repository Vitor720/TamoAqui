package com.ddaps.tamoaqui.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ddaps.tamoaqui.R
import com.ddaps.tamoaqui.common.domain.Status
import com.ddaps.tamoaqui.common.domain.models.Event
import com.ddaps.tamoaqui.databinding.FragmentEventDetailsBinding
import com.ddaps.tamoaqui.ui.viewModel.EventViewModel
import com.ddaps.tamoaqui.util.esconder
import com.ddaps.tamoaqui.util.mostrar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_event_checkin.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailsFragment : Fragment() {

    private val viewModel: EventViewModel by viewModel()
    private var binding: FragmentEventDetailsBinding? = null
    private val args: EventDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val displayEvent = args.eventData
        binding?.event = displayEvent
        binding?.lifecycleOwner = this

        startShareEventListener(displayEvent)
        startCheckInEventListener(displayEvent.id)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun startShareEventListener(event: Event){
        binding?.share?.setOnClickListener { shareEvent(event.name, event.details) }
    }

    private fun startCheckInEventListener(eventID: Int){
        binding?.checkin?.setOnClickListener { displayConfirmationDialog(eventID) }
    }

    private fun shareEvent(eventName: String, description: String){
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.putExtra(Intent.EXTRA_SUBJECT, description)
        startActivity(Intent.createChooser(share, eventName))
    }

    private fun displayConfirmationDialog(eventID: Int) {
        viewModel.getEventsLiveData().removeObservers(viewLifecycleOwner)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_event_checkin)
        dialog.setCancelable(true)
        val layoutXY = WindowManager.LayoutParams()
        layoutXY.copyFrom(dialog.window!!.attributes)
        layoutXY.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutXY.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.btn_cancel.setOnClickListener { dialog.dismiss() }
        dialog.btn_submit.setOnClickListener {
            val inValidInput: Boolean = dialog.user_name.text.isNullOrEmpty() || dialog.user_email.text.isNullOrEmpty()
            if (inValidInput){
                Snackbar.make(
                        binding!!.root,
                        "Favor preencher corretamente os campos para fazer check-in",
                        Snackbar.LENGTH_LONG
                ).show()
            } else{
                viewModel.getCheckInResponse().observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        Status.SUCCESS -> {
                            dialog.checkin_loading_response.esconder()
                            dialog.user_data_layout.mostrar()
                            dialog.buttons_layout.mostrar()
                            dialog.dismiss()
                            binding?.checkin?.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_checked_event))
                            Snackbar.make(binding!!.root, "Check-in realizado", Snackbar.LENGTH_SHORT).show()
                        }
                        Status.ERROR   -> { Snackbar.make(dialog.btn_submit,
                                "Falha ao realizar check-in, ${it.message}.",
                                Snackbar.LENGTH_LONG).show()
                            dialog.checkin_loading_response.esconder()
                            dialog.user_data_layout.mostrar()
                            dialog.buttons_layout.mostrar()
                        }
                        Status.LOADING -> {
                            dialog.checkin_loading_response.visibility = View.VISIBLE
                            dialog.user_data_layout.esconder()
                            dialog.buttons_layout.esconder()
                        }
                    }
                })
                viewModel.makeUserCheckInOnEventByID(eventID, dialog.user_name.text.toString() , dialog.user_email.text.toString())
            }
        }
        dialog.show()
        dialog.window!!.attributes = layoutXY
    }

}