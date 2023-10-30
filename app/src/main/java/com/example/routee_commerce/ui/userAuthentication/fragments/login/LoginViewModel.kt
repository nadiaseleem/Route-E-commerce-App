package com.example.routee_commerce.ui.userAuthentication.fragments.login

import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.User
import com.example.domain.usecase.LoginUserUseCase
import com.example.routee_commerce.utlis.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel(), LoginContract.ViewModel {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    val shouldClearFocus = MutableLiveData<Boolean>()

    private val _states = MutableLiveData<LoginContract.State>()
    override val states = _states

    private val _events = SingleLiveEvent<LoginContract.Event>()
    override val events = _events

    override fun invokeAction(action: LoginContract.Action) {
        when (action) {
            LoginContract.Action.LoginUser -> {
                login()
            }

            LoginContract.Action.OutSideClicked -> {
                _events.postValue(LoginContract.Event.HideKeyboard)
            }

            LoginContract.Action.RegisterClicked -> {
                _events.postValue(LoginContract.Event.NavigateToRegister)
            }
        }

    }

    private fun login() {
        shouldClearFocus.postValue(true)
        _events.postValue(LoginContract.Event.HideKeyboard)
        if (!validateForm()) return
        val user = createUser()
        viewModelScope.launch(Dispatchers.IO) {
            loginUserUseCase.invoke(user).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _states.postValue(LoginContract.State.Loading)
                    }

                    is ResultWrapper.Error -> {
                        _states.postValue(
                            LoginContract.State.Error(
                                result.error.localizedMessage ?: ""
                            )
                        )
                    }

                    is ResultWrapper.ServerError -> {
                        _states.postValue(
                            LoginContract.State.Error(
                                result.error.msg
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        with(sharedPreferences.edit()) {
                            putString("loginToken", result.data)
                            apply()
                        }
                        _states.postValue(LoginContract.State.Success)
                        _events.postValue(LoginContract.Event.NavigateToHome)
                    }
                }
            }
        }
    }

    private fun createUser(): User {
        return User(
            email = email.value,
            password = password.value,
        )
    }

    private fun validateForm(): Boolean {
        var valid = true
        if (email.value.isNullOrBlank()) {
            valid = false
            emailError.postValue("please enter email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()) {
            valid = false
            emailError.postValue("please enter a valid email address")
        } else {
            emailError.postValue(null)
        }
        if (password.value.isNullOrBlank()) {
            valid = false
            passwordError.postValue("please enter password")
        } else if (password.value?.length!! < 8) {
            valid = false
            passwordError.postValue("Minimum 8 characters password")
        } else if (!password.value!!.matches(".*[A-Z].*".toRegex())) {
            valid = false
            passwordError.postValue("Must Contain 1 Upper-cae Character")
        } else if (!password.value!!.matches(".*[a-z].*".toRegex())) {
            valid = false
            passwordError.postValue("Must Contain 1 Lower-cae Character")
        } else if (!password.value!!.matches(".*[@#\$%^&+=].*".toRegex())) {
            valid = false
            passwordError.postValue("Must Contain 1 Special Character (@#\$%^&+=)")
        } else if (!password.value!!.matches(".*[0-9].*".toRegex())) {
            valid = false
            passwordError.postValue("Must Contain 1 number")
        } else {
            passwordError.postValue(null)
        }
        return valid
    }

}