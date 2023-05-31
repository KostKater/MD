package com.dicoding.kostkater.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
    }
}