package com.example.foodapp.bottom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activity.MainActivity
import com.example.foodapp.activity.MealActivtiy
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.databinding.FragmentMealBottomBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"



class mealBottomFragment : BottomSheetDialogFragment() {

    private var mealID: String? = null
    private lateinit var binding: FragmentMealBottomBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealID = it.getString(MEAL_ID)

        }

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealID?.let {
            viewModel.getMealById(it)
        }

        observeBottomMeal()
        onBottomDialogClick()
    }

    private fun onBottomDialogClick() {
        binding.bottom.setOnClickListener{
            if(mealName !=null && mealThumb !=null){
                val intent = Intent(activity,MealActivtiy::class.java)

               intent.apply {
                   putExtra(HomeFragment.MEAL_ID,mealID)
                   putExtra(HomeFragment.MEAL_NAME,mealName)
                   putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }
    private var mealName:String? = null
    private var mealThumb:String? = null
    private fun observeBottomMeal() {
        viewModel.observeBottomLiveData().observe(viewLifecycleOwner, Observer { meal->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottom)
            binding.tvBottomArea.text = meal.strArea
            binding.tvBottomCategory.text = meal.strCategory
            binding.tvBottomMealName.text = meal.strMeal

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        })
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String) =
            mealBottomFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}