package com.kahin.petinthehouse.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.kahin.petinthehouse.R
import com.kahin.petinthehouse.databinding.FragmentHomeBinding
import com.kahin.petinthehouse.base.BaseFragment

class HomeFragment : BaseFragment() {

    private val args: HomeFragmentArgs by navArgs()

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

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeRvAdapter(
            arrayOf("Amy", "Holly", "Joy", "Bear", "Muffy", "Max", "Kiwi", "Coco", "Leo", "Simon"),
            arrayOf(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j)
        )

        val  rv: RecyclerView = binding.recyclerView
        rv.adapter = adapter

        binding.tvBottomMe.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMeFragment(args.name))
        }
    }

    override fun onBack() {
        requireActivity().finish()
    }
}