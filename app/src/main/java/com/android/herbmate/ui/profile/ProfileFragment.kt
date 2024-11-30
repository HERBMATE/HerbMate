package com.android.herbmate.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.android.herbmate.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Simpan tema
    private fun saveThemePreference(isDarkMode: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("dark_mode", isDarkMode)
        editor.apply()
    }
}