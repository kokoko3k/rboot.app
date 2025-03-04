package it.kokoko3k.rboot

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.kokoko3k.rboot.ui.theme.rbootTheme

class MainActivity : ComponentActivity() {

    private val PREFS_NAME = "RbootPrefs"
    private val ENABLED_KEY = "RbootEnabled"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            rbootTheme {
                val context = LocalContext.current
                val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                var isEnabled by remember {
                    mutableStateOf(sharedPrefs.getBoolean(ENABLED_KEY, false))
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding), isEnabled, context) { newValue ->
                        isEnabled = newValue
                        with(sharedPrefs.edit()) {
                            putBoolean(ENABLED_KEY, newValue)
                            apply()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, isEnabled: Boolean, context: Context, onCheckedChange: (Boolean) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                RbootUtils.StartRboot(context)
                Toast.makeText(context, "Starting Rboot script...", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Rboot script")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Enable RBoot script at boot?")
            Spacer(Modifier.width(8.dp))
            Switch(
                checked = isEnabled,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    rbootTheme {
        MainScreen(isEnabled = false, context = context) { _ -> }
    }
}