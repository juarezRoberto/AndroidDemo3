package com.juarez.androiddemo3.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.jwt.JWT
import com.juarez.androiddemo3.databinding.ActivityHomeBinding
import com.juarez.androiddemo3.utils.toast

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("jwt")

        token?.let {
            try {
                val jwt = JWT(token)

                binding.txtTitular.text = "titular: ${jwt.getClaim("titular").asString()}"
                binding.txtUrl.text = "url: ${jwt.getClaim("url").asString()}"
                binding.txtEmail.text = "email: ${jwt.getClaim("email").asString()}"
                binding.txtSolicitud.text = "solicitud: ${jwt.getClaim("solicitud").asString()}"
            } catch (e: Throwable) {
                toast("Error in JWT")
            }
        }
    }
}