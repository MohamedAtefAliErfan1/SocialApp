package com.mohamedabdelaziz.socialapp.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import com.mohamedabdelaziz.socialapp.R
import com.mohamedabdelaziz.socialapp.model.MessagesModel
import com.mohamedabdelaziz.socialapp.util.PrefKeys
import com.mohamedabdelaziz.socialapp.util.PrefUtils
import android.widget.ImageView
import android.widget.VideoView
import android.content.DialogInterface
import android.content.Intent

import android.webkit.WebView

import android.webkit.WebViewClient
import com.mohamedabdelaziz.socialapp.ui.CustomViewer


class ChatBodyAdapter(
    private val mList: ArrayList<MessagesModel.Data?>?,
    private val context: Context,
    private val id: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == 0) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_message, parent, false)
            return ChatUserViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.person_message, parent, false)
            return ChatPersonViewHolder(view)
        }


    }

    override fun onBindViewHolder(holderBody: RecyclerView.ViewHolder, position: Int) {


        if (holderBody.itemViewType == 0) {
            (holderBody as ChatUserViewHolder).textView.setText(mList?.get(position)?.contant)
            if (mList?.get(position)?.url!=null){

                val extension: String = mList?.get(position)?.url!!.substring(mList?.get(position)?.url!!.lastIndexOf("."))

                if (extension.equals(".png")||extension.equals(".jpg")||extension.equals(".jpeg"))
                { (holderBody as ChatUserViewHolder).image.visibility=View.VISIBLE
                    val imageView:ImageView=(holderBody as ChatUserViewHolder).image as ImageView
                        Glide.with(context).load(mList?.get(position)?.url).into(imageView)
                }
                else if (extension.equals(".mp4")||extension.equals(".3gp")||extension.equals(".flv"))
                {(holderBody as ChatUserViewHolder).video.visibility=View.VISIBLE
                    (holderBody as ChatUserViewHolder).video.setVideoURI(Uri.parse(mList?.get(position)?.url))
                    (holderBody as ChatUserViewHolder).video.requestFocus();
                    (holderBody as ChatUserViewHolder).video.start();
                }
                else
                {
                    (holderBody as ChatUserViewHolder).file.visibility=View.VISIBLE
                }
            }
            (holderBody as ChatUserViewHolder).image.setOnClickListener(View.OnClickListener {
                val intent: Intent = Intent(context,CustomViewer::class.java)
                intent.putExtra("url",mList?.get(position)?.url)
                context.startActivity(intent)
            })
            (holderBody as ChatUserViewHolder).video.setOnClickListener(View.OnClickListener {

                val intent: Intent = Intent(context,CustomViewer::class.java)
                intent.putExtra("url",mList?.get(position)?.url)
                context.startActivity(intent)
            })
            (holderBody as ChatUserViewHolder).file.setOnClickListener(View.OnClickListener {
                val alert: AlertDialog.Builder = AlertDialog.Builder(context)


                val wv = WebView(context)
                wv.loadUrl(mList?.get(position)?.url!!)
                wv.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        view.loadUrl(url)
                        return true
                    }
                }

                alert.setView(wv)
                alert.setNegativeButton("Close",
                    DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
                alert.show() })

        } else {
            (holderBody as ChatPersonViewHolder).textView.setText(mList?.get(position)?.contant)
            Glide.with(context).load(PrefUtils.getFromPrefs(context, PrefKeys.USER_AVATAR, ""))
                .placeholder(R.drawable.ic_launcher_background)
                .into((holderBody as ChatPersonViewHolder).imageView)

        }


    }

    override fun getItemCount(): Int {
        if (mList != null) {
            return mList.size
        } else
            return 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMessage(message: MessagesModel.Data) {
        mList?.add(message)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (mList?.get(position)?.user?.id!!.equals(id.toInt()))
            return 0
        else
            return 1
    }

    class ChatUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.textView12)
        val image:ImageView  = itemView.findViewById(R.id.imageContainer)
        val video:VideoView  = itemView.findViewById(R.id.videoContainer)
        val file:ImageView  = itemView.findViewById(R.id.fileContainer)
    }

    class ChatPersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.textView15)
        val imageView: CircularImageView = itemView.findViewById(R.id.imageView7)
        val image:ImageView  = itemView.findViewById(R.id.imageContainerPerson)
        val video:VideoView  = itemView.findViewById(R.id.videoContainerPerson)
        val file:ImageView  = itemView.findViewById(R.id.fileContainerPerson)
    }
}