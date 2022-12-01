package com.kahin.babyinthehouse.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kahin.babyinthehouse.R
import com.kahin.babyinthehouse.base.BaseFragment
import com.kahin.babyinthehouse.databinding.FragmentHomeBinding
import com.kahin.babyinthehouse.databinding.FragmentSignupBinding

class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeRvAdapter(
            arrayOf("aa", "dd", "das", "dsds", "dsdsds", "dsds", "dsds", "fsfs", "fsdfsf", "dsdfs"),
            arrayOf(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j)
        )

        val  rv: RecyclerView = binding.recyclerView
        rv.adapter = adapter

        binding.tvBottomMe.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_meFragment)
        }
    }

    override fun onBack() {
        findNavController().popBackStack(R.id.loginFragment, true)
    }
}