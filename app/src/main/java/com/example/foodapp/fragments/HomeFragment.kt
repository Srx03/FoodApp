package com.example.foodapp.fragments


import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager


import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activity.CategoryMealsActivity
import com.example.foodapp.activity.MainActivity
import com.example.foodapp.activity.MealActivtiy
import com.example.foodapp.adapters.CategoriesAdapters
import com.example.foodapp.adapters.MostPopularAdapters
import com.example.foodapp.bottom.mealBottomFragment

import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal

import com.example.foodapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMVVM: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapters
    private lateinit var categoryItemsAdapter: CategoriesAdapters

    companion object{
        const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
        const val CATEGORY_MEAL = "com.example.foodapp.fragments.categoryName"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMVVM = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemsRecyclerView()


        homeMVVM.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMVVM.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMVVM.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()

        onPopularItemLongClick()

        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick = {meal ->
            val mealBottomFragment = mealBottomFragment.newInstance(meal.idMeal)
            mealBottomFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoryItemsAdapter.onItemClick ={category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_MEAL,category.strCategory)
            startActivity(intent)
        }
    }
    private fun prepareCategoriesRecyclerView() {
        categoryItemsAdapter = CategoriesAdapters()
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(context, 3,GridLayoutManager.VERTICAL, false)
            adapter = categoryItemsAdapter
        }
    }
    private fun observeCategoriesLiveData() {
       homeMVVM.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {  categories ->
        categories.forEach{category ->
            categoryItemsAdapter.setCategoryList(categories)
        }
       })
    }
    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {meal ->
        val intent = Intent(activity, MealActivtiy::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun preparePopularItemsRecyclerView() {
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }
    private fun observePopularItemsLiveData() {
        homeMVVM.observePopularItemsLiveData().observe(viewLifecycleOwner,
            { mealList ->
                popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)


            })
    }
    private fun onRandomMealClick() {
        binding.cvFirst.setOnClickListener {
            val intent = Intent(activity,MealActivtiy::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun observerRandomMeal() {
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner,
            { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgMeal)
                this.randomMeal = meal
        })
    }
}