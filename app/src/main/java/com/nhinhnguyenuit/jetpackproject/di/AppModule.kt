package com.nhinhnguyenuit.jetpackproject.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AppModule {
    @Binds
    fun application(app: Application): Context

}