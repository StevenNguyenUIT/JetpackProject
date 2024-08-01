package com.nhinhnguyenuit.jetpackproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.nhinhnguyenuit.jetpackproject.data.local.UserDao
import com.nhinhnguyenuit.jetpackproject.data.local.UserEntity
import com.nhinhnguyenuit.jetpackproject.data.local.toUser
import com.nhinhnguyenuit.jetpackproject.data.local.toUserEntity
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.network.ApiService
import com.nhinhnguyenuit.jetpackproject.data.paging.PAGE_SIZE
import com.nhinhnguyenuit.jetpackproject.data.paging.UserPagingSource
import com.nhinhnguyenuit.jetpackproject.ui.viewmodel.MainCoroutineRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val apiService: ApiService = mockk()
    private val userDao: UserDao = mockk()
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepository(apiService, userDao)
    }

//    test getUsersStream returns flow of PagingData
    @Test
    fun getUsersStream() = runTest {
        val mockUserEntity = UserEntity(1, "login", "avatar_url", "html_url", "location", 100, 50)
        val mockUser = mockUserEntity.toUser()

        // Mock the UserDao to return the PagingSource
        val pagingSource = UserPagingSource(apiService, userDao)

        // Mock the ApiService to return a list of users for paging
        coEvery { apiService.getUsers(any(), any()) } returns listOf(mockUser)

        // Mock the UserDao to save the users to the local database
        coEvery { userDao.insertAll(any()) } just Runs

        // Create a Pager to test
        val pager = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { pagingSource }
        ).flow

        // Still not complete
    }

//    test getUserDetail returns user from API
    @Test
    fun getUserDetail() = runBlocking {
        val mockUser = User(1, "login", "avatar_url", "html_url", "location", 100, 50)
        coEvery { apiService.getUserDetail("login") } returns mockUser

        val user = userRepository.getUserDetail("login")

        assertEquals(mockUser, user)
        coVerify { apiService.getUserDetail("login") }
    }

//    test getLocalUsers returns users from local database
    @Test
    fun getLocalUsers() = runBlocking {
        val mockUserEntity = UserEntity(1, "login", "avatar_url", "html_url", "location", 100, 50)
        coEvery { userDao.getAllUsers() } returns listOf(mockUserEntity)

        val users = userRepository.getLocalUsers()

        assertEquals(listOf(mockUserEntity.toUser()), users)
        coVerify { userDao.getAllUsers() }
    }

//    test saveUsers inserts users into local database
    @Test
    fun saveUsers() = runBlocking {
        val mockUser = User(1, "login", "avatar_url", "html_url", "location", 100, 50)
        coEvery { userDao.insertAll(any()) } just Runs

        userRepository.saveUsers(listOf(mockUser))

        coVerify { userDao.insertAll(listOf(mockUser.toUserEntity())) }
    }

//    test clearLocalUsers deletes all users from local database
    @Test
    fun `test clearLocalUsers deletes all users from local database`() = runBlocking {
        coEvery { userDao.deleteAllUsers() } just Runs

        userRepository.clearLocalUsers()

        coVerify { userDao.deleteAllUsers() }
    }
}
