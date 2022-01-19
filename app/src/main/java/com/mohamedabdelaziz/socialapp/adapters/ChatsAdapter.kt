package com.mohamedabdelaziz.socialapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohamedabdelaziz.socialapp.R
import com.mohamedabdelaziz.socialapp.model.ChatModel
import com.mohamedabdelaziz.socialapp.ui.ChatBody
import com.mohamedabdelaziz.socialapp.util.Util


class ChatsAdapter(private val mList:ArrayList<ChatModel.Data>, private val context: Context) :
    RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)

        return ChatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {


        holder.userName.text = mList.get(position).name
        Glide.with(context).load(mList.get(position).avatar).placeholder(R.drawable.ic_launcher_background).into(holder.avatar)
        if (mList.get(position).conversation != null) {
            holder.message.text = mList.get(position).conversation?.lastMessage?.contant
            holder.time.visibility = View.VISIBLE
            holder.time.text = Util.UTC_to_local(mList.get(position).conversation?.lastMessage?.createdAt)

        } else {
            holder.message.text = "hay there , I'm using SocialApp"
            holder.time.visibility = View.INVISIBLE
        }


        holder.itemView.setOnClickListener(View.OnClickListener {

            val intent:Intent = Intent(context, ChatBody::class.java)
            if(mList.get(position).conversation?.equals(null) == false)
            intent.putExtra("user_id", mList.get(position).conversation?.id)
            intent.putExtra("user", mList.get(position).id)
            intent.putExtra("user_name", mList.get(position).name)
            intent.putExtra("user_avatar", mList.get(position).avatar)
            context.startActivity(intent)
        }
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.userAvatar)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val message: TextView = itemView.findViewById(R.id.lastMessage)
        val time: TextView = itemView.findViewById(R.id.lastMessageTime)
    }
}