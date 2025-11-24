package com.example.englishwithchristian

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class GrammarActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var listView: ListView
    private val grammarList = mutableListOf<Pair<String, String>>() // Pair(title, url)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar)

        // 🔹 Toolbar + back arrow
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Grammar"
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        // 🔹 Force arrow to always go straight back to MainActivity
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        listView = findViewById(R.id.listViewGrammar)
        db = FirebaseFirestore.getInstance()

        // Get only activities of type "grammar"
        db.collection("activities")
            .whereEqualTo("type", "grammar")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val title = document.getString("title") ?: "Untitled"
                    val url = document.getString("url") ?: ""
                    grammarList.add(Pair(title, url))
                }

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    grammarList.map { it.first }
                )
                listView.adapter = adapter

                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        val selectedUrl = grammarList[position].second
                        val intent = Intent(this, WebViewActivity::class.java)
                        intent.putExtra("url", selectedUrl)
                        startActivity(intent)
                    }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}