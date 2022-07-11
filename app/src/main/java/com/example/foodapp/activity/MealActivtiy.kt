package com.example.foodapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealActivtiyBinding
import com.example.foodapp.db.MealDB
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewmodel.MealViewModel
import com.example.foodapp.viewmodel.MealViewModelFactory

class MealActivtiy : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var ytLink: String
    private lateinit var binding: ActivityMealActivtiyBinding
    private lateinit var mealMVVM: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealActivtiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDB = MealDB.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDB)
        mealMVVM = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        getMealInformationMealIntent()

        setInformationInViews()

        loadingCase()
        mealMVVM.getMealDetail(mealId)
        observeMealDetailLiveData()

        onYtImgClick()
        onFavoritesClick()

    }

    private fun onFavoritesClick() {
        binding.btnLike.setOnClickListener{
            mealToSave?.let {
                mealMVVM.insertUpdateMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onYtImgClick() {
        binding.imgYT.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ytLink))
            startActivity(intent)
        }
    }
    private var mealToSave:Meal?=null
    private fun observeMealDetailLiveData() {
        mealMVVM.observeMealDetailLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponsCase()
                val meal = t
                mealToSave = meal

                binding.tvCategory.text = "Category: ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal!!.strArea}"
                binding.tvInstructionsString.text = meal!!.strInstructions

                ytLink =meal.strYoutube
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgDetail)
        binding.collapsingToolbarLayout.title = mealName
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationMealIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLike.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYT.visibility = View.INVISIBLE
    }

    private fun onResponsCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnLike.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYT.visibility = View.VISIBLE
    }


}