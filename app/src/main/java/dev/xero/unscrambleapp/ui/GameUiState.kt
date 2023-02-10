package dev.xero.unscrambleapp.ui

data class GameUiState (
	var currentScrambledWord: String = "",
	val isGuessedWordWrong: Boolean = false
)