package com.abhijith.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<T:ViewBinding>:Fragment() {

    private var _binding:T?=null
    val viewBinding get() = _binding!!

    abstract fun getBinding( inflater: LayoutInflater,
                             container: ViewGroup?,
                             savedInstanceState: Bundle?):T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater, container, savedInstanceState)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}