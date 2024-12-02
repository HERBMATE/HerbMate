package com.android.herbmate.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.herbmate.ViewModelFactory
import com.android.herbmate.databinding.FragmentProfileBinding
import com.android.herbmate.ui.login.LoginActivity


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        binding.switchDarkMode.isChecked = isDarkMode
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Aktifkan Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveThemePreference(true)
            } else {
                // Aktifkan Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveThemePreference(false)
            }
        }

        binding.btnLogOut.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        viewModel.userSession.observe(viewLifecycleOwner) { user ->
            user?.let {

                binding.edEmail.text = Editable.Factory.getInstance().newEditable(it.email ?: "")
                binding.edName.text = Editable.Factory.getInstance().newEditable(it.name ?: "")

            } ?: run {
                binding.edEmail.text = Editable.Factory.getInstance().newEditable("")
                binding.edName.text = Editable.Factory.getInstance().newEditable("")
//                binding.edPassword.text = Editable.Factory.getInstance().newEditable("")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveThemePreference(isDarkMode: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("dark_mode", isDarkMode)
        editor.apply()
    }

}