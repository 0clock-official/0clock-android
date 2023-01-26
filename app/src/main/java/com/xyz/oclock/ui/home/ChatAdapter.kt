package com.xyz.oclock.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.BindingAdapter.visibility
import com.xyz.oclock.core.model.Chat
import com.xyz.oclock.core.model.ChatType
import com.xyz.oclock.databinding.ItemChatBinding

class ChatAdapter: BindingListAdapter<Chat, ChatAdapter.ChatViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chat = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = parent.binding<ItemChatBinding>(R.layout.item_chat)
        return ChatViewHolder(binding)
    }

    inner class ChatViewHolder constructor(
        private val binding: ItemChatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: Chat) {
            binding.chat = chat
            binding.showProfileImg = true
            setChatVisibility(chat)
        }

        private fun setChatVisibility(chat: Chat) {
            when (chat.type) {
                ChatType.CHAT_ME -> {
                    binding.chatU1Layout.visibility = View.VISIBLE
                    binding.chatU2Layout.visibility = View.GONE
                    binding.alertLayout.visibility = View.GONE
                }
                ChatType.CHAT_YOU -> {
                    binding.chatU1Layout.visibility = View.GONE
                    binding.chatU2Layout.visibility = View.VISIBLE
                    binding.alertLayout.visibility = View.GONE
                }
                ChatType.ALERT -> {
                    binding.chatU1Layout.visibility = View.GONE
                    binding.chatU2Layout.visibility = View.GONE
                    binding.alertLayout.visibility = View.VISIBLE
                    binding.alertButtonLayout.visibility = View.GONE
                }
                ChatType.SELECT -> {
                    binding.chatU1Layout.visibility = View.GONE
                    binding.chatU2Layout.visibility = View.GONE
                    binding.alertLayout.visibility = View.VISIBLE
                    binding.alertButtonLayout.visibility = View.VISIBLE
                }
                else -> {}
            }
        }

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