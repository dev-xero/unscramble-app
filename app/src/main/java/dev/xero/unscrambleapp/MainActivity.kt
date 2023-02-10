package dev.xero.unscrambleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dev.xero.unscrambleapp.ui.GameScreen
import dev.xero.unscrambleapp.ui.theme.UnscrambleAppTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			UnscrambleAppTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
				) {
					GameScreen()
				}
			}
		}
	}
}