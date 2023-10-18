package com.example.routee_commerce.ui.userAuthentication.fragments.register

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routee_commerce.api.ApiManger
import com.example.routee_commerce.api.model.User
import com.example.routee_commerce.api.model.UserResponse
import com.example.routee_commerce.ui.userAuthentication.sessionProvider.SessionProvider
import com.example.routee_commerce.utlis.Message
import com.example.routee_commerce.utlis.SingleLiveEvent
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {
    val username = MutableLiveData<String>("ziad")
    val phoneNumber = MutableLiveData<String>("01010101010")
    val email = MutableLiveData<String>("ziad.fathi.seleem@gmail.com")
    val password = MutableLiveData<String>("Ziads3223@")
    val passwordConfirmation = MutableLiveData<String>("Ziads3223@")

    val usernameError = MutableLiveData<String?>()
    val phoneNumberError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmationError = MutableLiveData<String?>()

    val shouldClearFocus = MutableLiveData<Boolean>()
    val events = SingleLiveEvent<RegisterViewEvents>()
    val hideKeyboard = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<Message>()


    fun register() {
        shouldClearFocus.postValue(true)
        hideKeyboard.postValue(true)
        if (!validateForm()) return
        val user = User(
            username.value,
            phoneNumber.value,
            email.value,
            password.value,
            passwordConfirmation.value
        )
        viewModelScope.launch {
            try {
                val response = ApiManger.getApis().registerUser(user)
                SessionProvider.user = response.user
                messageLiveData.postValue(Message("User Registered successfully"))
                //navigate to home screen
                events.postValue(RegisterViewEvents.NavigateToHome)

            } catch (e: HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                val response = Gson().fromJson(jsonString, UserResponse::class.java)
                messageLiveData.postValue(Message(response.errors?.msg ?: response.message))
            } catch (e: Exception) {
                messageLiveData.postValue(Message(e.localizedMessage))
            }
        }

    }


    fun onLoginClicked() {
        events.postValue(RegisterViewEvents.NavigateToLogin)
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

    fun onOutsideClick() {
        hideKeyboard.postValue(true)
    }

}
