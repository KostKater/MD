package com.dicoding.kostkater.ui.register

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.ActivityRegisterBinding
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.ui.ViewModelFactory
import com.dicoding.kostkater.ui.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), null)
        )[RegisterViewModel::class.java]

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        registerViewModel.registerResponse.observe(this) { registerResponse ->
            if (registerResponse.userInfo == null) {
                showAlertDialog("Oops!", registerResponse.message, false)
                return@observe
            }
            showAlertDialog("Hore!", registerResponse.message, true)
        }
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val email = binding.edRegisterEmail.text.toString()
            binding.emailEditTextLayout.error = null

            val password = binding.edRegisterPassword.text.toString()
            binding.passwordEditTextLayout.error = null

            val confirmPassword = binding.edRegisterPassword2.text.toString()
            binding.passwordEditTextLayout2.error = null

            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = getString(R.string.required)
                }

                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = getString(R.string.required)
                }

                confirmPassword.isEmpty() -> {
                    binding.passwordEditTextLayout2.error = getString(R.string.required)
                }

                confirmPassword != password -> {
                    binding.passwordEditTextLayout2.error = getString(R.string.not_matched)
                }

                else -> {
                    registerViewModel.register(email, password)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showAlertDialog(title: String, message: String, success: Boolean) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            if (success) {
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            } else {
                setNegativeButton(getString(R.string.try_again)) { _, _ ->
                    val intent = Intent(context, RegisterActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
            create()
            show()
        }
    }
}