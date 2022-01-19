package com.mohamedabdelaziz.socialapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdelaziz.socialapp.R
import com.mohamedabdelaziz.socialapp.adapters.NotificationAdapter
import com.mohamedabdelaziz.socialapp.databinding.FragmentNotificationsBinding
import com.mohamedabdelaziz.socialapp.viewmodel.NotificationsViewModel

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private var recyclerView: RecyclerView?=null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val data = ArrayList<String>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add("Item " + i)
        }
        recyclerView=root.findViewById(R.id.notification_list)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter= NotificationAdapter(data)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}