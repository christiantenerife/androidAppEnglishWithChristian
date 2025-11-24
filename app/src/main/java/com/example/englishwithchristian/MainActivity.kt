package com.example.englishwithchristian

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // 🔹 (Opcional) Leer actividades desde Firestore para comprobar conexión
        db.collection("activities")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val title = document.getString("title")
                    val url = document.getString("url")
                    Log.d("Firestore", "Actividad: $title ($url)")
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al leer actividades", e)
            }

        // Referencias a los botones
        val btnGrammar = findViewById<Button>(R.id.btnGrammar)
        val btnVocabulary = findViewById<Button>(R.id.btnVocabulary)
        val btnExams = findViewById<Button>(R.id.btnExams)

        // Navegación a cada sección
        btnGrammar.setOnClickListener {
            startActivity(Intent(this, GrammarActivity::class.java))
            Log.d("NAV", "Opening GrammarActivity")
            startActivity(Intent(this, GrammarActivity::class.java))
        }

        btnVocabulary.setOnClickListener {
            startActivity(Intent(this, VocabularyActivity::class.java))
        }

        btnExams.setOnClickListener {
            startActivity(Intent(this, ExamsActivity::class.java))
        }

        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            // Sign out the current user
            auth.signOut()

            // Go back to AuthActivity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        // 🔐 Comprobar si hay usuario autenticado
        if (auth.currentUser == null) {
            // Si no hay sesión, volver a AuthActivity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish() // Evita volver aquí con el botón atrás
        }
    }

}