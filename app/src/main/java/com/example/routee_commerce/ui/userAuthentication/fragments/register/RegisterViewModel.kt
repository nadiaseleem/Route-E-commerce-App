package com.example.routee_commerce.ui.userAuthentication.fragments.register

import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.User
import com.example.domain.usecase.RegisterUserUseCase
import com.example.routee_commerce.utlis.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel(), RegisterContract.ViewModel {

    val username = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()

    val usernameError = MutableLiveData<String?>()
    val phoneNumberError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmationError = MutableLiveData<String?>()

    val shouldClearFocus = MutableLiveData<Boolean>()

    private val _states = MutableLiveData<RegisterContract.State>()
    override val states = _states

    private val _events = SingleLiveEvent<RegisterContract.Event>()
    override val events = _events

    override fun invokeAction(action: RegisterContract.Action) {
        when (action) {
            is RegisterContract.Action.RegisterUser -> {
                register()
            }

            is RegisterContract.Action.LoginClicked -> {
                _events.postValue(RegisterContract.Event.NavigateToLogin)
            }

            is RegisterContract.Action.OutSideClicked -> {
                _events.postValue(RegisterContract.Event.HideKeyboard)
            }
        }
    }

    private fun register() {
        shouldClearFocus.postValue(true)
        _events.postValue(RegisterContract.Event.HideKeyboard)
        if (!validateForm()) return
        val user = createUser()
        viewModelScope.launch(Dispatchers.IO) {
            registerUserUseCase.invoke(user).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _states.postValue(RegisterContract.State.Loading)
                    }

                    is ResultWrapper.Error -> {
                        _states.postValue(
                            RegisterContract.State.Error(
                                result.error.localizedMessage ?: ""
                            )
                        )
                    }

                    is ResultWrapper.ServerError -> {
                        _states.postValue(
                            RegisterContract.State.Error(
                                result.error.msg
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        with(sharedPreferences.edit()) {
                            putString("token", result.data)
                            apply()
                        }
                        _states.postValue(RegisterContract.State.Success)
                        _events.postValue(RegisterContract.Event.NavigateToHome)
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        if (username.value.isNullOrBlank()) {
            valid = false
            usernameError.postValue("please enter username")
        } else {
            usernameError.postValue(null)
        }
        if (phoneNumber.value.isNullOrBlank()) {
            valid = false
            phoneNumberError.postValue("please enter valid mobile number")
        } else if (phoneNumber.value!!.length != 11) {
            valid = false
            phoneNumberError.postValue("A valid mobile number should be 11 numbers")
        } else {
            phoneNumberError.postValue(null)
        }
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
        if (passwordConfirmation.value.isNullOrBlank()) {
            valid = false
            passwordConfirmationError.postValue("please reenter password")
        } else if (passwordConfirmation.value != password.value) {
            valid = false
            passwordConfirmationError.postValue("password doesn't match")
        } else {
            passwordConfirmationError.postValue(null)
        }

        return valid
    }

    private fun createUser(): User {
        return User(
            username.value,
            phoneNumber.value,
            email.value,
            password.value,
            passwordConfirmation.value
        )
    }
}
