package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.pojo.MealsByCategory

class CateogoriesMealsAdapters: RecyclerView.Adapter<CateogoriesMealsAdapters.CateogoriesMealsViewModel>() {
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    private var mealList = ArrayList<MealsByCategory>()

    fun setMealList(mealList: List<MealsByCategory>){

        this.mealList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()

    }

    inner class CateogoriesMealsViewModel(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateogoriesMealsViewModel {
        return CateogoriesMealsViewModel(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CateogoriesMealsViewModel, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}