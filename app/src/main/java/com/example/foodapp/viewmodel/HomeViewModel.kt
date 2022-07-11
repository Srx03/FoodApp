package com.example.foodapp.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDB
import com.example.foodapp.pojo.*

import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(
    private val mealDB: MealDB
): ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categiresLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDB.mealDao().getAllMeals()
    private var bottomLiveData = MutableLiveData<Meal>()
    private val searchMealsLiveData = MutableLiveData<List<Meal>>()
    fun getRandomMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() !=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                       randomMealLiveData.value = randomMeal
                }else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })

    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategroyList>{

            override fun onResponse(call: Call<MealsByCategroyList>, response: Response<MealsByCategroyList>) {
                if(response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategroyList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })

    }

    fun getCategories(){
        RetrofitInstance.api.getCategoires().enqueue(object  : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {
                    categoryList ->
                    categiresLiveData.postValue(categoryList.categories)
                }


            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })

    }

    fun getMealById(id: String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {meal ->
                    bottomLiveData.postValue(meal)
                }


            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDB.mealDao().deleteMeal(meal)
        }
    }

    fun insertUpdateMeal(meal: Meal){
        viewModelScope.launch{
            mealDB.mealDao().insertUpdateMeal(meal)

        }
    }

    fun searchMeals(searchQuery: String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object : Callback<MealList>{
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val mealsList = response.body()?.meals
            mealsList?.let {
                searchMealsLiveData.postValue(it)
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.d("HomeViewModel", t.message.toString())
            }
        }
    )

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>>{
        return categiresLiveData
    }
    fun observeFavoritesLiveData(): LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }

    fun observeBottomLiveData(): LiveData<Meal>{
        return bottomLiveData
    }

    fun observeSearchMealsLiveData(): LiveData<List<Meal>>{
        return searchMealsLiveData
    }


    }


