package com.yusuftalhaklc.akotlinquotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusuftalhaklc.akotlinquotes.databinding.RecyclerRowBinding

class QuoteAdapter(val quoteList : ArrayList<Quotem>) : RecyclerView.Adapter<QuoteAdapter.QuoteHolder>() {

    class QuoteHolder(val binding:RecyclerRowBinding) :RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuoteHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteHolder, position: Int) {
        holder.binding.quoteTextView2.text = quoteList.get(position).quote
        holder.binding.authorTextView2.text = quoteList.get(position).author
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

}
