package com.mohamedabdelaziz.socialapp.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.mohamedabdelaziz.socialapp.databinding.ActivityLoginBinding
import com.mohamedabdelaziz.socialapp.model.LoginModel
import com.mohamedabdelaziz.socialapp.viewmodel.LoginViewModel
import com.mohamedabdelaziz.socialapp.util.PrefKeys
import com.mohamedabdelaziz.socialapp.util.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val loginViewModel:   LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

       binding.login.setOnClickListener(View.OnClickListener {
           binding.loading.visibility=View.VISIBLE
           loginViewModel.login(binding.username.text.toString(), binding.password.text.toString())
       })
        loginViewModel.getLoginData().observe(this,
            Observer<LoginModel> { loginModel ->
                if (loginModel.isCheck) {
                    loginModel?.data?.token?.accessToken?.let {
                        PrefUtils.saveToPrefs(this,PrefKeys.USER_TOKEN,
                            it
                        )
                    }
                    loginModel.data?.user?.email?.let {
                        PrefUtils.saveToPrefs(this,PrefKeys.USER_EMAIL,
                            it
                        )
                    }
                    loginModel.data?.user?.name?.let {
                        PrefUtils.saveToPrefs(this,PrefKeys.USER_NAME,
                            it
                        )
                    }
                    loginModel.data?.user?.phone?.let {
                        PrefUtils.saveToPrefs(this,PrefKeys.USER_PHONE,
                            it
                        )
                    }
                    loginModel.data?.user?.avatar?.let {
                        PrefUtils.saveToPrefs(this,PrefKeys.USER_AVATAR,
                            it
                        )
                    }
                    loginModel.data?.user?.id?.let {
                        PrefUtils.saveToPrefs(this,PrefKeys.USER_ID,
                            it.toString()
                        )
                    }
                    Log.e(TAG, "onCreate: "+  PrefUtils.getFromPrefs(this,PrefKeys.USER_ID,"")+"")
                    PrefUtils.saveToPrefs(this,PrefKeys.ISLOGIN,true)
                    Toast.makeText(this, loginModel.message, Toast.LENGTH_SHORT).show()
                    binding.loading.visibility=View.GONE
                    startActivity(Intent(this,HomeActivity::class.java))
                } else {
                    Toast.makeText(this, loginModel.message, Toast.LENGTH_SHORT).show()
                    binding.loading.visibility=View.GONE
                }
            })

    }

    override fun onBackPressed() {

    }
}