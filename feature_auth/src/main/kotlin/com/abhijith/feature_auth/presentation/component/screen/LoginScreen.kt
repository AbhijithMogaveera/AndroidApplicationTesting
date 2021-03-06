package com.abhijith.feature_auth.presentation.component.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
//import com.abhijith.core.ViewBindingFragment
import com.abhijith.feature_auth.databinding.LoginScreenLayoutBinding
import com.abhijith.feature_auth.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen: Fragment() {

    private var _binding:LoginScreenLayoutBinding?=null

    val viewBinding get() = _binding!!

    val loginViewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return getBinding(inflater, container, savedInstanceState).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LoginScreenLayoutBinding {
        return LoginScreenLayoutBinding.inflate(inflater, container, false).apply {
            _binding = this
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}