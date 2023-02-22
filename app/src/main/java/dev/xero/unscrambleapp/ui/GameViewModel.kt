package dev.xero.unscrambleapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.xero.unscrambleapp.data.MAX_NO_OF_WORDS
import dev.xero.unscrambleapp.data.SCORE_INCREASE
import dev.xero.unscrambleapp.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel: ViewModel() {
	private val _uiState = MutableStateFlow(GameUiState())
	val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
	var userGuess by mutableStateOf("")

	private lateinit var currentWord: String
	private var usedWords: MutableSet<String> = mutableSetOf()

	private fun shuffleContentWord(word: String): String {
		val tempWord = word.toCharArray()
		tempWord.shuffle()
		while (String(tempWord) == word) {
			tempWord.shuffle()
		}
		return String(tempWord)
	}

	private fun pickRandomWordAndShuffle(): String {
		currentWord = allWords.random()
		return if(usedWords.contains(currentWord)) {
			pickRandomWordAndShuffle()
		} else {
			usedWords.add(currentWord)
			shuffleContentWord(currentWord)
		}
	}

	private fun updateGameState(updatedScore: Int) {
		if (usedWords.size == MAX_NO_OF_WORDS) {
			_uiState.update {
				currentState -> currentState.copy(
					isGuessedWordWrong = false,
					score = updatedScore,
					isGameOver = true
				)
			}
		} else {
			_uiState.update {
					currentState -> currentState.copy(
					isGuessedWordWrong = false,
					currentScrambledWord = pickRandomWordAndShuffle(),
					score = updatedScore,
					currentWordCount = currentState.currentWordCount.inc()
				)
			}
		}
	}

	fun updateUserGuess(guessedWord: String) {
		userGuess = guessedWord
	}

	fun checkUserGuess() {
		if (userGuess.equals(currentWord, ignoreCase = true)) {
			val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
			updateGameState(updatedScore)
		} else {
			_uiState.update {
				currentState -> currentState.copy(isGuessedWordWrong = true)
			}
			skipWord()
		}
		updateUserGuess("")
	}

	fun skipWord() {
		updateGameState(_uiState.value.score)
		updateUserGuess("")
	}


	fun resetGame() {
		usedWords.clear()
		_uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
	}

	init {
		resetGame()
	}
}