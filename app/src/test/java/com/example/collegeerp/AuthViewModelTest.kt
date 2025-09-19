package com.example.collegeerp

import com.example.collegeerp.domain.repository.AuthRepository
import com.example.collegeerp.ui.AuthViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: AuthViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        viewModel = AuthViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `signIn calls repository signIn`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        coEvery { authRepository.signIn(email, password) } returns Unit

        // When
        viewModel.signIn(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { authRepository.signIn(email, password) }
    }

    @Test
    fun `signUp calls repository signUp`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val name = "Test User"
        coEvery { authRepository.signUp(email, password, name) } returns "uid123"

        // When
        var callbackResult = false
        viewModel.signUp(email, password, name) { success ->
            callbackResult = success
        }
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { authRepository.signUp(email, password, name) }
        assert(callbackResult)
    }
}