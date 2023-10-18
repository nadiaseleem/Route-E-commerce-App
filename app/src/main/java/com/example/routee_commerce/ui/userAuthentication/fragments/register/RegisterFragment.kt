package com.example.routee_commerce.ui.userAuthentication.fragments.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.routee_commerce.R
import com.example.routee_commerce.databinding.FragmentRegisterBinding
import com.example.routee_commerce.ui.home.activity.MainActivity
import com.example.routee_commerce.utlis.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private lateinit var viewBinding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        hideKeyboard()
        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.hideKeyboard.observe(viewLifecycleOwner) { hide ->
            if (hide)
                hideKeyboard()
        }
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT)
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
        }

    }

    private fun handleEvents(registerViewEvent: RegisterViewEvents) {
        when (registerViewEvent) {
            RegisterViewEvents.NavigateToHome -> navigateToHome()
            RegisterViewEvents.NavigateToLogin -> navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment3)
    }

    private fun navigateToHome() {
        startActivity(Intent(activity, MainActivity::class.java))
        requireActivity().finish()
    }


    private fun hideKeyboard() {
        view?.hideKeyboard(activity as AppCompatActivity?)
    }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding.unbind()
    }

}