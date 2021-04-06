package com.example.github.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.github.R
import com.example.github.model.FavoriteInfo
import com.example.github.model.UserViewModel

class FavoriteAdapter(
    private val context: Context,
    private val viewModel: UserViewModel,
    private val data: List<FavoriteInfo>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById(R.id.image) as ImageView
        val username = itemView.findViewById(R.id.username) as TextView
        val favorite = itemView.findViewById(R.id.favorite) as ImageView

        init {
            itemView.setOnClickListener {
                viewModel.change.set(true) // true: 화면 업데이트

                val item = FavoriteInfo(data[adapterPosition].login, data[adapterPosition].avatarUrl)
                viewModel.db?.favorite()?.delete(item) // DB delete

                viewModel.requestFavoriteInfo()
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)

        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        data[position].let {
            with(holder) {
                // 프로필 이미지
                Glide.with(context)
                    .load(it.avatarUrl)
                    .apply(RequestOptions().circleCrop())
                    .into(image)
                // 사용자 이름
                username.text = it.login
                favorite.isSelected = viewModel.db?.favorite()?.existsFavoriteInfo(it.login) == true
            }
        }
    }
}