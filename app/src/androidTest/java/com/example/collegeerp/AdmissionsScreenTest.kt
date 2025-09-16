package com.example.collegeerp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.collegeerp.ui.screens.AdmissionsScreen
import org.junit.Rule
import org.junit.Test

class AdmissionsScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun submitEnabledWhenFieldsFilled() {
        composeRule.setContent {
            AdmissionsScreen { _, _ -> }
        }
        composeRule.onNodeWithText("Full name").performTextInput("John Doe")
        composeRule.onNodeWithText("Program").performTextInput("BSc")
        composeRule.onNodeWithText("Submit").performClick()
    }
}


