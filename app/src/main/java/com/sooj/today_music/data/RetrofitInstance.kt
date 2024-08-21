package com.sooj.today_music.data

import com.sooj.today_music.domain.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object RetrofitInstance {
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//}
// apiservice2를 생성할 수 없기에, 실제 api 호출 불가 (실제 api 인스턴스 생성 부분 누락)

object RetrofitInstance {
    val api : ApiService2 by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService2::class.java)
    }
}

// Retrofit 설정 담고 있는 객체

/** Retrofit 인스턴스 선언과 접근 방식 차이
 * */