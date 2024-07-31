package com.nhinhnguyenuit.jetpackproject.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class UserViewModelTest {
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private val testDispatcher = TestCoroutineDispatcher()
//    private val testScope = TestCoroutineScope(testDispatcher)
//
//    private lateinit var userRepository: UserRepository
//    private lateinit var userViewModel: UserViewModel
//
//    @Before
//    fun setup() {
//        MockKAnnotations.init(this, relaxed = true)
//        userRepository = mockk()
//        userViewModel = UserViewModel(userRepository)
//    }
//
//    @After
//    fun teardown() {
//        clearAllMocks()
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `test getUserDetail`() = testScope.runBlockingTest {
//        val mockUser = User(1, "login", "avatar_url", "html_url", "location", 100, 50)
//        coEvery { userRepository.getUserDetail("login") } returns mockUser
//
//        mainViewModel.getUserDetail("login")
//        advanceUntilIdle()
//
//        assertEquals(mainViewModel.userDetail.value, mockUser)
//    }
//
//    @Test
//    fun `test getLocalUsers on init`() = testScope.runBlockingTest {
//        val mockUser = User(1, "login", "avatar_url", "html_url", "location", 100, 50)
//        coEvery { userRepository.getLocalUsers() } returns listOf(mockUser)
//
//        mainViewModel = MainViewModel(userRepository)
//        advanceUntilIdle()
//
//        assertEquals(mainViewModel.localUsers.value, listOf(mockUser))
//    }
}