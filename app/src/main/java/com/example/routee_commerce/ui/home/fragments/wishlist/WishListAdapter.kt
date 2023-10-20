package com.example.routee_commerce.ui.home.fragments.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routee_commerce.databinding.ItemWishlistBinding

class WishListAdapter(var items:List<com.example.domain.model.WishListItem?>?=null):RecyclerView.Adapter<WishListAdapter.ViewHolder>() {
    class ViewHolder(var viewBinding:ItemWishlistBinding):RecyclerView.ViewHolder(viewBinding.root){
        fun bind(item:com.example.domain.model.WishListItem){
            viewBinding.itemTitle.text = item.title
            viewBinding.priceText.text = item.price.toString()
        }
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemWishlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
         return ViewHolder(viewBinding)
     }

     override fun getItemCount(): Int {
         return items?.size?:0
     }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items?.get(position)!!)
     }

    fun bindItems(addedItems:List<com.example.domain.model.WishListItem?>?) {
        items = addedItems
        notifyDataSetChanged()
    }
}