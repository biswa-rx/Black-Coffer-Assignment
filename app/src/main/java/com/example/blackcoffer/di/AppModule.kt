package com.example.blackcoffer.di

import com.example.blackcoffer.data.explore.RemoteExploreRequest
import com.example.blackcoffer.domain.explore.ExploreRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExploreRequest(): ExploreRequest {
        return RemoteExploreRequest()
    }
}