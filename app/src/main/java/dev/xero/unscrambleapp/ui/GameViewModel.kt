package dev.xero.unscrambleapp.ui

import androidx.lifecycle.ViewModel
import dev.xero.unscrambleapp.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class GameViewModel: ViewModel() {
	private val _uiState = MutableStateFlow(GameUiState())
	val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

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

	fun resetGame() {
		usedWords.clear()
		_uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
	}

	init {
		resetGame()
	}
}