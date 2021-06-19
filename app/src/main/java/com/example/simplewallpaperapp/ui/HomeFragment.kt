package com.example.simplewallpaperapp.ui
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.simplewallpaperapp.R
import com.example.simplewallpaperapp.adapters.MyPagerAdapter
import com.example.simplewallpaperapp.databinding.CustomTabBinding
import com.example.simplewallpaperapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bind: CustomTabBinding
    private lateinit var categoryList:ArrayList<String>
    private lateinit var myPagerAdapter: MyPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        loaDCategory()

        myPagerAdapter=MyPagerAdapter(categoryList, childFragmentManager)
        binding.viewPager.adapter=myPagerAdapter
        binding.tablaLayout.setupWithViewPager(binding.viewPager)
        setTabs()
        binding.tablaLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                val tittle = tabView?.findViewById<TextView>(R.id.tittle)
                val shape = tabView?.findViewById<CardView>(R.id.shape)
                tittle?.setTextColor(Color.WHITE)
                shape?.setCardBackgroundColor(Color.WHITE)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                val tittle = tabView?.findViewById<TextView>(R.id.tittle)
                val shape = tabView?.findViewById<CardView>(R.id.shape)
                tittle?.setTextColor(Color.parseColor("#808A93"))
                shape?.setCardBackgroundColor(Color.parseColor("#0C0C0C"))
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        return binding.root
    }
    private fun loaDCategory() {
        categoryList= ArrayList()
        categoryList.add("ALL")
        categoryList.add("NEW")
        categoryList.add("ANIMALS")
        categoryList.add("TECHNOLOGY")
        categoryList.add("NATURE")
        categoryList.add("ARCHITECTURE")
        categoryList.add("CARS")
    }
    private fun setTabs() {
        val count: Int = binding.tablaLayout.tabCount
        for (i in 0 until count) {
            bind = CustomTabBinding.inflate(layoutInflater, null, false)
            bind.tittle.text = categoryList[i]
            if (i == 0) {
                bind.tittle.setTextColor(Color.WHITE)
                bind.shape.setCardBackgroundColor(Color.WHITE)
            } else {
                bind.tittle.setTextColor(Color.parseColor("#808A93"))
                bind.shape.setCardBackgroundColor(Color.parseColor("#0C0C0C"))
            }
            binding.tablaLayout.getTabAt(i)?.customView = bind.root
        }
    }
}