package com.example.englishwithchristian

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class VocabularyActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var listView: ListView
    private val vocabularyList = mutableListOf<Pair<String, String>>() // Pair(title, url)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocabulary)

        // 🔹 Toolbar + Back arrow
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Vocabulary"
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        listView = findViewById(R.id.listViewVocabulary)
        db = FirebaseFirestore.getInstance()

        // Get only activities of type "vocabulary"
        db.collection("activities")
            .whereEqualTo("type", "vocabulary")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val title = document.getString("title") ?: "Untitled"
                    val url = document.getString("url") ?: ""
                    vocabularyList.add(Pair(title, url))
                }

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    vocabularyList.map { it.first }
                )
                listView.adapter = adapter

                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        val selectedUrl = vocabularyList[position].second
                        val intent = Intent(this, WebViewActivity::class.java)
                        intent.putExtra("url", selectedUrl)
                        startActivity(intent)
                    }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}

