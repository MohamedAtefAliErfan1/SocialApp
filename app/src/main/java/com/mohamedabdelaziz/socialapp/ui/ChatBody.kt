package com.mohamedabdelaziz.socialapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mohamedabdelaziz.socialapp.R
import com.mohamedabdelaziz.socialapp.adapters.ChatBodyAdapter
import com.mohamedabdelaziz.socialapp.databinding.ActivityChatBodyBinding
import com.mohamedabdelaziz.socialapp.model.MessagesModel
import com.mohamedabdelaziz.socialapp.viewmodel.ChatBodyViewModel
import com.mohamedabdelaziz.socialapp.viewmodel.ChatsViewModel
import com.mohamedabdelaziz.socialapp.util.PrefKeys
import com.mohamedabdelaziz.socialapp.util.PrefUtils
import com.mohamedabdelaziz.socialapp.util.Util
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import com.google.android.material.snackbar.Snackbar

import android.os.Environment
import android.provider.Settings
import androidx.core.os.EnvironmentCompat
import com.mohamedabdelaziz.socialapp.BuildConfig
import java.lang.Exception


@AndroidEntryPoint
class ChatBody : AppCompatActivity() {
    private lateinit var binding: ActivityChatBodyBinding
    lateinit var chatBodyAdapter: ChatBodyAdapter
    var file: File? = null
    var requestFile: RequestBody? = null
    val chatBodyViewModel: ChatBodyViewModel by viewModels()
    val chatViewModel: ChatsViewModel by viewModels()
    val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

