package com.mument_android.app.presentation.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.startup.detail.viewmodels.MumentDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MumentDetailViewModelTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    lateinit var mumentDetailViewModel: MumentDetailViewModel

    /***
     * Rule 이라는 단어 그대로 규칙을 설정해준다.
     * InstantTaskExecutorRule은 백그라운드 작업과 연관된 모든 아키텍쳐 컴포넌트들의 테스트를 한개의 스레드에서 실행한다.
     * 작업들의 동기화에 신경을 쓰지 않아도 되고, LiveData를 테스트할 때는 필수로 사용된다고 보면 된다.
     ***/

    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        //mumentDetailViewModel = MumentDetailViewModel()
    }

    @Test
    fun `감정태그들이_EnumClass에서_정상적으로_반환되는지_테스트`() {
        /*mumentDetailViewModel.setRandomTags()  //
        assertTrue(
            EmotionalTag.values().map { it.tag }.any { tag ->
                mumentDetailViewModel.emotionalTag.value == tag
        })*/
    }
}