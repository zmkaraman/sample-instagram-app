package com.example.sampleapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sampleapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false)

        val binding = FragmentDetailBinding.inflate(inflater)

        val media = arguments?.let {
            DetailFragmentArgs.fromBundle(it).media

        }
        binding.lifecycleOwner = this
        binding.viewModel = detailViewModel

        binding.textViewBack.setOnClickListener {
            activity?.onBackPressed()
        }

        media?.let {
            detailViewModel.getUserMediaData(it.id)
        }

        detailViewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                //TODO MERVE aksiyon ?
            }
        })


        return binding.root
    }

}