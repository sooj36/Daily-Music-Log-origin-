package com.sooj.today_music.di

import com.sooj.today_music.data.ApiService_EndPoint
import com.sooj.today_music.data.SearchRepositoryImpl
import com.sooj.today_music.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideSearchRepository(
        api : ApiService_EndPoint
    ) : SearchRepository {
        return SearchRepositoryImpl(api)
    }
}