package com.sooj.today_music.di

import com.sooj.today_music.data.MemoRepositoryImpl
import com.sooj.today_music.data.SearchRepositoryImpl
import com.sooj.today_music.domain.MemoRepository
import com.sooj.today_music.domain.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/** Repository 제공 역할
 * 네트워크로부터 데이터를 가져오는 역할하는 Repo를 Dagger Hilt 통해 의존성 주입으로 제공
@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideSearchRepository(api : ApiService_EndPoint) : SearchRepository {
        return SearchRepositoryImpl(api)
    }
}
*/

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ) : SearchRepository

    @Binds
    @Singleton
    abstract fun bindsMemoRepository(
        memoRepositoryImpl: MemoRepositoryImpl
    ) : MemoRepository
}