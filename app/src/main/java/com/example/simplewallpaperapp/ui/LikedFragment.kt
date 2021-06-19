package com.example.simplewallpaperapp.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pagination.models.Hit
import com.example.simplewallpaperapp.R
import com.example.simplewallpaperapp.adapters.RvAdapter
import com.example.simplewallpaperapp.databinding.FragmentSlideshowBinding
import com.example.simplewallpaperapp.room.AppDataBase
class LikedFragment : Fragment() {
    private lateinit var binding: FragmentSlideshowBinding
    private lateinit var appDataBase: AppDataBase
    private lateinit var rvAdapter: RvAdapter
    private lateinit var list:ArrayList<Hit>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        appDataBase= AppDataBase.getInstance(requireContext())
        list= ArrayList(appDataBase.imageDao().getAllImages())
        rvAdapter= RvAdapter(list,object:RvAdapter.OnItemClickListener{
            override fun onItemMusic(hit: Hit) {
                val bundle= bundleOf("data" to hit,"num" to 1)
                findNavController().navigate(R.id.imageFragment,bundle)
            }
        })
        binding.recView.adapter=rvAdapter
        return binding.root
    }
}