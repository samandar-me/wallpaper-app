package com.sdk.wallpaperapp.presentation.activity.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.sdk.wallpaperapp.databinding.ActivityLoginBinding
import com.sdk.wallpaperapp.presentation.activity.main.MainActivity
import com.sdk.wallpaperapp.presentation.activity.register.RegisterActivity
import com.sdk.wallpaperapp.util.error
import com.sdk.wallpaperapp.util.success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        binding.btnLogin.setOnClickListener {
            val email: String = binding.editEmail.text.toString().trim()
            val password: String = binding.editPassword.text.toString().trim()
            lifecycleScope.launch {
                viewModel.loginUser(email, password)
                viewModel.user.collect {
                    when (it) {
                        is LoginState.Init -> Unit
                        is LoginState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.btnLogin.isVisible = false
                        }
                        is LoginState.Error -> {
                            binding.progressBar.isVisible = false
                            binding.btnLogin.isVisible = true
                            binding.editEmail.requestFocus()
                            error(it.e)
                        }
                        is LoginState.Success -> {
                            binding.progressBar.isVisible = false
                            binding.btnLogin.isVisible = true
                            success("Successfully logged in")
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }
        binding.textRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}