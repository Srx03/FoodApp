package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemsBinding
import com.example.foodapp.pojo.MealsByCategory



class MostPopularAdapters(): RecyclerView.Adapter<MostPopularAdapters.PopularMealViewHolder>() {
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    var onLongItemClick: ((MealsByCategory) -> Unit)? = null
    private var mealsList = ArrayList<MealsByCategory>()

    fun setMeals(mealsList: ArrayList<MealsByCategory>){

        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.bindinig.imgPopularMeal)

            holder.itemView.setOnClickListener {
                onItemClick.invoke(mealsList[position])
            }
        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke((mealsList[position]))
            true
        }
    }

    override fun getItemCount(): Int {
        return  mealsList.size
    }

    class PopularMealViewHolder ( var bindinig: PopularItemsBinding):RecyclerView.ViewHolder(bindinig.root)
}