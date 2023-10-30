package com.example.routee_commerce.ui.userAuthentication.fragments.login

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
import com.example.routee_commerce.databinding.FragmentLoginBinding
import com.example.routee_commerce.ui.home.activity.MainActivity
import com.example.routee_commerce.utlis.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var viewBinding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        hideKeyboard()
        subscribeToLiveData()
        initViews()
    }

    private fun initViews() {
        viewBinding.loginBtn.setOnClickListener {
            viewModel.invokeAction(LoginContract.Action.LoginUser)
        }
        viewBinding.donTHaveAnAccountTv.setOnClickListener {
            viewModel.invokeAction(LoginContract.Action.RegisterClicked)
        }
        viewBinding.constraintOutside.setOnClickListener {
            viewModel.invokeAction(LoginContract.Action.OutSideClicked)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.states.observe(viewLifecycleOwner, ::handleStates)
    }

    private fun handleStates(state: LoginContract.State) {
        when (state) {
            is LoginContract.State.Error -> {
                showErrorView(state.message)
            }

            LoginContract.State.Loading -> {
                showLoadingView()
            }

            LoginContract.State.Success -> {
                showSuccessView()
            }
        }
    }

    private fun handleEvents(event: LoginContract.Event) {
        when (event) {
            LoginContract.Event.HideKeyboard -> hideKeyboard()
            LoginContract.Event.NavigateToHome -> navigateToHome()
            LoginContract.Event.NavigateToRegister -> navigateToRegister()
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


    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
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