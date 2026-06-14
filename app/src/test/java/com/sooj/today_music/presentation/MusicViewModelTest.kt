package com.sooj.today_music.presentation

import com.sooj.today_music.domain.Image
import com.sooj.today_music.domain.Track
import com.sooj.today_music.room.TrackEntity
import com.sooj.today_music.testing.FakeMemoRepository
import com.sooj.today_music.testing.FakeSearchRepository
import com.sooj.today_music.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MusicViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val searchRepository = FakeSearchRepository()
    private val memoRepository = FakeMemoRepository()

    private fun viewModel(): MusicViewModel {
        return MusicViewModel(
            repository = searchRepository,
            memoRepository = memoRepository,
            ioDispatcher = mainDispatcherRule.dispatcher
        )
    }

    @Test
    fun getMusic_updatesSearchList() = runTest {
        val tracks = listOf(track(name = "Hype Boy", artist = "NewJeans"))
        searchRepository.searchResult = tracks

        val viewModel = viewModel()
        viewModel.getMusic_vm("hype")
        advanceUntilIdle()

        assertEquals(tracks, viewModel.searchList_st.value)
    }

    @Test
    fun fetchTrackAndUrl_mapsTrackNameToExtralargeAlbumImageUrl() = runTest {
        val track = track(name = "Ditto", artist = "NewJeans")
        searchRepository.searchResult = listOf(track)
        searchRepository.albumsByTrackAndArtist = mapOf(
            ("Ditto" to "NewJeans") to albumWithImages(
                "https://example.test/medium.jpg" to "medium",
                "https://example.test/extralarge.jpg" to "extralarge"
            )
        )

        val viewModel = viewModel()
        viewModel.fetchTrackAndUrl_vm("ditto")
        advanceUntilIdle()

        assertEquals(
            "https://example.test/extralarge.jpg",
            viewModel.getAlbumMap_st.value["Ditto"]
        )
    }

    @Test
    fun saveSelectedTrack_savesSelectedTrackAndEmptyMemo_thenSetsSaveResultTrue() = runTest {
        val selectedTrack = track(name = "Attention", artist = "NewJeans")
        val viewModel = viewModel()
        viewModel.selectTrack_vm(selectedTrack)
        viewModel.setAlbumUrlForTest("Attention", "https://example.test/attention.jpg")

        viewModel.saveSelectedTrack_vm()
        advanceUntilIdle()

        assertEquals(1, searchRepository.savedTracks.size)
        assertEquals("Attention", searchRepository.savedTracks.single().trackName)
        assertEquals("NewJeans", searchRepository.savedTracks.single().artistName)
        assertEquals("https://example.test/attention.jpg", searchRepository.savedTracks.single().imageUrl)
        assertEquals("", searchRepository.savedMemos.single().memoContent)
        assertEquals(searchRepository.savedTracks.single().trackId, searchRepository.savedMemos.single().trackId)
        assertEquals(true, viewModel.saveResult_st.value)
    }

    @Test
    fun getAllTracks_updatesSavedTrackState() = runTest {
        val savedTrack = savedTrack(trackId = 7, trackName = "Cookie")
        searchRepository.savedTracks += savedTrack

        val viewModel = viewModel()
        viewModel.getAllTracks_vm()
        advanceUntilIdle()

        assertEquals(listOf(savedTrack), viewModel.getAllSavedTracks_st.value)
    }

    @Test
    fun deleteSavedTrack_deletesTrackAndRefreshesSavedTrackState() = runTest {
        val deletedTrack = savedTrack(trackId = 1, trackName = "OMG")
        val remainingTrack = savedTrack(trackId = 2, trackName = "Super Shy")
        searchRepository.savedTracks += listOf(deletedTrack, remainingTrack)
        val viewModel = viewModel()
        viewModel.getAllTracks_vm()
        advanceUntilIdle()

        viewModel.deleteSavedTrack(deletedTrack)
        advanceUntilIdle()

        assertEquals(listOf(deletedTrack), searchRepository.deletedTracks)
        assertEquals(listOf(remainingTrack), viewModel.getAllSavedTracks_st.value)
    }

    private fun track(name: String, artist: String): Track {
        return Track(name = name, artist = artist, image = emptyList())
    }

    private fun savedTrack(trackId: Int, trackName: String): TrackEntity {
        return TrackEntity(
            trackId = trackId,
            trackName = trackName,
            artistName = "NewJeans",
            imageUrl = "https://example.test/$trackName.jpg",
            saveAt = 1_700_000_000_000
        )
    }

    private fun albumWithImages(vararg images: Pair<String, String>) = searchRepository.albumWithImage("").copy(
        image = images.map { (url, size) -> Image(url = url, size = size) }
    )
}
