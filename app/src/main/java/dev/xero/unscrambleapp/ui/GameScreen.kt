package dev.xero.unscrambleapp.ui

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.xero.unscrambleapp.ui.theme.UnscrambleAppTheme
import dev.xero.unscrambleapp.R


@Composable
fun GameScreen(
	modifier: Modifier = Modifier,
	gameViewModel: GameViewModel = viewModel()
) {
	Column(
		modifier = modifier
			.verticalScroll(rememberScrollState())
			.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		val gameUiState by gameViewModel.uiState.collectAsState()

		GameStatus()
		GameLayout(
			currentScrambledWord = gameUiState.currentScrambledWord
		)
		Row(
			modifier = modifier
				.fillMaxWidth()
				.padding(top = 16.dp),
			horizontalArrangement = Arrangement.SpaceAround
		) {
			OutlinedButton(
				onClick = { },
				modifier = Modifier
					.weight(1f)
					.padding(end = 8.dp)
			) {
				Text(stringResource(R.string.skip))
			}

			Button(
				modifier = modifier
					.fillMaxWidth()
					.weight(1f)
					.padding(start = 8.dp),
				onClick = { }
			) {
				Text(stringResource(R.string.submit))
			}
		}
	}
}

@Composable
fun GameStatus(modifier: Modifier = Modifier) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp)
			.size(48.dp),
	) {
		Text(
			text = stringResource(R.string.word_count, 1),
			fontSize = 18.sp,
		)
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentWidth(Alignment.End),
			text = stringResource(R.string.score, 0),
			fontSize = 18.sp,
		)
	}
}

@Composable
fun GameLayout(
	currentScrambledWord: String,
	modifier: Modifier = Modifier
) {
	Column(
		verticalArrangement = Arrangement.spacedBy(24.dp),

		) {
		Text(
			text = currentScrambledWord,
			fontSize = 45.sp,
			modifier = modifier.align(Alignment.CenterHorizontally)
		)
		Text(
			text = stringResource(R.string.instructions),
			fontSize = 17.sp,
			modifier = Modifier.align(Alignment.CenterHorizontally)
		)
		OutlinedTextField(
			value = "",
			singleLine = true,
			modifier = Modifier.fillMaxWidth(),
			onValueChange = { },
			label = { Text(stringResource(R.string.enter_your_word)) },
			isError = false,
			keyboardOptions = KeyboardOptions.Default.copy(
				imeAction = ImeAction.Done
			),
			keyboardActions = KeyboardActions(
				onDone = { }
			),
		)
	}
}

/*
 * Creates and shows an AlertDialog with final score.
 */
@Composable
private fun FinalScoreDialog(
	onPlayAgain: () -> Unit,
	modifier: Modifier = Modifier
) {
	val activity = (LocalContext.current as Activity)

	AlertDialog(
		onDismissRequest = {},
		title = { Text(stringResource(R.string.congratulations)) },
		text = { Text(stringResource(R.string.you_scored, 0)) },
		modifier = modifier,
		dismissButton = {
			TextButton(
				onClick = {
					activity.finish()
				}
			) {
				Text(text = stringResource(R.string.exit))
			}
		},
		confirmButton = {
			TextButton(
				onClick = onPlayAgain
			) {
				Text(text = stringResource(R.string.play_again))
			}
		}
	)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	UnscrambleAppTheme {
		GameScreen()
	}
}