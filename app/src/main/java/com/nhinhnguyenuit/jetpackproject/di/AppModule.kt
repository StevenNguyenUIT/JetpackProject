package com.nhinhnguyenuit.jetpackproject.di

import android.content.Context
import androidx.room.Room
import com.nhinhnguyenuit.jetpackproject.data.local.UserDao
import com.nhinhnguyenuit.jetpackproject.data.local.UserDatabase
import com.nhinhnguyenuit.jetpackproject.data.network.ApiService
import com.nhinhnguyenuit.jetpackproject.data.network.RetrofitInstance
import com.nhinhnguyenuit.jetpackproject.data.repository.UserRepository
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
    fun provideApiService(): ApiService{
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext appContext: Context): UserDatabase{
        return Room.databaseBuilder(
            appContext,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    fun provideUserDao(db: UserDatabase): UserDao{
        return db.userDao()
    }

    @Provides
    fun provideUserRepository(apiService: ApiService, userDao: UserDao): UserRepository{
        return UserRepository(apiService, userDao)
    }
}