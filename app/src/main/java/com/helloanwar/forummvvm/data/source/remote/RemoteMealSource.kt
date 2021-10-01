package com.helloanwar.forummvvm.data.source.remote

import com.helloanwar.forummvvm.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteMealSource {
    suspend fun <T> getMeals(): Resource<T> = withContext(Dispatchers.IO) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: MealService = retrofit.create(MealService::class.java)

        return@withContext try {
            val response = service.getMeals()
            Resource.Success(response as T)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}