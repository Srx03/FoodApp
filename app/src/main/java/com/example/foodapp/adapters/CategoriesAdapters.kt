package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.pojo.Category


class CategoriesAdapters(): RecyclerView.Adapter<CategoriesAdapters.CategoiresViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick: ((Category) ->Unit)? = null
    fun setCategoryList(categoriesList:List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoiresViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoiresViewHolder {
        return CategoiresViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoiresViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.ImgCategoryItem)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
       holder.itemView.setOnClickListener {
           onItemClick!!.invoke(categoriesList[position])

       }


    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}