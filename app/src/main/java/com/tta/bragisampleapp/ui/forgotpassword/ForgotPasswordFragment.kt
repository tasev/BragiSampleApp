package com.tta.bragisampleapp.ui.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tta.bragisampleapp.shared.BaseFragment
import com.tta.bragisampleapp.shared.ConnectionViewModel
import com.tta.bragisampleapp.R
import com.tta.bragisampleapp.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentForgotPasswordBinding

    private val connectionViewModel: ConnectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this)[ForgotPasswordViewModel::class.java]

        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loginViewModel.text.observe(viewLifecycleOwner) {
            binding.textViewForgotPasswordTitle.text = it
        }
        binding.buttonForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }

        connectionViewModel.connectionStatus.observe(viewLifecycleOwner) {
            showConnectionSnackBar(binding.coordinatorLayoutSnackBar, it.titleResource, it.statusColorResource)
        }

        /**
         * button for checking the connection
         * When a user presses the “Check Connection” button, show a 'message sent' a pop up
        only if the current state is a connection established.
         */
        binding.buttonCheckConnection.setOnClickListener {
            val titleResource = connectionViewModel.connectionStatus.value?.titleResource
            if(titleResource!=null && titleResource == R.string.connection_established){
                showDialogWithOneOption(getString(R.string.message_sent), getString(R.string.ok),{},{})
            }
        }
        return root
    }
}