            file = File(this.let { uri?.let { it1 -> Util.getRealPathFromURI(it, it1) } })
            requestFile = RequestBody.create(
                uri?.let { this.contentResolver?.getType(it)?.let { it.toMediaTypeOrNull() } },
                file!!
            )

        }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = ArrayList<String>()
        binding.option.setOnClickListener(View.OnClickListener {
            PrefUtils.clearPrefs(this)
            startActivity(Intent(this, LoginActivity::class.java))

        })
        binding.back.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        })
        Glide.with(this).load(intent.getStringExtra("user_avatar"))
            .placeholder(R.drawable.ic_launcher_background).into(binding.chatBodyAvatar)
        binding.chatBodyName.setText(intent.getStringExtra("user_name"))
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        chatBodyAdapter = ChatBodyAdapter(
            null,
            this,
            PrefUtils.getFromPrefs(this, PrefKeys.USER_ID, "") as String
        )
        binding.rvChat.adapter = chatBodyAdapter
        if (intent.extras?.get("user_id")?.equals(null) == false) {
            chatBodyViewModel.getMessages(intent.extras?.get("user_id").toString())
        }
        chatBodyViewModel.getMessageModel().observe(this, Observer { messageModel: MessagesModel ->

            binding.rvChat.layoutManager = LinearLayoutManager(this)
            chatBodyAdapter =
                ChatBodyAdapter(
                    messageModel.data,
                    this,
                    PrefUtils.getFromPrefs(this, PrefKeys.USER_ID, "") as String
                )
            binding.rvChat.adapter = chatBodyAdapter
            binding.rvChat.scrollToPosition((binding.rvChat.adapter as ChatBodyAdapter).itemCount - 1)
        })
        binding.attach.setOnClickListener(View.OnClickListener { showBottomSheet() })
        binding.send.setOnClickListener(View.OnClickListener {
            binding.sendprogress.visibility = View.VISIBLE
            if (file == null) {
                chatBodyViewModel.sendMessage(
                    RequestBody.create(
                        "text/plain".toMediaTypeOrNull(),
                        intent.extras?.get("user").toString()
                    ),
                    RequestBody.create(
                        "text/plain".toMediaTypeOrNull(),
                        binding.message.text.toString()
                    ),
                    null
                )
            } else {
                chatBodyViewModel.sendMessage(
                    RequestBody.create(
                        "text/plain".toMediaTypeOrNull(),
                        intent.extras?.get("user").toString()
                    ),
                    RequestBody.create(
                        "text/plain".toMediaTypeOrNull(),
                        binding.message.text.toString()
                    ),
                    MultipartBody.Part.createFormData(
                        "file",
                        file?.getName(),
                        requestFile!!

                    )
                )
            }

            binding.message.setText("")
            file = null
            binding.sendprogress.visibility=View.GONE

        })
        chatBodyViewModel.getSendMessageModel().observe(this, Observer { t ->
            if (t.check) {
                chatBodyAdapter.addMessage(t.data!!)
                chatBodyAdapter.notifyDataSetChanged()
                if (intent.extras?.get("user_id") != null)
                    chatBodyViewModel.getMessages(intent.extras?.get("user_id").toString())
                else {
                    chatViewModel.getAllUsers()
                    chatViewModel.getChatModel().observe(this, Observer { t ->
                        for (item in 0..t.data?.size!! - 1) {
                            if (t.data?.get(item)?.id == intent.extras?.get("user")) {
                                chatBodyViewModel.getMessages(t.data?.get(item)?.conversation?.id.toString())
                                break
                            }
                        }
                    })
                }

                chatBodyAdapter.notifyDataSetChanged()
                binding.sendprogress.visibility = View.GONE
            }
        })
        binding.message.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                if (file == null) {
                    chatBodyViewModel.sendMessage(
                        RequestBody.create(
                            "text/plain".toMediaTypeOrNull(),
                            intent.extras?.get("user").toString()
                        ),
                        RequestBody.create(
                            "text/plain".toMediaTypeOrNull(),
                            binding.message.text.toString()
                        ),
                        null
                    )
                } else {
                    chatBodyViewModel.sendMessage(
                        RequestBody.create(
                            "text/plain".toMediaTypeOrNull(),
                            intent.extras?.get("user").toString()
                        ),
                        RequestBody.create(
                            "text/plain".toMediaTypeOrNull(),
                            binding.message.text.toString()
                        ),
                        MultipartBody.Part.createFormData(
                            "file",
                            file?.getName(),
                            requestFile!!

                        )
                    )
                }

                binding.message.setText("")
                file = null
                return@OnKeyListener true
            } else
                return@OnKeyListener false

        })
    }

    private fun showBottomSheet() {
        val bottomSheetDialog: BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout)
        val fileCard: CardView? = bottomSheetDialog.findViewById(R.id.sheetFile)
        val imageCard: CardView? = bottomSheetDialog.findViewById(R.id.sheetImage)
        val videoCard: CardView? = bottomSheetDialog.findViewById(R.id.sheetVideo)

        fileCard?.setOnClickListener(View.OnClickListener {
            if(SDK_INT >= 30) {
                if (!Environment.isExternalStorageManager()) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Permission needed!",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Settings") {
                            try {
                                val uri =
                                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                                val intent =
                                    Intent(
                                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                        uri
                                    )
                                startActivity(intent)
                            } catch (ex: Exception) {
                                val intent = Intent()
                                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                                startActivity(intent)
                            }
                        }
                        .show()
                }
            }
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
            bottomSheetDialog.dismiss()
        })
        imageCard?.setOnClickListener(View.OnClickListener { getContent.launch("image/*")
            bottomSheetDialog.dismiss()})
        videoCard?.setOnClickListener(View.OnClickListener { getContent.launch("video/*")
            bottomSheetDialog.dismiss()})
        bottomSheetDialog.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            file = File(this.let { data?.data?.let { it1 -> Util.getRealPathFromURI(it, it1) } })
            requestFile = RequestBody.create(
                data?.data?.let {
                    this.contentResolver?.getType(it)?.let { it.toMediaTypeOrNull() }
                },
                file!!
            )
        }
    }

    override fun onBackPressed() {

    }
}