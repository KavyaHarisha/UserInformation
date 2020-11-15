package com.userinformation.user

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.userinformation.R
import com.userinformation.data.entities.User
import com.userinformation.databinding.UserItemRowLayoutBinding

class UserViewHolder(private val binding: UserItemRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.itemTitle.text = user.title
        binding.itemDescription.text = user.description
        binding.itemImageRef.load(user.imageHref) {
            error(R.mipmap.ic_launcher_round)
        }
    }
}
