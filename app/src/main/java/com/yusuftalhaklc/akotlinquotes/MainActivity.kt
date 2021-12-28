package com.yusuftalhaklc.akotlinquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yusuftalhaklc.akotlinquotes.api.QuoteJson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.quotable.io/"
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            }

        })
    }
    fun clicked (view : View){
        getCurrentData()
    }


}
