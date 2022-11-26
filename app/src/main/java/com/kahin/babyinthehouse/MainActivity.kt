package com.kahin.babyinthehouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.kahin.babyinthehouse.sign.SignActivity
import com.kahin.babyinthehouse.ui.theme.HomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(this, "Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(context: Context?, name: String) {
    Text(text = "Hello $name!")
    Button(
        onClick = {
        context?.let {
            startActivity(it, Intent(context, SignActivity::class.java), null)
        }

    }) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeTheme {
        Greeting(null,"Android")
    }
}