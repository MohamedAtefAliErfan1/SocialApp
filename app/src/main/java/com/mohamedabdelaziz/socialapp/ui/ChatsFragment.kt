package com.mohamedabdelaziz.socialapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamedabdelaziz.socialapp.R
import com.mohamedabdelaziz.socialapp.adapters.ChatsAdapter
import com.mohamedabdelaziz.socialapp.databinding.FragmentChatsBinding
import com.mohamedabdelaziz.socialapp.model.ChatModel
import com.mohamedabdelaziz.socialapp.viewmodel.ChatsViewModel
import com.mohamedabdelaziz.socialapp.util.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsFragment : Fragment() {
    private var _binding: FragmentChatsBinding? = null
    val chatsViewModel: ChatsViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.option.setOnClickListener(View.OnClickListener {   PrefUtils.clearPrefs(context)
            context?.startActivity(Intent(context, LoginActivity::class.java)) })
        binding.back.setOnClickListener(View.OnClickListener {    HomeActivity.navView.selectedItemId =
            R.id.navigation_dashboard })
        chatsViewModel.getAllUsers()
        chatsViewModel.getChatModel().observe(requireActivity(), Observer { chatModel ->
          binding.chatList.layoutManager=LinearLayoutManager(context)
          binding.chatList.adapter= context?.let { ChatsAdapter(chatModel.data as ArrayList<ChatModel.Data>, it) }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}