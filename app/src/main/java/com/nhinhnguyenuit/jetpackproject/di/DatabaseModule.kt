package com.nhinhnguyenuit.jetpackproject.di

import android.content.Context
import androidx.room.Room
import com.nhinhnguyenuit.jetpackproject.data.local.UserDao
import com.nhinhnguyenuit.jetpackproject.data.local.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext, UserDatabase::class.java, "user_database"
    ).build()

    @Provides
    fun provideUserDao(db: UserDatabase): UserDao = db.userDao()
}