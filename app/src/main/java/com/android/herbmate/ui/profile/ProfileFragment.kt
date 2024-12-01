package com.android.herbmate.ui.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

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
                binding.edPassword.text = Editable.Factory.getInstance().newEditable("")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}