package com.juarez.androiddemo3.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.juarez.androiddemo3.databinding.ActivityMainBinding
import com.juarez.androiddemo3.home.HomeActivity
import com.juarez.androiddemo3.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.IOException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                toast("username and password are required")
            } else {
                viewModel.login(username, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is LoginState.Error -> {
                        if (it.exception is IOException) {
                            toast("Check your connection")
                        } else toast("Invalid credentials")
                    }
                    is LoginState.Loading -> {
                        binding.loginProgressBar.isVisible = it.isLoading
                        binding.btnLogin.isEnabled = !it.isLoading
                    }
                    is LoginState.Success -> {
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        intent.putExtra("jwt", it.data)
                        startActivity(intent)
                    }
                    else -> Unit
                }
            }
        }
    }
}