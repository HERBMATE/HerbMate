package com.android.herbmate.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.herbmate.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PasswordInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var textInputLayout: TextInputLayout? = null
    private var isConfirmPassword: Boolean = false
    private var otherPasswordInput: PasswordInput? = null
    private var isPasswordValid: Boolean = false

    fun setTextInputLayout(layout: TextInputLayout) {
        this.textInputLayout = layout
        layout.isErrorEnabled = false
    }

    fun setOtherPasswordInput(otherInput: PasswordInput) {
        this.otherPasswordInput = otherInput
        otherInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    private fun validatePassword(input: String) {
        val layoutParams = textInputLayout?.layoutParams

        if (!isConfirmPassword) {
            // Validasi password utama
            if (input.length < 8) {
                textInputLayout?.error = context.getString(R.string.password_handling) // "Password terlalu pendek"
                isPasswordValid = false
                layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT // Tinggi dinamis saat error
            } else {
                textInputLayout?.error = null
                isPasswordValid = true
                layoutParams?.height = dpToPx(75) // Tinggi tetap jika valid
            }
        } else {
            // Validasi Confirm Password
            val mainPassword = otherPasswordInput?.text.toString()
            if (input != mainPassword) {
                textInputLayout?.error = context.getString(R.string.confirm_password_handling)
                isPasswordValid = false
                layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                textInputLayout?.error = null
                isPasswordValid = true
                layoutParams?.height = dpToPx(75)
            }
        }

        textInputLayout?.layoutParams = layoutParams
    }

    fun setAsConfirmPassword() {
        isConfirmPassword = true
    }
}