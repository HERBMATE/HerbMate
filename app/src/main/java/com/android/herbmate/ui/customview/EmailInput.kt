package com.android.herbmate.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.ViewGroup
import com.android.herbmate.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EmailInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var textInputLayout: TextInputLayout? = null
    private var isEmailValid: Boolean = false

    fun setTextInputLayout(layout: TextInputLayout) {
        this.textInputLayout = layout
        layout.isErrorEnabled = false
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    private fun validateEmail(email: String) {
        val layoutParams = textInputLayout?.layoutParams
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayout?.error = context.getString(R.string.email_handling)
            isEmailValid = false
        } else {
//            reset()
            textInputLayout?.error = null
            isEmailValid = true
        }

        if (isEmailValid) {
            layoutParams?.height = dpToPx(75)
            textInputLayout?.layoutParams = layoutParams
        } else {
            layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            textInputLayout?.layoutParams = layoutParams
        }
    }

//    private fun reset(){
//        val layoutParams = textInputLayout?.layoutParams
//        // Periksa apakah layoutParams tidak null
//        if (layoutParams == null) {
//            layoutParams.height = 65
//            textInputLayout?.layoutParams = layoutParams // Terapkan kembali layoutParams ke textInputLayout
//        }
//    }
}