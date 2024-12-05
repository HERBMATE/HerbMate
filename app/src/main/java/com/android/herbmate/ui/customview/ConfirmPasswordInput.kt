package com.android.herbmate.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import com.android.herbmate.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ConfirmPasswordInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var textInputLayout: TextInputLayout? = null
    private var isPasswordValid: Boolean = false
    private var password: String? = null // Menyimpan password yang dimasukkan

    fun setTextInputLayout(layout: TextInputLayout) {
        this.textInputLayout = layout
        layout.isErrorEnabled = false
    }

    fun setPassword(password: String) {
        this.password = password
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("ConfirmPasswordInput", "Input changed: $s")
                validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    private fun validatePassword(confirmPassword: String) {
        Log.d("ConfirmPasswordInput", "Validating password. Confirm Password: $confirmPassword, Original Password: $password")

        val layoutParams = textInputLayout?.layoutParams
        if (confirmPassword.isEmpty()) {
            textInputLayout?.error = context.getString(R.string.confirm_password_empty)
            isPasswordValid = false
            Log.d("ConfirmPasswordInput", "Validation failed: Confirm password is empty.")
        } else if (confirmPassword != password) {
            textInputLayout?.error = context.getString(R.string.confirm_password_handling)
            isPasswordValid = false
            Log.d("ConfirmPasswordInput", "Validation failed: Passwords do not match.")
        } else {
            textInputLayout?.error = null
            isPasswordValid = true
            Log.d("ConfirmPasswordInput", "Validation succeeded: Passwords match.")
        }

        // Mengatur tinggi layout berdasarkan validasi
        if (isPasswordValid) {
            layoutParams?.height = dpToPx(75) // Atur tinggi jika valid
        } else {
            layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT // Atur tinggi ke wrap_content jika tidak valid
        }
        textInputLayout?.layoutParams = layoutParams // Terapkan perubahan layoutParams
    }
}