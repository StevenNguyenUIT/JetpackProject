package com.nhinhnguyenuit.jetpackproject.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor


class UserViewModelTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val repository: UserRepository = mockk()
    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        viewModel = UserViewModel(repository)
    }

    @Test
    fun `getUserDetail should fetch user detail from repository`() = runTest {
        val user = User(1, "login", "avatar_url", "html_url", "location", 10, 20)
        coEvery { repository.getUserDetail("login") } returns user

        viewModel.getUserDetail("login")

        val result = viewModel.userDetail.first()
        assertEquals(user, result)
        coVerify { repository.getUserDetail("login") }
    }

    @Test
    fun `localUsers should be populated on init`() = runTest {
        val users = listOf(User(1, "login", "avatar_url", "html_url", "location", 10, 20))
        coEvery { repository.getLocalUsers() } returns users

        viewModel = UserViewModel(repository)
        val result = viewModel.localUsers.first()
        assertEquals(users, result)
        coVerify { repository.getLocalUsers() }
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}