package com.example.foodapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodapp.R
import com.example.foodapp.db.MealDB
import com.example.foodapp.viewmodel.HomeViewModel
import com.example.foodapp.viewmodel.HomeViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  val viewModel: HomeViewModel by lazy {
      val mealDB = MealDB.getInstance(this)
      val homeViewModelProviderFactory = HomeViewModelFactory(mealDB)
      ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



val navController = Navigation.findNavController(this, R.id.main_fragment)

        NavigationUI.setupWithNavController(btm_nav,navController)


    }
}