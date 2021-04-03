package com.example.github.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.github.R
import com.example.github.databinding.ViewpagerMainBinding

class ViewPagerAdapter(
    private val searchBinding: ViewpagerMainBinding,
    private val favoriteBinding: ViewpagerMainBinding
) : PagerAdapter() {

    override fun getCount(): Int = 2

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }

    override fun destroyItem(container: View, position: Int, `object`: Any) {
        val view: View? = `object` as View
        (container as androidx.viewpager.widget.ViewPager).removeView(view)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =  when(position) {
            0 -> searchBinding.root
            else -> favoriteBinding.root
        }

        container.addView(view)

        return view
    }
}