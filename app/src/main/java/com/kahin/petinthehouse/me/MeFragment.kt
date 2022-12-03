package com.kahin.petinthehouse.me

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kahin.petinthehouse.R
import com.kahin.petinthehouse.base.BaseFragment
import com.kahin.petinthehouse.databinding.FragmentMeBinding

class MeFragment : BaseFragment() {

    companion object {
        fun newInstance() = MeFragment()
    }

    private val args: MeFragmentArgs by navArgs()

    private lateinit var viewModel: MeViewModel
    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onBack() {
        findNavController().navigateUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, MeViewModelFactory())[MeViewModel::class.java]
        // TODO: Use the ViewModel

        val usernameTextView = binding.tvName
        val emailTextView = binding.tvEmail
        val logoutButton = binding.btnLogOut

        viewModel.user.observeEventDataBy {
            Log.d("aaa", "33333333333")
            usernameTextView.text = it.userName
            emailTextView.text = it.email
        }

        logoutButton.setOnClickListener {
            findNavController().popBackStack(R.id.loginFragment, false)
        }
    }

    override fun onResume() {
        super.onResume()

        Log.d("aaa", "------ ${args.name}")
        viewModel.findUser(args.name)
    }
}