package com.example.simplewallpaperapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pagination.models.Hit
import com.example.simplewallpaperapp.databinding.ItemImageBinding
import com.squareup.picasso.Picasso
class RvAdapter(var list: List<Hit>, var onItemClickListener: OnItemClickListener): RecyclerView.Adapter<RvAdapter.MyViewHolder>(){
    inner class MyViewHolder(val itemImageBinding: ItemImageBinding):RecyclerView.ViewHolder(
        itemImageBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        Picasso.get().load(obj.webformatURL)
            .into(holder.itemImageBinding.image)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemMusic(obj)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener {
        fun onItemMusic(hit: Hit)
    }
}