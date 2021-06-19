package com.example.simplewallpaperapp.adapters
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.simplewallpaperapp.ui.PagerFragment
class MyPagerAdapter(private val list:ArrayList<String>,fm:FragmentManager)
    :FragmentPagerAdapter(fm){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                PagerFragment.newInstance(list[0])
            }
            1->{
                PagerFragment.newInstance(list[1])
            }
            2->{
                PagerFragment.newInstance(list[2])
            }
            3->{
                PagerFragment.newInstance(list[3])
            }
            4->{
                PagerFragment.newInstance(list[4])
            }
            5->{
                PagerFragment.newInstance(list[5])
            }
            6->{
                PagerFragment.newInstance(list[6])
            }
            else -> PagerFragment()
        }
    }
}