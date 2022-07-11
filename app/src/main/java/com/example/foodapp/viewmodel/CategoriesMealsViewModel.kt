package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.MealsByCategroyList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesMealsViewModel: ViewModel() {
    private var categoriesMealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategroyList>{
            override fun onResponse(
                call: Call<MealsByCategroyList>,
                response: Response<MealsByCategroyList>
            ) {
                //if(response.body() != null){
                 //  categoriesMealsLiveData.value = response.body()!!.meals
                //}
                //else return
                response.body()?.let {mealsList ->
                    categoriesMealsLiveData.postValue(mealsList.meals)

                }

            }

            override fun onFailure(call: Call<MealsByCategroyList>, t: Throwable) {
                Log.d("CategoriesViewModel", t.message.toString())
            }
        })


    }

    fun observeCategoriesMealsLiveData(): LiveData<List<MealsByCategory>> {
        return categoriesMealsLiveData
    }
}