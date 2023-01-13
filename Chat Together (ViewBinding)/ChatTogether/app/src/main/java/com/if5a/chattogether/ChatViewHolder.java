package com.if5a.chattogether;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if5a.chattogether.databinding.ItemChatBinding;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    public ItemChatBinding itemChatBinding;

    public ChatViewHolder(@NonNull ItemChatBinding itemChatBinding) {
        super(itemChatBinding.getRoot());

        this.itemChatBinding = itemChatBinding;
    }
}
