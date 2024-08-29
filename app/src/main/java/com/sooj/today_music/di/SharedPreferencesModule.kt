package com.sooj.today_music.di

import com.sooj.today_music.data.MemoRepositoryImpl
import com.sooj.today_music.domain.MemoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object SharedPreferencesModules {
//
//    @Provides
//    @Singleton
//    fun providesSharedPreferences(@ApplicationContext context : Context) : SharedPreferences { // 인스턴스 생성하여, 애플 전역 사용하도록 제공
//        return context.getSharedPreferences("",Context.MODE_PRIVATE)
//    }
//
//    @Provides
//    @Singleton
//    fun providesSharedPreferencesEditor(sharedPreferences: SharedPreferences) : SharedPreferences { // Editor 객체 사용하여, sp에 데이터 쓰는 작업 가능하게 제공
//        return sharedPreferences
//    }
//}

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