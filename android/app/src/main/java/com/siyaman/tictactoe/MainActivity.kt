package com.siyaman.tictactoe

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.siyaman.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TicTacToeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          UIGame()
        }
      }
    }
  }
}

@DarkLightPreviews
@Composable
private fun DefaultPreview() {
  TicTacToeTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
    ) {
      UIGame()
    }
  }
}

@Preview(
  name = "Dark mode",
  group = "UI mode",
  uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
  device = Devices.NEXUS_5,
  showSystemUi = true
)
@Preview(
  name = "Light mode",
  group = "UI mode",
  uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
  device = Devices.AUTOMOTIVE_1024p,
  showSystemUi = true
)
annotation class DarkLightPreviews