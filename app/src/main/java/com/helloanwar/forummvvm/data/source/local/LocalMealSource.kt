package com.helloanwar.forummvvm.data.source.local

import com.helloanwar.forummvvm.data.Resource
import com.helloanwar.forummvvm.data.model.MealResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalMealSource {
    suspend fun <T> getMeals(): Resource<T> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = MealResponse()
            Resource.Success(response as T)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}