package com.example.collegeerp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.collegeerp.ui.screens.AuthScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun authScreen_displaysSignInByDefault() {
        composeTestRule.setContent {
            AuthScreen(onSignIn = { _, _ -> })
        }

        composeTestRule.onNodeWithText("Sign In").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun authScreen_canSwitchToSignUp() {
        composeTestRule.setContent {
            AuthScreen(onSignIn = { _, _ -> })
        }

        composeTestRule.onNodeWithText("Don't have an account? Sign Up").performClick()
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
        composeTestRule.onNodeWithText("Full Name").assertIsDisplayed()
    }

    @Test
    fun authScreen_signInButtonDisabledWhenFieldsEmpty() {
        composeTestRule.setContent {
            AuthScreen(onSignIn = { _, _ -> })
        }

        composeTestRule.onNodeWithText("Sign In").assertIsNotEnabled()
    }

    @Test
    fun authScreen_signInButtonEnabledWhenFieldsFilled() {
        composeTestRule.setContent {
            AuthScreen(onSignIn = { _, _ -> })
        }

        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        
        composeTestRule.onNodeWithText("Sign In").assertIsEnabled()
    }
}