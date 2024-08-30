package com.sooj.today_music.di


import com.sooj.today_music.data.MemoRepositoryImpl
import com.sooj.today_music.domain.MemoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class SharedPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindsMemoRepository(
        memoRepositoryImpl: MemoRepositoryImpl
    ) : MemoRepository
}

// Application : 애플리케이션의 수명 동안 지속되는 전역 context
