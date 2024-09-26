package com.sooj.today_music.data

import com.sooj.today_music.domain.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton
/** Retrofit 인스턴스 제공
 * endpoint 객체를 dagger hilt 통해 의존성 주입하여 제공 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun RetrofitInstance_build() : ApiService_EndPoint {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService_EndPoint::class.java)
    }
}

/**
object RetrofitInstance_build {
    val musicApi : ApiService_EndPoint by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService_EndPoint::class.java)
    }
}
*/

//object RetrofitInstance_build {
//    private const val baseUrl = ""
//
//    private fun getInstance() : Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val musicApi : ApiService_EndPoint = getInstance().create(ApiService_EndPoint::class.java)
//}
