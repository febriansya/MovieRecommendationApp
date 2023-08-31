package com.example.movierecommendationapp.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierecommendationapp.databinding.MovieItemBinding
import com.example.movierecommendationapp.domain.model.Movie

class HomeAdapater(
    val list: ArrayList<Movie>
) : RecyclerView.Adapter<HomeAdapater.ViewHolder>() {
    class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val date = binding.tvTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: MovieItemBinding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return minOf(list.size, 10)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = list[position]
        holder.title.text = movie.judul
        holder.date.text = movie.tanggal
    }
}