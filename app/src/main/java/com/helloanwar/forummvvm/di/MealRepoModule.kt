package com.helloanwar.forummvvm.di

import com.helloanwar.forummvvm.data.source.MealRepository
import com.helloanwar.forummvvm.data.source.local.LocalMealSource
import com.helloanwar.forummvvm.data.source.remote.RemoteMealSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MealRepoModule {

    @Provides
    fun provideMealRepo(): MealRepository {
        return MealRepository(RemoteMealSource(), LocalMealSource())
    }
}