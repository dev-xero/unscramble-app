package dev.xero.unscrambleapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.xero.unscrambleapp.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel: ViewModel() {
	private val _uiState = MutableStateFlow(GameUiState())
	val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
	var userGuess by mutableStateOf("")
		private set


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

	fun updateUserGuess(guessedWord: String) {
		userGuess = guessedWord
	}

	fun checkUserGuess() {
		if (userGuess.equals(currentWord, ignoreCase = true)) {

		} else {
			_uiState.update {
				currentState -> currentState.copy(isGuessedWordWrong = true)
			}
		}
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