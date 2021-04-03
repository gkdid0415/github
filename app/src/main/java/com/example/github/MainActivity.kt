package com.example.github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.github.adapters.UserAdapter
import com.example.github.adapters.ViewPagerAdapter
import com.example.github.databinding.ActivityMainBinding
import com.example.github.databinding.ViewpagerMainBinding
import com.example.github.model.UserViewModel
import com.example.github.model.ViewModelFactory
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAB_SEARCH = 0 // 사용자 검색
        const val TAB_FAVORITE = 1 // 즐겨찾기
    }

    private lateinit var viewModel: UserViewModel
    private lateinit var searchBinding: ViewpagerMainBinding
    private lateinit var favoriteBinding: ViewpagerMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(application)).get(UserViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel

        searchBinding = ViewpagerMainBinding.inflate(LayoutInflater.from(this))
        searchBinding.viewModel = viewModel
        searchBinding.github.adapter = UserAdapter(this, TAB_SEARCH)

        favoriteBinding = ViewpagerMainBinding.inflate(LayoutInflater.from(this))
        favoriteBinding.viewModel = viewModel
        favoriteBinding.github.adapter = UserAdapter(this, TAB_FAVORITE)

        viewModel.users.observe(this, Observer {

        })

        viewModel.favorites.observe(this, Observer {

        })

        binding.viewpager.adapter = ViewPagerAdapter(searchBinding, favoriteBinding)
        binding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tab))
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })
    }
}