package com.example.routee_commerce.utlis

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:Error")
fun setTextInputLayoutError(textInputLayout: TextInputLayout, error: String?) {
    textInputLayout.error = error
    textInputLayout.errorIconDrawable = null
}

@BindingAdapter("app:clearFocusOnCondition")
fun clearFocusOnCondition(view: View, condition: Boolean) {
    if (condition) {
        view.clearFocus()
    }
}


