package com.sooj.today_music.domain

import com.sooj.today_music.BuildConfig
import com.sooj.today_music.data.RetrofitInstance_build
import retrofit2.Response

interface SearchRepository {
    suspend fun getTrackInfo(track: String): List<Track>
//        return RetrofitInstance_build.musicApi.getMusicSearch("track.search","", BuildConfig.LAST_FM_API_KEY, "json")

}
/** Retrofit을 통해 받은 HTTP 응답 전체를 처리
 * 에러처리나 상태코드를 직접 다를 수 있기에, 유연 & 복장성 증가 */

interface SearchRepository2 {
    suspend fun getTrackInfo(track: String) : List<Track>
}

/** 응답 데이터 중 Track 리스트만 반환
 * 응답이 성공적인지 확인하는 과정이나 에러처리 필요 시, 별도의 처리 로직 추가 되어야*/