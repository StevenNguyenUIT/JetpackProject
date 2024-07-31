package com.nhinhnguyenuit.jetpackproject.data.repository

import com.nhinhnguyenuit.jetpackproject.data.local.UserDao
import com.nhinhnguyenuit.jetpackproject.data.local.UserEntity
import com.nhinhnguyenuit.jetpackproject.data.local.toUser
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.network.ApiService
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class UserRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        apiService = mockk()
        userDao = mockk()
        userRepository = UserRepository(apiService, userDao)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `test getUserStream from API`() = runBlocking {
        val mockUser = User(1, "login", "avatar_url", "html_url", "location", 100, 50)
        coEvery { apiService.getUsers(20, 0) } returns listOf(mockUser)

        val users = userRepository.getUsersStream().toList()

        assertEquals(users[0], listOf(mockUser))
        coVerify { userDao.insertAll(any<List<UserEntity>>()) }
    }

    @Test
    fun `test getUserDetail from API`() = runBlocking {
        val mockUser = User(1, "login", "avatar_url", "html_url", "location", 100, 50)
        coEvery { apiService.getUserDetail("login") } returns mockUser

        val user = userRepository.getUserDetail("login")

        assertEquals(user, mockUser)
    }

    @Test
    fun `test getLocalUsers`() = runBlocking {
        val mockUserEntity = UserEntity(1, "login", "avatar_url", "html_url", "location", 100, 50)
        coEvery { userDao.getAllUsers() } returns listOf(mockUserEntity)

        val users = userRepository.getLocalUsers()

        assertEquals(users, listOf(mockUserEntity.toUser()))
    }

    @Test
    fun `test saveUsers`() = runBlocking {
        val mockUser = User(1, "login", "avatar_url", "html_url", "location", 100, 50)

        userRepository.saveUsers(listOf(mockUser))

        coVerify { userDao.insertAll(any<List<UserEntity>>()) }
    }

    @Test
    fun `test clearLocalUsers`() = runBlocking {
        userRepository.clearLocalUsers()

        coVerify { userDao.deleteAllUsers() }
    }
}