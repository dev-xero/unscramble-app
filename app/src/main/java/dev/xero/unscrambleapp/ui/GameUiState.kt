package dev.xero.unscrambleapp.ui

data class GameUiState (
	var currentScrambledWord: String = "",
	var isGameOver: Boolean = false,
	var score: Int = 0,
	val isGuessedWordWrong: Boolean = false,
	val currentWordCount: Int = 1,
)