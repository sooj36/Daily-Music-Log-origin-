package com.sooj.today_music.di

import android.content.Context
import androidx.room.Room
import com.sooj.today_music.room.Database
import com.sooj.today_music.room.TrackDao
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
    fun provideDatabase(
        @ApplicationContext context :Context) : Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            Database.NAME
        )
//            .fallbackToDestructiveMigration() // 기존 데이터 삭제 후 새로 생성
            .build()
    }

    @Provides
    @Singleton
    fun provideTrackDao(database: Database) : TrackDao {
        return database.getTrackDao()
    }
}