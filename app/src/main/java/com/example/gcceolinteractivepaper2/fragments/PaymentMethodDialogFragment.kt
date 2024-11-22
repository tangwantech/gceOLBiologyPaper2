package com.example.gcceolinteractivepaper2.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.R


class PaymentMethodDialogFragment : DialogFragment() {
    private lateinit var onPaymentMethodDialogListeners: OnPaymentMethodDialogListeners

    private lateinit var rbMtn: RadioButton
    private lateinit var rbOrange: RadioButton
    private lateinit var rgPaymentMethod: RadioGroup

    private var positiveBtn: Button? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPaymentMethodDialogListeners) {
            onPaymentMethodDialogListeners = context
        }

    }


    private fun initViews(): View{

        val view = requireActivity().layoutInflater.inflate(R.layout.payment_method, null)

        rgPaymentMethod = view.findViewById(R.id.rgPaymentMethod)

        rbMtn = view.findViewById(R.id.rbMtn)
        rbOrange = view.findViewById(R.id.rbOrange)

        return view
    }

    private fun setUpViewListeners(){

        rgPaymentMethod.setOnCheckedChangeListener { _, id ->
            when(id){
                R.id.rbMtn -> {

                    onPaymentMethodDialogListeners.onPaymentTypeSelected(AppConstants.MTN_MOMO)

                }

                R.id.rbOrange -> {

                    onPaymentMethodDialogListeners.onPaymentTypeSelected(AppConstants.ORANGE_MOMO)
                }

            }
            positiveBtn?.isEnabled = true
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = initViews()
        setUpViewListeners()
        return setUpAlertDialog(view)
    }
    private fun setUpAlertDialog(view: View): Dialog {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(requireContext().resources.getString(R.string.select_payment_method))
//            setMessage()
            setView(view)
            setPositiveButton(requireContext().resources.getString(R.string.next)){ btn, _ ->
                onPaymentMethodDialogListeners.onPaymentMethodNextButtonClicked()
                btn.dismiss()
            }
            setNegativeButton(requireContext().resources.getString(R.string.cancel)) { btn, _ ->
                onPaymentMethodDialogListeners.onPaymentMethodCancelButtonClicked()
            }
        }.create()

        builder.setOnShowListener {
            positiveBtn = builder.getButton(AlertDialog.BUTTON_POSITIVE)
//            val positiveBtn = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveBtn?.isEnabled = false



        }
        return builder
    }

    companion object {
        fun newInstance(): DialogFragment {
            val paymentMethodDialogFragment = PaymentMethodDialogFragment()
            val bundle = Bundle().apply {
            }
            paymentMethodDialogFragment.arguments = bundle
            return paymentMethodDialogFragment
        }
    }


    interface OnPaymentMethodDialogListeners {
        fun onPaymentMethodNextButtonClicked()
        fun onPaymentMethodCancelButtonClicked()
        fun onPaymentTypeSelected(momoPartner: String)
    }
}