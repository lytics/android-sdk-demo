package com.lytics.android.demo.ui.account

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lytics.android.Lytics
import com.lytics.android.demo.R
import com.lytics.android.demo.databinding.FragmentRegisterBinding
import com.lytics.android.events.LyticsConsentEvent
import com.lytics.android.events.LyticsIdentityEvent

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        Lytics.currentUser?.let {
            val name: String = it.attributes?.get("name") as? String ?: ""
            binding.nameTextField.editText?.setText(name)
            val email: String = it.identifiers?.get("email") as? String ?: ""
            binding.emailTextField.editText?.setText(email)

            val tac: Boolean = it.consent?.get("tac") as? Boolean ?: false
            binding.privacyPolicyCheckbox.isChecked = tac
            val gaid: Boolean = it.consent?.get("gaid") as? Boolean ?: false
            binding.gaidCheckbox.isChecked = gaid
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailTextField.editText?.text.toString()
            val name = binding.nameTextField.editText?.text.toString()
            Lytics.identify(
                LyticsIdentityEvent(
                    name = "login",
                    identifiers = mapOf("email" to email),
                    attributes = mapOf("name" to name)
                )
            )

            val termsAndConditions = binding.privacyPolicyCheckbox.isChecked
            Lytics.consent(LyticsConsentEvent(name = "android consent", consent = mapOf("tac" to termsAndConditions)))

            val gaid = binding.gaidCheckbox.isChecked
            Lytics.consent(LyticsConsentEvent(name = "android consent", consent = mapOf("gaid" to gaid)))

            Toast.makeText(requireContext(), R.string.signed_up, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.navigation_events)
        }

        binding.gaidCheckbox.setOnClickListener {
            val gaid = binding.gaidCheckbox.isChecked
            if (gaid) {
                val builder = AlertDialog.Builder(requireContext())
                builder.apply {
                    setMessage(R.string.gaid_prompt)
                    setPositiveButton(R.string.allow_tracking) { _, _ ->
                        Lytics.enableGAID()
                    }
                    setNegativeButton(R.string.do_no_track) { _, _ ->
                        binding.gaidCheckbox.isChecked = false
                    }
                }
                builder.create().show()
            } else {
                Lytics.disableGAID()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}