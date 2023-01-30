package com.xyz.oclock.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding
import com.xyz.oclock.R
import com.xyz.oclock.core.model.Chat
import com.xyz.oclock.core.model.ChatType
import com.xyz.oclock.databinding.*

class ChatAdapter: BindingListAdapter<Chat, ChatAdapter.ChatViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chat = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        when(viewType) {
            ChatType.CHAT_ME.viewType -> {
                val binding = parent.binding<ItemChatMeBinding>(R.layout.item_chat_me)
                return ChatMeViewHolder(binding)
            }
            ChatType.CHAT_YOU.viewType -> {
                val binding = parent.binding<ItemChatYouBinding>(R.layout.item_chat_you)
                return ChatYouViewHolder(binding)
            }
            ChatType.SELECT.viewType -> {
                val binding = parent.binding<ItemChatSelectBinding>(R.layout.item_chat_select)
                return ChatSelectViewHolder(binding)
            }
            ChatType.ALERT.viewType -> {
                val binding = parent.binding<ItemChatAlertBinding>(R.layout.item_chat_alert)
                return ChatAlertViewHolder(binding)
            }
            else -> {
                val binding = parent.binding<ItemChatAlertBinding>(R.layout.item_chat_alert)
                return ChatAlertViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.viewType
    }

    abstract class ChatViewHolder(view: View): RecyclerView.ViewHolder(view) {
        abstract fun bind(chat: Chat)
    }

    inner class ChatMeViewHolder constructor(
        private val binding: ItemChatMeBinding
    ) : ChatViewHolder(binding.root) {

        override fun bind(chat: Chat) {
            binding.chat = chat
        }
    }

    inner class ChatYouViewHolder constructor(
        private val binding: ItemChatYouBinding
    ) : ChatViewHolder(binding.root) {

        override fun bind(chat: Chat) {
            binding.chat = chat
        }
    }

    inner class ChatAlertViewHolder constructor(
        private val binding: ItemChatAlertBinding
    ) : ChatViewHolder(binding.root) {

        override fun bind(chat: Chat) {
            binding.chat = chat
        }
    }

    inner class ChatSelectViewHolder constructor(
        private val binding: ItemChatSelectBinding
    ) : ChatViewHolder(binding.root) {

        override fun bind(chat: Chat) {
            binding.chat = chat
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean  =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean =
                oldItem == newItem
        }
    }


}