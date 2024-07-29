package com.nhinhnguyenuit.jetpackproject.di

import com.nhinhnguyenuit.jetpackproject.data.network.ApiService
import com.nhinhnguyenuit.jetpackproject.data.network.RetrofitInstance
import com.nhinhnguyenuit.jetpackproject.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//    @Provides
//    @Singleton
//    fun provideApiService(): ApiService{
//        return RetrofitInstance.api
//    }

//    @Provides
//    @Singleton
//    fun provideRepository(apiService: ApiService): UserRepository{
//        return UserRepository(apiService)
//    }
}