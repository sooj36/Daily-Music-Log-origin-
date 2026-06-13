package com.sooj.today_music.testing

import com.sooj.today_music.domain.Album
import com.sooj.today_music.domain.Image
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import com.sooj.today_music.room.MemoEntity
import com.sooj.today_music.room.TrackEntity

class FakeSearchRepository : SearchRepository {
    var searchResult: List<Track> = emptyList()
    var albumsByTrackAndArtist: Map<Pair<String, String>, Album?> = emptyMap()
    val savedTracks = mutableListOf<TrackEntity>()
    val savedMemos = mutableListOf<MemoEntity>()
    val deletedTracks = mutableListOf<TrackEntity>()
    private var nextTrackId = 1

    override suspend fun getMusic_impl(track: String): List<Track> = searchResult

    override suspend fun getAlbumPoster_impl(track: String, artist: String): Album? {
        return albumsByTrackAndArtist[track to artist]
    }

    override suspend fun saveSelectedTrack_impl(
        trackEntity: TrackEntity,
        memoEntity: MemoEntity
    ) {
        val savedTrack = if (trackEntity.trackId == 0) {
            trackEntity.copy(trackId = nextTrackId++)
        } else {
            nextTrackId = maxOf(nextTrackId, trackEntity.trackId + 1)
            trackEntity
        }
        savedTracks.replaceByTrackId(savedTrack)
        savedMemos.replaceByTrackId(memoEntity.copy(trackId = savedTrack.trackId))
    }

    override suspend fun getAllTracks_impl(): List<TrackEntity> = savedTracks.toList()

    override suspend fun deleteTrack_impl(trackEntity: TrackEntity) {
        deletedTracks += trackEntity
        savedTracks.removeAll { it.trackId == trackEntity.trackId }
        savedMemos.removeAll { it.trackId == trackEntity.trackId }
    }

    override suspend fun getPostInfo_impl(track: String): List<Album> = emptyList()

    fun albumWithImage(url: String): Album {
        return Album(
            artist = "artist",
            title = "title",
            mbid = "",
            url = "",
            image = listOf(Image(url = url, size = "extralarge"))
        )
    }

    private fun MutableList<TrackEntity>.replaceByTrackId(trackEntity: TrackEntity) {
        val index = indexOfFirst { it.trackId == trackEntity.trackId }
        if (index >= 0) {
            this[index] = trackEntity
        } else {
            add(trackEntity)
        }
    }

    private fun MutableList<MemoEntity>.replaceByTrackId(memoEntity: MemoEntity) {
        val index = indexOfFirst { it.trackId == memoEntity.trackId }
        if (index >= 0) {
            this[index] = memoEntity
        } else {
            add(memoEntity)
        }
    }
}
