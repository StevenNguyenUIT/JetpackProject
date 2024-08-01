package com.nhinhnguyenuit.jetpackproject.ui.viewmodel

import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val userRepository: UserRepository = mockk()
    private lateinit var userViewModel: UserViewModel

    @Before
    fun setUp() {
        userViewModel = UserViewModel(userRepository)
    }
//    test getUserDetail updates userDetail
    @Test
    fun getUserDetail() = runTest {
        val login = "test_user"
        val mockUser = User(1, login, "avatar_url", "html_url", "location", 100, 50)

        // Mock the UserRepository to return a user
        coEvery { userRepository.getUserDetail(login) } returns mockUser

        // Observe the userDetail StateFlow
        val observer = StateFlowTestObserver(userViewModel.userDetail)

        // Call the getUserDetail function
        userViewModel.getUserDetail(login)

        // Verify that the userDetail StateFlow is updated correctly
        assertEquals(mockUser, observer.awaitValue())

        // Verify that the UserRepository's getUserDetail function was called
        coVerify { userRepository.getUserDetail(login) }
    }
//    test loadLocalUsers on initialization
    @Test
    fun loadLocalUsers() = runTest {
        val mockLocalUsers = listOf(User(1, "login", "avatar_url", "html_url", "location", 100, 50))

        // Mock the UserRepository to return local users
        coEvery { userRepository.getLocalUsers() } returns mockLocalUsers

        // Create a new instance of UserViewModel to trigger initialization
        userViewModel = UserViewModel(userRepository)

        // Observe the localUsers StateFlow
        val observer = StateFlowTestObserver(userViewModel.localUsers)

        // Verify that the localUsers StateFlow is updated correctly
        assertEquals(mockLocalUsers, observer.awaitValue())

        // Verify that the UserRepository's getLocalUsers function was called
        coVerify { userRepository.getLocalUsers() }
    }
}
