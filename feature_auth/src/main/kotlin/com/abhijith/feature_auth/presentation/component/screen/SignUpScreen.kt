package com.abhijith.feature_auth.presentation.component.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.abhijith.core.utility.InputState
import com.abhijith.feature_auth.R
//import com.abhijith.core.ViewBindingFragment
import com.abhijith.feature_auth.databinding.LoginScreenLayoutBinding
import com.abhijith.feature_auth.databinding.SignupScreenLayoutBinding
import com.abhijith.feature_auth.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpScreen : Fragment() {

    private var _binding: SignupScreenLayoutBinding? = null

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

        viewBinding.loginElements.etEmailInput.doOnTextChanged { text, start, before, count ->
            loginViewModel.onEmailChanged(text.toString())
        }
        viewBinding.loginElements.btnSignUp.setOnClickListener {
            loginViewModel.onLoginClick()
        }
        viewBinding.loginElements.btnGotoLogin.setOnClickListener {
            findNavController().navigate(R.id.nav_sign_up_to_login_screen)
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                onViewLifecycleMoveToStart()
            }
        }
    }

    private suspend fun onViewLifecycleMoveToStart() {
        coroutineScope {
            launch {
                loginViewModel.emailValidationErrorMessage.collect {
                    when (it) {
                        InputState.BLANK -> {
                            //ignore
                        }
                        InputState.INVALID -> {
                            viewBinding.loginElements.tvEmailInputError.text = requireContext().getString(R.string.invalid_email_id)
                        }
                        InputState.EXISTS -> {
                            viewBinding.loginElements.tvEmailInputError.text = requireContext().getString(R.string.existing_email_id)
                        }
                        InputState.VALID -> {
                            viewBinding.loginElements.tvEmailInputError.text = ""
                        }
                        InputState.PROHIBITED -> {
                            //ignore
                        }
                        InputState.UN_INITIALIZED -> {
                            //ignore
                        }
                    }
                }
            }
        }
    }

    private fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): SignupScreenLayoutBinding {
        return SignupScreenLayoutBinding.inflate(inflater, container, false).apply {
            _binding = this
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}