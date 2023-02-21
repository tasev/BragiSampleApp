package com.tta.bragisampleapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tta.bragisampleapp.R
import com.tta.bragisampleapp.databinding.FragmentLoginBinding
import com.tta.bragisampleapp.shared.BaseFragment
import com.tta.bragisampleapp.shared.ConnectionViewModel
import java.util.concurrent.TimeUnit


class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding

    private val connectionViewModel: ConnectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this)[LoginViewModel::class.java]

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loginViewModel.text.observe(viewLifecycleOwner) {
            binding.textViewLoginTitle.text = it
        }
        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
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
        /**
         * button with a Send Commands title to the Login Screen
         * When a user presses this button, creates an array of commands with ID from 1 to 10 and each command takes ID-seconds to execute.
         */
        binding.buttonSendCommands.setOnClickListener {
            connectionViewModel.runDummyCommandWithIdAndTimer(1,TimeUnit.SECONDS)
        }

        return root
    }

    override fun onPause() {
        connectionViewModel.disposable?.dispose()
        super.onPause()
    }

}