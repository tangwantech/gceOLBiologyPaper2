package com.example.gcceolinteractivepaper2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

class OrderedTypeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    companion object {
        fun newInstance(bundle: Bundle) =
            OrderedTypeFragment().apply {
                arguments = bundle
            }
    }


}