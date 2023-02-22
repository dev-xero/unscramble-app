package dev.xero.unscrambleapp.test

import SCORE_INCREASE
import dev.xero.unscrambleapp.ui.GameViewModel
import getUnscrambledWord
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import org.junit.Test

class GameViewModelTest {
	private val viewModel = GameViewModel()

	@Test
	fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
		var currentGameUiState = viewModel.uiState.value
		val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

		viewModel.updateUserGuess(correctPlayerWord)
		viewModel.checkUserGuess()

		currentGameUiState = viewModel.uiState.value
		// ASSERT THAT WE GUESSED CORRECTLY
		assertFalse(currentGameUiState.isGuessedWordWrong)

		// ASSERT THAT THE SCORE IS UPDATED CORRECTLY
		assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
	}

	companion object {
		private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
	}

}