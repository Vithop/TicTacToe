package com.siyaman.tictactoe

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.siyaman.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun UITile(
  modifier: Modifier = Modifier,
  piece: Player? = null,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  TextButton(
    modifier = modifier.fillMaxSize(), enabled = enabled && piece == null, onClick = onClick
  ) {
    Text(
      piece?.toString() ?: "",
      style = MaterialTheme.typography.headlineLarge,
      color = MaterialTheme.colorScheme.primary
    )
  }
}


@DarkLightPreviews
@Composable
private fun DefaultPreview() {
  TicTacToeTheme {
    Surface {
      Row {
        UITile(modifier = Modifier.weight(1f), piece = Player.O, enabled = false) {}
        UITile(modifier = Modifier.weight(1f), piece = null, enabled = true) {}
        UITile(modifier = Modifier.weight(1f), piece = Player.X, enabled = true) {}
      }
    }
  }
}