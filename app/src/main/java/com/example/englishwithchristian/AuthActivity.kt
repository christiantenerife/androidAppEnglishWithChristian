package com.example.englishwithchristian

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvError: TextView
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Firebase instances
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // UI references
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        tvError = findViewById(R.id.tvError)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        // Si ya hay usuario logueado, ir directo a MainActivity
        auth.currentUser?.let {
            goToMain()
        }

        btnLogin.setOnClickListener {
            tvError.visibility = View.GONE
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                showError("Introduce email y contraseña.")
            } else {
                loginUser(email, password)
            }
        }

        btnRegister.setOnClickListener {
            tvError.visibility = View.GONE
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                showError("Introduce email and password.")
            } else if (password.length < 6) {
                showError("The password should have at least six characters.")
            } else {
                registerUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        btnLogin.isEnabled = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    goToMain()
                } else {
                    showError("Error al iniciar sesión: ${task.exception?.localizedMessage}")
                }
            }
    }

    private fun registerUser(email: String, password: String) {
        btnRegister.isEnabled = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                btnRegister.isEnabled = true
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                    val userData = hashMapOf(
                        "uid" to uid,
                        "email" to email,
                        "level" to "B2",
                        "source" to "app",
                        "createdAt" to com.google.firebase.Timestamp.now()
                    )
                    db.collection("users").document(uid).set(userData)
                    goToMain()
                } else {
                    showError("Error al registrar: ${task.exception?.localizedMessage}")
                }
            }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
    }
}