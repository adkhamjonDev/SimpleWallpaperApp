package com.example.simplewallpaperapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pagination.models.Hit
import com.example.simplewallpaperapp.R
import com.example.simplewallpaperapp.adapters.ImagePagingAdapter
import com.example.simplewallpaperapp.databinding.FragmentRandomBinding
import com.example.simplewallpaperapp.network.ApiClient
import com.example.simplewallpaperapp.viewModel.ImageViewModel
import com.example.simplewallpaperapp.viewModel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RandomFragment : Fragment() {
    private lateinit var binding:FragmentRandomBinding
    private lateinit var imagePagingAdapter: ImagePagingAdapter
    private lateinit var imageViewModel: ImageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentRandomBinding.inflate(inflater, container, false)
        imageViewModel= ViewModelProviders.of(
            this,
            ViewModelFactory(ApiClient.apiService,"random")
        )[ImageViewModel::class.java]
        imagePagingAdapter = ImagePagingAdapter(object : ImagePagingAdapter.MyOnClickListener{
            override fun onItemClick(data: Hit) {
                val bundle= bundleOf("data" to data,"num" to 0)
                findNavController().navigate(R.id.imageFragment,bundle)
            }

        })
        binding.recView.hasFixedSize()
        binding.recView.adapter = imagePagingAdapter
        lifecycleScope.launch {
            imageViewModel.image.collectLatest {
                imagePagingAdapter.submitData(it)
            }
        }
        return binding.root
    }
}