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
import com.example.simplewallpaperapp.databinding.FragmentPagerBinding
import com.example.simplewallpaperapp.network.ApiClient
import com.example.simplewallpaperapp.viewModel.ImageViewModel
import com.example.simplewallpaperapp.viewModel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
private const val ARG_PARAM1 = "param1"
class PagerFragment : Fragment() {
    private var param1: String? = null
    private lateinit var binding:FragmentPagerBinding
    private lateinit var imagePagingAdapter: ImagePagingAdapter
    private lateinit var imageViewModel: ImageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentPagerBinding.inflate(inflater, container, false)
        imageViewModel= ViewModelProviders.of(
            this,
            ViewModelFactory(ApiClient.apiService,param1!!)
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
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}