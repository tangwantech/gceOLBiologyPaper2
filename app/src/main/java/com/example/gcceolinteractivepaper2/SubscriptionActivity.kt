package com.example.gcceolinteractivepaper2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.gcceolinteractivepaper2.datamodels.PackageData
import com.example.gcceolinteractivepaper2.datamodels.PackageFormData
import com.example.gcceolinteractivepaper2.fragments.PackagesDialogFragment
import com.example.gcceolinteractivepaper2.fragments.PaymentMethodDialogFragment
import com.example.gcceolinteractivepaper2.viewmodels.SubscriptionActivityViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SubscriptionActivity: AppCompatActivity(),
    PaymentMethodDialogFragment.OnPaymentMethodDialogListeners,
    PackagesDialogFragment.PackageDialogListener{

    private var alertDialog: AlertDialog? = null

    private var packagesDialog: DialogFragment? = null
    private var paymentMethodDialog: DialogFragment? = null

    private var currentRefNum: String? = null

    private lateinit var viewModel: SubscriptionActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val subjectName = intent.getStringExtra(AppConstants.SUBJECT_NAME)
        title = "subscription"
        setupViewModel()
        setupViewObservers()
        showPackagesDialog()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[SubscriptionActivityViewModel::class.java]

        viewModel.setMomoPayService(MomoPayService(this))

    }

    private fun setupViewObservers() {
        viewModel.packageUpdateStatus.observe(this){status ->

            status?.let{
                if (it){
                    showPackageActivatedDialog()

                }

            }

        }


        setMomoPayFlow()

    }

    private fun setMomoPayFlow(){
        var pendingCount = 0
        var failCount = 0
        viewModel.transactionStatus.observe(this) {

            it?.let{status ->
                when(status) {
                    AppConstants.PENDING -> {
                        if (pendingCount == 0){
                            showRequestUserToPayDialog()
                        }
                        pendingCount += 1

                    }
                    AppConstants.SUCCESSFUL -> {
                        showPaymentReceivedDialog()
                        showActivatingPackageDialog()
                        activateUserPackage()
                    }
                    AppConstants.FAILED -> {
                        if (failCount == 0){
                            showTransactionFailedDialog()
                        }
                        failCount += 1

                    }
                }
            }


        }

    }


    private fun showProcessingRequestDialog() {
        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(this).create()
        alertDialog?.apply {
            setCancelable(false)
        }
        alertDialog?.setMessage(resources.getString(R.string.processing_request))
        alertDialog?.show()
    }

    private fun showActivatingPackageDialog() {
        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.activating_package_message))
            setCancelable(false)
        }.create()

        alertDialog?.show()

    }



    private fun showRequestUserToPayDialog() {

        val dialogView = layoutInflater.inflate(R.layout.fragment_request_to_pay, null)
        val tvRequestToPayTitle: TextView = dialogView.findViewById(R.id.tvRequestToPayTitle)
        val tvRequestToPayMessage: TextView = dialogView.findViewById(R.id.tvRequestToPayMessage)

        val tvRequestToPayPackageType: TextView =
            dialogView.findViewById(R.id.tvRequestToPayPackage)
        val tvRequestToPayPackagePrice: TextView =
            dialogView.findViewById(R.id.tvRequestToPayAmount)
        val tvTransactionId: TextView = dialogView.findViewById(R.id.tvTransactionId)

        if (viewModel.getMomoPartner() == AppConstants.MTN_MOMO) {
            tvRequestToPayTitle.setBackgroundColor(resources.getColor(R.color.mtn))
            tvRequestToPayMessage.text = resources.getString(R.string.mtn_request_to_pay_message)

        } else {
            tvRequestToPayTitle.setBackgroundColor(resources.getColor(R.color.orange))
            tvRequestToPayMessage.text = resources.getString(R.string.orange_request_to_pay_message)
        }

        tvRequestToPayPackageType.text = viewModel.getSubjectPackageType()
        tvRequestToPayPackagePrice.text = "${viewModel.getPackagePrice()} FCFA"

        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(this).create()
        alertDialog?.apply {
            setCancelable(false)
        }
        alertDialog?.setView(dialogView)
        alertDialog?.show()

    }


    private fun showPackageActivatedDialog() {
        val view = layoutInflater.inflate(
            R.layout.package_activation_successful_dialog,
            null
        )
        val tvPackageActivationSuccessful: TextView =
            view.findViewById(R.id.tvPackageActivationSuccessful)
        tvPackageActivationSuccessful.text =
            "${viewModel.getSubjectPackageType()} ${resources.getString(R.string.activated_successfully)}"

        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(this).apply {
            setView(view)
            setPositiveButton("Ok"){ _, _ ->
                exitActivity()
            }
            setCancelable(false)
        }.create()
        alertDialog?.show()
    }

    private fun showTransactionFailedDialog() {
        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(this).create()
        val view = this.layoutInflater.inflate(R.layout.package_activation_failed_dialog, null)
        val tvFailedMessage: TextView = view.findViewById(R.id.tvPackageActivationFailed)
        tvFailedMessage.text =
            "${resources.getString(R.string.failed_to_activate_package)} ${viewModel.getSubjectPackageType()} "

        alertDialog?.setView(view)
        alertDialog?.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
//            cancelFailedToActivateDialog()
            exitActivity()

        }
        alertDialog?.show()

    }


    private fun showPaymentReceivedDialog(){

        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(this).apply {
            setMessage(resources.getString(R.string.payment_received))
        }.create()
//        alertDialog?.setMessage(resources.getString(R.string.payment_received))
        alertDialog?.show()
    }

    private fun displayInvoice(){
        val view = LayoutInflater.from(this).inflate(R.layout.subscription_summary_layout, null)

        val packageName: TextView = view.findViewById(R.id.invoicePackageNameTv)
        val packagePrice: TextView = view.findViewById(R.id.invoicePackagePriceTv)
        val momoNumber: TextView = view.findViewById(R.id.invoiceMomoNumberTv)

        packageName.text = "${viewModel.getSubjectPackageType()}"
        packagePrice.text = "${viewModel.getPackagePrice()} FCFA"
        momoNumber.text = "${viewModel.getMomoNumber()}"

        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(this).apply{
            setMessage(resources.getString(R.string.verify_payment_info))
            setView(view)
            setCancelable(false)
            setPositiveButton(resources.getString(R.string.pay)  ){btn, _ ->
                showProcessingRequestDialog()
                initiatePayment()
                btn.dismiss()
            }
            setNegativeButton(resources.getString(R.string.cancel)){btn, _ ->
                exitActivity()
            }
        }.create()
        alertDialog?.show()
    }

    private fun showPackagesDialog(){
        packagesDialog = PackagesDialogFragment.newInstance()
        packagesDialog?.show(supportFragmentManager, null)
    }

    private fun showPaymentMethodDialog() {
        paymentMethodDialog = PaymentMethodDialogFragment.newInstance()
        paymentMethodDialog?.isCancelable = false
        paymentMethodDialog?.show(supportFragmentManager, null)
    }

    private fun displayEnterMomoNumberDialog(){
        val view = layoutInflater.inflate(R.layout.momo_number_dialog, null)
        val etMoMoNumber: TextInputEditText = view.findViewById(R.id.etMomoNumber)

        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(this).create()
        alertDialog?.setTitle(resources.getString(R.string.enter_number))
        alertDialog?.setView(view)
        alertDialog?.setCancelable(false)
        alertDialog?.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.next)){ _, _ ->
            displayInvoice()
        }
        alertDialog?.setButton(AlertDialog.BUTTON_NEGATIVE,resources.getString(R.string.cancel)){ _, _ ->
            exitActivity()
        }
        alertDialog?.show()

        val btnPositive = alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositive?.isEnabled = false

        etMoMoNumber.doOnTextChanged { text, _, _, _ ->
            if(text.toString().isNotEmpty() && text.toString().length == 9){
//                subscriptionFormData.momoNumber = text.toString()
                viewModel.updateMomoNumber(text.toString())
                btnPositive?.isEnabled = true

            }else{
                btnPositive?.isEnabled = false
            }
        }
    }

    private fun activateUserPackage() {
        viewModel.activateSubjectPackage()
    }

    override fun onPackageDialogNextButtonClicked() {
        packagesDialog?.dismiss()
        packagesDialog = null
        showPaymentMethodDialog()
    }

    override fun onPackageDialogCancelButtonClicked() {
        exitActivity()
    }

    override fun onPackageItemSelected(packageFormData: PackageFormData) {
        viewModel.updateSubscriptionPackageTypePriceAndDuration(packageFormData)
    }

    private fun exitActivity(){
        finish()
    }

    private fun initiatePayment(){
        viewModel.initiatePayment()

    }

    companion object{
        fun getIntent(context: Context): Intent {
            val intent = Intent(context, SubscriptionActivity::class.java)
            return intent
        }
    }

    override fun onPaymentMethodNextButtonClicked() {
        paymentMethodDialog?.dismiss()
        displayEnterMomoNumberDialog()
    }

    override fun onPaymentTypeSelected(momoPartner: String) {
        viewModel.updateMoMoPartner(momoPartner)
    }

    override fun onPaymentMethodCancelButtonClicked(){
        exitActivity()
    }

}