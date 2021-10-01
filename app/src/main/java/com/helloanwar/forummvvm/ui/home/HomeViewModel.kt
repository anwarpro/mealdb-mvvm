package com.helloanwar.forummvvm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloanwar.forummvvm.data.Resource
import com.helloanwar.forummvvm.data.model.MealResponse
import com.helloanwar.forummvvm.data.source.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val mealRepository: MealRepository
) : ViewModel() {

    private val _meals = MutableLiveData<Resource<MealResponse>>()
    val meal: LiveData<Resource<MealResponse>>
        get() = _meals

    fun getMeals() = viewModelScope.launch {
        _meals.value = Resource.Loading
        _meals.value = mealRepository.getMeals()
    }

    init {
        getMeals()
    }
}