package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDB
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDB: MealDB
): ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    private var favoritesLiveData = MutableLiveData<List<Meal>>()
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })


    }
    fun observeMealDetailLiveData(): LiveData<Meal>{
        return mealDetailsLiveData
    }

    fun insertUpdateMeal(meal: Meal){
       viewModelScope.launch{
           mealDB.mealDao().insertUpdateMeal(meal)

       }
    }


    fun observeFavoriteslLiveData(): LiveData<List<Meal>>{
        return favoritesLiveData
    }

}