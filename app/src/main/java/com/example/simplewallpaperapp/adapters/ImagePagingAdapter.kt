package com.example.simplewallpaperapp.adapters
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagination.models.Hit
import com.example.simplewallpaperapp.databinding.ItemImageBinding
import com.squareup.picasso.Picasso
class ImagePagingAdapter(var myOnClickListener: MyOnClickListener) : PagingDataAdapter<Hit, ImagePagingAdapter.Vh>(
    MyDiffUtill()
) {
    class MyDiffUtill : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }
    }
    inner class Vh(var itemImageBinding: ItemImageBinding) :
        RecyclerView.ViewHolder(itemImageBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(data: Hit) {
            Picasso.get().load(data.webformatURL)
                .into(itemImageBinding.image)
            itemImageBinding.image.setOnClickListener {
                myOnClickListener.onItemClick(data)
            }
        }
    }
    override fun onBindViewHolder(holder: Vh, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    interface MyOnClickListener{
        fun onItemClick(data: Hit)
    }
}