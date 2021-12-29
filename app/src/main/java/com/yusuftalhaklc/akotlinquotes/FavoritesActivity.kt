package com.yusuftalhaklc.akotlinquotes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusuftalhaklc.akotlinquotes.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var quoteList : ArrayList<Quotem>
    private lateinit var quoteAdapter : QuoteAdapter

    private lateinit var binding : ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoritesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.gray)

        quoteList = ArrayList<Quotem>()
        quoteAdapter = QuoteAdapter(quoteList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = quoteAdapter

        try{

        val database = this.openOrCreateDatabase("Quote", Context.MODE_PRIVATE,null)

        val cursor = database.rawQuery("SELECT * FROM quotes ORDER BY id DESC",null)
        val quoteIx = cursor.getColumnIndex("quoteContent")
        val authIx = cursor.getColumnIndex("quoteAuthor")

        while (cursor.moveToNext()){

            val quote = cursor.getString(quoteIx)
            val auth = cursor.getString(authIx)
            val quoteA = Quotem(quote,auth)
            quoteList.add(quoteA)

        }
            quoteAdapter.notifyDataSetChanged()

            cursor.close()
        }


        catch (e: Exception){
            e.printStackTrace()
        }


    }
    fun backClicked (view :View){
        val intent = Intent(this@FavoritesActivity,MainActivity::class.java)
        startActivity(intent)

    }
}