package com.sooj.today_music.presentation

import com.sooj.today_music.room.MemoEntity
import com.sooj.today_music.testing.FakeMemoRepository
import com.sooj.today_music.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MemoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val memoRepository = FakeMemoRepository()

    private fun viewModel(): MemoViewModel {
        return MemoViewModel(
            memoRepository = memoRepository,
            ioDispatcher = mainDispatcherRule.dispatcher
        )
    }

    @Test
    fun insertMemo_delegatesToRepository() = runTest {
        val memo = memo(trackId = 3, memoContent = "first note")
        val viewModel = viewModel()

        viewModel.insertMemo_vm(memo)
        advanceUntilIdle()

        assertEquals(listOf(memo), memoRepository.inserted)
    }

    @Test
    fun updateMemo_delegatesToRepository() = runTest {
        val memo = memo(trackId = 5, memoContent = "updated note")
        val viewModel = viewModel()

        viewModel.updateMemo_vm(memo)
        advanceUntilIdle()

        assertEquals(listOf(memo), memoRepository.updated)
    }

    @Test
    fun deleteMemo_delegatesToRepository() = runTest {
        val viewModel = viewModel()

        viewModel.deleteMemo_vm(trackId = 8)
        advanceUntilIdle()

        assertEquals(listOf(8), memoRepository.deletedIds)
    }

    private fun memo(trackId: Int, memoContent: String): MemoEntity {
        return MemoEntity(
            trackId = trackId,
            memoContent = memoContent
        )
    }
}
