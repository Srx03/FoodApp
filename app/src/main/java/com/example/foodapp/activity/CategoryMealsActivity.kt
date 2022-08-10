package com.example.foodapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapters.CateogoriesMealsAdapters
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.viewmodel.CategoriesMealsViewModel



class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealMVVM: CategoriesMealsViewModel
    private lateinit var categoryMealsAdapters: CateogoriesMealsAdapters
    private lateinit var meal: MealsByCategory



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        onCategoryItemClick()

        categoryMealMVVM = ViewModelProviders.of(this)[CategoriesMealsViewModel::class.java]

        categoryMealMVVM.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_MEAL)!!)

        categoryMealMVVM.observeCategoriesMealsLiveData().observe(this, Observer { mealList ->
        categoryMealsAdapters.setMealList(mealList)
            binding.tvCategoryCount.text = "Ima ukupno " + mealList.size.toString() + " obroka"




        })


    }

    private fun prepareRecyclerView() {
        categoryMealsAdapters = CateogoriesMealsAdapters()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapters
        }
    }

    private fun onCategoryItemClick() {
        categoryMealsAdapters.onItemClick = {meal ->
            val intent = Intent(this, MealActivtiy::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

}