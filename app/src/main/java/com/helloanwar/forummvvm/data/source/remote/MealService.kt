package com.helloanwar.forummvvm.data.source.remote

import com.helloanwar.forummvvm.data.model.MealResponse
import retrofit2.http.GET


interface MealService {
    @GET("1/search.php?s=")
    suspend fun getMeals(): MealResponse
}