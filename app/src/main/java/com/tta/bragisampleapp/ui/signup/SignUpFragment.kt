package com.tta.bragisampleapp.ui.signup

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
import com.tta.bragisampleapp.databinding.FragmentSignUpBinding

class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val connectionViewModel: ConnectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this)[SignUpViewModel::class.java]

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loginViewModel.text.observe(viewLifecycleOwner) {
            binding.textViewSignUpTitle.text = it
        }
        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_forgotPasswordFragment)
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