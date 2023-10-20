package com.example.routee_commerce.ui.home.fragments.categories.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Category
import com.example.routee_commerce.R
import com.example.routee_commerce.databinding.ItemCategoryRectBinding

class CategoriesAdapter(private var categories: List<Category?>? = null) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    class ViewHolder(val itemCategoryBinding: ItemCategoryRectBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {
        fun bind(category: Category?, isSelected: Boolean) {
            itemCategoryBinding.category = category
            itemCategoryBinding.executePendingBindings()
            if (isSelected) {
                itemCategoryBinding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemCategoryBinding.root.context,
                        R.color.white
                    )
                )
                itemCategoryBinding.draggingBar.visibility = View.VISIBLE
            } else {
                itemCategoryBinding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemCategoryBinding.root.context,
                        R.color.rv_bg
                    )
                )
                itemCategoryBinding.draggingBar.visibility = View.INVISIBLE
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryRectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = categories?.size ?: 0

    var selectedPosition = 0
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories!![position]!!
        holder.bind(category, selectedPosition == position)
        categoryClicked?.let { categoryClicked ->
            holder.itemView.setOnClickListener {
                notifyItemChanged(selectedPosition)
                selectedPosition = position
                notifyItemChanged(position)
//                categoryClicked.invoke(position, category)

            }
        }


    }

    fun bindCategories(categories: List<Category?>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    var categoryClicked: ((position: Int, category: Category) -> Unit)? = null
}