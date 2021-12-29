package com.yusuftalhaklc.akotlinquotes

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yusuftalhaklc.akotlinquotes.api.QuoteJson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://api.quotable.io/"
class MainActivity : AppCompatActivity() {

    var clicked : Boolean = true
    private lateinit var database : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = this.openOrCreateDatabase("Quote", Context.MODE_PRIVATE,null)
        database.execSQL("CREATE TABLE IF NOT EXISTS quotes (id INTEGER PRIMARY KEY, quoteContent TEXT, quoteAuthor VARCHAR)")

        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.gray)

        getCurrentData()
    }

    fun getCurrentData(){

        val gson :Gson = GsonBuilder().setLenient().create()

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiRequest::class.java)

        val cal : Call<QuoteJson> = api.getQuote()

        cal.enqueue(object : Callback<QuoteJson> {
            override fun onFailure(call: Call<QuoteJson>, t: Throwable) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<QuoteJson>, response: Response<QuoteJson>) {
                authorTextView.text =  response.body()!!.author
                quoteTextView.text=  response.body()!!.content
                animationView.progress = 0F
                clicked = true
                control()
            }
        })


    }

    fun clicked (view : View){
        getCurrentData()
    }

    fun favoritedClicked(view : View){
        clickedFav()
    }

    fun clickedFav(){
        val quote = quoteTextView.text.toString()
        val author = authorTextView.text.toString()


        if(clicked){
            val values = ContentValues().apply {
                put("quoteContent", quote)
                put("quoteAuthor",author)
            }
            database.insert("quotes",null,values)

            animationView.playAnimation()
            clicked = false
        }
        else {
            try {
                database.delete("quotes", "quoteContent"+ "=?", arrayOf(quote)) > 0
            }
            catch (e: Exception){
                e.printStackTrace()
            }

            animationView.progress = 0F
            clicked = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    fun control(){

        val cursor = database.rawQuery("SELECT * FROM quotes WHERE quoteContent = ?", arrayOf(quoteTextView.text.toString()))

        val quoteContentIx = cursor.getColumnIndex("quoteContent")

        while(cursor.moveToNext()){
            if( quoteTextView.text.toString() == cursor.getString(quoteContentIx)){
                animationView.playAnimation()
                clicked = false
            }
        }
        cursor.close()
    }

    fun buttonClicked(view:View){
        val intent = Intent(this@MainActivity,FavoritesActivity::class.java)
        startActivity(intent)
    }

}
