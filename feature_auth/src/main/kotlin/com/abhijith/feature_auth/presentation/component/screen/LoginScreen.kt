package com.abhijith.feature_auth.presentation.component.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhijith.core.ViewBindingFragment
import com.abhijith.feature_auth.databinding.LoginScreenLayoutBinding

class LoginScreen: ViewBindingFragment<LoginScreenLayoutBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LoginScreenLayoutBinding {
        return LoginScreenLayoutBinding.inflate(inflater, container, false)
    }

}