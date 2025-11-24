package com.example.englishwithchristian

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ExamsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var listView: ListView
    private val examList = mutableListOf<Pair<String, String>>() // Pair(title, url)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exams)

        // 🔹 Toolbar + back arrow
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Exams"
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        listView = findViewById(R.id.listViewExams)
        db = FirebaseFirestore.getInstance()

        // 🔹 Get only exams, ordered by numeric 'part'
        db.collection("activities")
            .whereEqualTo("type", "exam")
            .orderBy("part", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                examList.clear()
                for (document in result) {
                    val title = document.getString("title") ?: "Untitled"
                    val url = document.getString("url") ?: ""
                    examList.add(Pair(title, url))
                }

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    examList.map { it.first }
                )
                listView.adapter = adapter

                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        val selectedUrl = examList[position].second
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
