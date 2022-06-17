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
import java.io.IOException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUsers()

        binding.btnLogin.setOnClickListener {

            // email admin@macropay.mx
            // Admin1

            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                toast("username and password are required")
            } else {
                viewModel.login(username, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Error -> {
                        binding.loginProgressBar.isVisible = false
                        binding.btnLogin.isEnabled = true

                        if (state.throwable is IOException) {
                            toast("Check your connection")
                        } else toast("Invalid credentials")
                    }
                    is LoginState.Loading -> {
                        binding.loginProgressBar.isVisible = true
                        binding.btnLogin.isEnabled = false
                    }
                    is LoginState.Success -> {
                        binding.loginProgressBar.isVisible = false
                        binding.btnLogin.isEnabled = true

                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        intent.putExtra("jwt", state.tokenWrapper.token)
                        startActivity(intent)
                    }
                    is LoginState.Completed -> {
                        toast("completed")
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.usersState.collect { usersState ->
                when (usersState) {
                    is GetUsersState.Loading -> {
                        if (usersState.isLoading) toast("Loading") else toast("No Loading")
                    }
                    is GetUsersState.Error -> {
                        toast(usersState.throwable.localizedMessage ?: "unknown error")
                    }
                    is GetUsersState.Success -> toast(usersState.users.toString())
                    else -> Unit
                }
            }
        }
    }
}