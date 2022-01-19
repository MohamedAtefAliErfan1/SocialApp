package com.mohamedabdelaziz.socialapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mohamedabdelaziz.socialapp.R
import com.mohamedabdelaziz.socialapp.databinding.FragmentProfileBinding
import com.mohamedabdelaziz.socialapp.viewmodel.ProfileViewModel
import com.mohamedabdelaziz.socialapp.util.PrefKeys
import com.mohamedabdelaziz.socialapp.util.PrefUtils
import dagger.hilt.android.AndroidEntryPoint


import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.mohamedabdelaziz.socialapp.util.Util
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    lateinit var avatar: File
    private val binding get() = _binding!!
    lateinit var requestFile: RequestBody
    val profileViewModel: ProfileViewModel by viewModels()
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

        binding.avatar.setImageURI(uri)
        avatar = File(context?.let { uri?.let { it1 -> Util.getRealPathFromURI(it, it1) } })
        Glide.with(this)
            .load(context?.let { uri?.let { it1 -> Util.getRealPathFromURI(it, it1) } })
            .into(binding.avatar)
        requestFile=RequestBody.create(
            uri?.let { context?.contentResolver?.getType(it)?.let { it.toMediaTypeOrNull() } },
            avatar
        )

    }

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        activity?.setActionBar(binding.profileToolbar)
        binding.option.setOnClickListener(View.OnClickListener {
           PrefUtils.clearPrefs(context)
            context?.startActivity(Intent(context, LoginActivity::class.java))

        })

        binding.back.setOnClickListener(View.OnClickListener {
            HomeActivity.navView.selectedItemId =
                R.id.navigation_home
        })
        binding.camera.setOnClickListener(View.OnClickListener {
            getContent.launch("image/*")
        })
        binding.toolBarName.setText(
            PrefUtils.getFromPrefs(context, PrefKeys.USER_NAME, "").toString()
        )
        binding.name.setText(PrefUtils.getFromPrefs(context, PrefKeys.USER_NAME, "").toString())

        binding.profileEmail.setText(
            PrefUtils.getFromPrefs(context, PrefKeys.USER_EMAIL, "").toString()
        )
        binding.profileUserName.setText(
            PrefUtils.getFromPrefs(context, PrefKeys.USER_NAME, "").toString()
        )
        binding.positionEditText.setText(
            PrefUtils.getFromPrefs(context, PrefKeys.USER_PHONE, "").toString()
        )
        Glide.with(this)
            .load(PrefUtils.getFromPrefs(context,PrefKeys.USER_AVATAR,""))
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.avatar)

        binding.save.setOnClickListener(View.OnClickListener {
            binding.loading.visibility = View.VISIBLE
            profileViewModel.updateProfile(
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                  ""
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    binding.profilePassword.text.toString()
                ),
                MultipartBody.Part.createFormData(
                    "avatar",
                    avatar.getName(),
                   requestFile
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                   ""
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                   ""
                )

            )

        })
        profileViewModel.getProfileData().observe(requireActivity(), Observer { profilemodel ->
            if (profilemodel.isCheck) {
                Toast.makeText(context, profilemodel.message, Toast.LENGTH_SHORT).show()
                binding.loading.visibility = View.GONE
                profilemodel.data?.avatar?.let {
                    PrefUtils.saveToPrefs(context,PrefKeys.USER_AVATAR,
                        it
                    )
                }
            } else {
                Toast.makeText(context, profilemodel.message, Toast.LENGTH_SHORT).show()
                binding.loading.visibility = View.GONE
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}