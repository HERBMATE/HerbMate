package com.android.herbmate.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.databinding.FragmentProfileBinding
import com.android.herbmate.ui.history.HistoryActivity
import com.android.herbmate.ui.login.LoginActivity
import com.google.android.material.switchmaterial.SwitchMaterial


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val binding get() = _binding!!
    private var userEmail: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        binding.switchDarkMode.isChecked = isDarkMode
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveThemePreference(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveThemePreference(false)
            }
        }

        var isEdit = false

        binding.btnEdit.setOnClickListener {
            if (isEdit) {
                binding.edName.isEnabled = false
                binding.edPassword.isEnabled = false
                binding.edPasswordbaru.isEnabled = false
                binding.btnSave.visibility = View.GONE
                binding.btnCancel.visibility = View.GONE
                isEdit = false
            } else {
                binding.edName.isEnabled = true
                binding.edPassword.isEnabled = true
                binding.edPasswordbaru.isEnabled = true
                binding.btnSave.visibility = View.VISIBLE
                binding.btnCancel.visibility = View.VISIBLE
                isEdit = true
            }
        }

        viewModel.updateResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
                is ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.edName.isEnabled = false
                    binding.edPassword.isEnabled = false
                    binding.edPasswordbaru.isEnabled = false
                    binding.edPassword.text = null
                    binding.edPasswordbaru.text = null
                    binding.btnSave.visibility = View.GONE
                    binding.btnCancel.visibility = View.GONE
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val name = binding.edName.text.toString()
            val verify = binding.edPassword.text.toString()
            val password = binding.edPasswordbaru.text.toString()

            viewModel.update(email, name,verify, password)

        }

        binding.btnCancel.setOnClickListener {
            binding.edName.isEnabled = false
            binding.edPassword.isEnabled = false
            binding.btnSave.visibility = View.GONE
            binding.btnCancel.visibility = View.GONE
            isEdit = false
        }

        binding.btnLogOut.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        binding.btnHistory.setOnClickListener{
            startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }

        viewModel.userSession.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.edEmail.text = Editable.Factory.getInstance().newEditable(it.name)
                binding.edName.text = Editable.Factory.getInstance().newEditable(it.email)

            } ?: run {
                binding.edEmail.text = Editable.Factory.getInstance().newEditable("")
                binding.edName.text = Editable.Factory.getInstance().newEditable("")
//                binding.edPassword.text = Editable.Factory.getInstance().newEditable("")
            }
        }

        val switchTheme : SwitchMaterial = binding.switchDarkMode

        viewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
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