package com.kahin.petinthehouse.sign

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.kahin.petinthehouse.base.BaseFragment
import com.kahin.petinthehouse.databinding.FragmentSignupBinding

class SignUpFragment : BaseFragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private var _binding: FragmentSignupBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, SignUpViewModelFactory())[SignUpViewModel::class.java]

        val emailEt = binding.email
        val passwordEt = binding.password
        val usernameEt = binding.username
        val signUpBtn = binding.btnSignUp

        viewModel.apply {
            signUpFormState.observeEventDataBy { signUpFormState ->
                signUpBtn.isEnabled = signUpFormState.isDataValid
                signUpFormState.let {
                    it.emailError?.let { msg ->
                        emailEt.error = getString(msg)
                    }
                    it.passwordError?.let { msg ->
                        passwordEt.error = getString(msg)
                    }
                }
            }

            signUpResult.observeEventDataBy { signUpResult ->
                signUpResult.error?.let {
                    showSignUpFailed(it)
                }
                signUpResult.success?.let {
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment(signUpResult.success.displayName))
                }
            }
        }

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.signUpDataChanged(
                    email = emailEt.text.toString(),
                    password = passwordEt.text.toString(),
                    username = usernameEt.text.toString()
                )
            }
        }
        emailEt.addTextChangedListener(afterTextChangedListener)
        passwordEt.addTextChangedListener(afterTextChangedListener)
        passwordEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.signUp(
                    email = emailEt.text.toString(),
                    password = passwordEt.text.toString(),
                    username = usernameEt.text.toString()
                )
            }
            false
        }
        usernameEt.addTextChangedListener(afterTextChangedListener)

        signUpBtn.setOnClickListener {
            viewModel.signUp(
                email = emailEt.text.toString(),
                password = passwordEt.text.toString(),
                username = usernameEt.text.toString()
            )
        }
    }

    override fun onBack() {
        findNavController().navigateUp()
    }

    private fun showSignUpFailed(errorString: String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }
}