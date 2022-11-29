package com.kahin.babyinthehouse.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kahin.babyinthehouse.R
import com.kahin.babyinthehouse.databinding.FragmentHomeBinding
import com.kahin.babyinthehouse.databinding.FragmentSignupBinding

class HomeFragment : Fragment() {

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
            arrayOf("aa", "dd", "das", "dsds", "dsdsds", "dsds", "dsds", "fsfs", "fsdfsf", "dsds"),
            arrayOf(R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa, R.drawable.aa)
        )

        val  rv: RecyclerView = binding.recyclerView
        rv.adapter = adapter
    }

}