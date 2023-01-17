package com.xyz.oclock.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding
import com.xyz.oclock.R
import com.xyz.oclock.core.model.Chat
import com.xyz.oclock.databinding.ItemChatBinding

class ChatAdapter: BindingListAdapter<Chat, ChatAdapter.ChatViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = parent.binding<ItemChatBinding>(R.layout.item_chat)
        return ChatViewHolder(binding)
    }

    inner class ChatViewHolder constructor(
        private val binding: ItemChatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Chat>() {

            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean  =
                oldItem.timeStamp == newItem.timeStamp

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean =
                oldItem == newItem
        }
    }


}