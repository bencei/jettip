package io.danubius.jettip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.danubius.jettip.ui.TopHeader
import io.danubius.jettip.ui.components.BillForm
import io.danubius.jettip.ui.theme.JetTipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTipApp()
        }
    }
}

@Composable
fun JetTipApp() {
    JetTipTheme {
        val totalCostPerPerson = remember {
            mutableStateOf(0.0)
        }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopHeader(totalCostPerPerson.value)
            BillForm { amount ->
                Log.d("AMT", "MainContent: ${amount.toInt()}")
                totalCostPerPerson.value = amount
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTipApp()
}