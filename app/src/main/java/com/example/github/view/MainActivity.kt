package com.example.github.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.github.R
import com.example.github.adapters.FavoriteAdapter
import com.example.github.adapters.UserAdapter
import com.example.github.adapters.ViewPagerAdapter
import com.example.github.databinding.ActivityMainBinding
import com.example.github.databinding.ViewpagerMainBinding
import com.example.github.model.UserViewModel
import com.example.github.model.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var searchBinding: ViewpagerMainBinding
    private lateinit var favoriteBinding: ViewpagerMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(application)).get(UserViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Github 사용자 검색 화면 바인딩
        searchBinding = ViewpagerMainBinding.inflate(LayoutInflater.from(this))
        searchBinding.viewModel = viewModel

        // 로컬 즐겨찾기 검색 화면 바인딩
        favoriteBinding = ViewpagerMainBinding.inflate(LayoutInflater.from(this))
        favoriteBinding.viewModel = viewModel

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        searchBinding.github.addItemDecoration(divider)
        favoriteBinding.github.addItemDecoration(divider)

        // Github 사용자 데이터 Observer
        viewModel.users.observe(this, Observer {
            searchBinding.github.adapter = UserAdapter(this, viewModel, it)
        })

        // 로컬 즐겨찾기 데이터 Observer
        viewModel.favorites.observe(this, Observer {
            favoriteBinding.github.adapter = FavoriteAdapter(this, viewModel, it)
        })

        binding.viewpager.adapter = ViewPagerAdapter(searchBinding, favoriteBinding)
        binding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tab))
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewpager.currentItem = tab?.position!!

                if (viewModel.change.get()) {
                    if (binding.viewpager.currentItem == 0) {
                        searchBinding.github.adapter?.notifyDataSetChanged()
                    } else {
                        viewModel.requestFavoriteInfo()
                    }
                    viewModel.change.set(false)
                }
            }
        })

        binding.search.setOnClickListener {
            hideKeyboard(binding.input)
            showLoadingDialog()

            if (binding.viewpager.currentItem == 0) {
                viewModel.username.set(binding.input.text.toString())
                viewModel.requestUserInfo()
            } else {
                viewModel.favoritename.set(binding.input.text.toString())
                viewModel.requestFavoriteInfo()
            }
        }
    }

    /**
     * 키보드 컨트롤
     */
    private fun hideKeyboard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    /**
     * 로딩 다이얼로그
     */
    private fun showLoadingDialog() {
        val dialog = LoadingDialog(this@MainActivity)
        CoroutineScope(Main).launch {
            dialog.show()
            delay(2000)
            dialog.dismiss()
        }
    }
}