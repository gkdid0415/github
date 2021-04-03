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
import com.example.github.MainActivity
import com.example.github.R
import com.example.github.model.UserInfo

class UserAdapter internal constructor(
    private val context: Context,
    private val tab: Int
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users = emptyList<UserInfo>()
    private var favorites = emptyList<UserInfo>()

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById(R.id.image) as ImageView
        val username = itemView.findViewById(R.id.username) as TextView
    }

    override fun getItemCount() = if (tab == MainActivity.TAB_SEARCH) users.size else favorites.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)

        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = users[position]

        Glide.with(context)
            .load(current.avatarUrl)
            .apply(RequestOptions().circleCrop())
            .into(holder.image)
        holder.username.text = current.login
    }
}