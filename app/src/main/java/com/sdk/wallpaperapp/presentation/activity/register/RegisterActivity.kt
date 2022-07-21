package com.sdk.wallpaperapp.presentation.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.sdk.wallpaperapp.databinding.ActivityRegisterBinding
import com.sdk.wallpaperapp.presentation.activity.main.MainActivity
import com.sdk.wallpaperapp.util.error
import com.sdk.wallpaperapp.util.success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        binding.btnRegister.setOnClickListener {
            val name: String = binding.editName.text.toString().trim()
            val email: String = binding.editEmail.text.toString().trim()
            val password: String = binding.editPassword.text.toString().trim()
            lifecycleScope.launch {
                viewModel.registerUser(name, email, password)
                viewModel.user.collect {
                    when (it) {
                        is RegisterState.Init -> Unit
                        is RegisterState.Loading -> {
                            binding.btnRegister.isVisible = false
                            binding.progressBar.isVisible = true
                        }
                        is RegisterState.Error -> {
                            binding.btnRegister.isVisible = true
                            binding.progressBar.isVisible = false
                            binding.editName.requestFocus()
                            error(it.error)
                        }
                        is RegisterState.Success -> {
                            viewModel.saveUser(it.user.email, it.user.password)
                            binding.btnRegister.isVisible = true
                            binding.progressBar.isVisible = false
                            success("Successfully registered")
                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                            finishAffinity()
                        }
                    }
                }
            }
        }
        binding.textLogin.setOnClickListener {
            finish()
        }
    }
}