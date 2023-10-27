package com.example.routee_commerce.ui.userAuthentication.fragments.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.routee_commerce.R
import com.example.routee_commerce.databinding.FragmentRegisterBinding
import com.example.routee_commerce.ui.home.activity.MainActivity
import com.example.routee_commerce.utlis.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var viewBinding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        initViews()
    }

    private fun initViews() {
        viewBinding.registerBtn.setOnClickListener {
            viewModel.invokeAction(RegisterContract.Action.RegisterUser)
        }
        viewBinding.loginDoHaveAccountTv.setOnClickListener {
            viewModel.invokeAction(RegisterContract.Action.LoginClicked)
        }
        viewBinding.constraintOutside.setOnClickListener {
            viewModel.invokeAction(RegisterContract.Action.OutSideClicked)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.states.observe(viewLifecycleOwner, ::handleStates)
    }

    private fun handleEvents(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.NavigateToHome -> navigateToHome()
            is RegisterContract.Event.NavigateToLogin -> navigateToLogin()
            is RegisterContract.Event.HideKeyboard -> hideKeyboard()
        }
    }

    private fun handleStates(state: RegisterContract.State) {
        when (state) {
            is RegisterContract.State.Error -> {
                showErrorView(state.message)
            }

            is RegisterContract.State.Loading -> {
                showLoadingView()
            }

            is RegisterContract.State.Success -> {
                showSuccessView()
            }
        }
    }

    private fun showSuccessView() {
        viewBinding.icNext.isVisible = true
        viewBinding.progressBar.isVisible = false
    }

    private fun showErrorView(message: String) {
        viewBinding.icNext.isVisible = true
        viewBinding.progressBar.isVisible = false
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(resources.getColor(R.color.white))
            .show()
    }

    private fun showLoadingView() {
        viewBinding.icNext.isVisible = false
        viewBinding.progressBar.isVisible = true
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