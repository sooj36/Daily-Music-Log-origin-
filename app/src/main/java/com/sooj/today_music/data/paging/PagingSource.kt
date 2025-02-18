package com.sooj.today_music.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.api.Page
import com.sooj.today_music.data.ApiService_EndPoint
import com.sooj.today_music.domain.MusicGetInfoModel_dc

class PagingSource(
    private val apiService : ApiService_EndPoint,
    private val query : String
) : PagingSource<Int, MusicGetInfoModel_dc>() {
    override fun getRefreshKey(state: PagingState<Int, MusicGetInfoModel_dc>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MusicGetInfoModel_dc> {
        TODO("Not yet implemented")
    }
}
//
//{
//    override fun getRefreshKey(state: PagingState<Int, MusicGetInfoModel_dc>): Int? {
//        return state.anchorPosition
//    }

    // load 함수 : 실제 데이터 로드하는 핵심 메서드
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MusicGetInfoModel_dc> {
//        return try {
//            val page = params.key ?: 1
//            val response = apiService.getTrackSearch(track = query, limit =  10) // API 호출
//            val tracks = response.body()?.results?.trackmatches?.track ?: emptyList()
//
//            // Track을 MusicGetInfoModel_dc로 변환
//            val data = tracks.map { track ->
//                MusicGetInfoModel_dc(
//                    id = track.name,
//                    name = track.name,
//                    artist = track.artist,
//                    image = track.image?.firstOrNull()?.url //첫번째 이미지 가져오기
//                )
//            }
//        } catch (e:Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}