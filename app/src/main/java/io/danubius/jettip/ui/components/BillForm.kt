package io.danubius.jettip.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Toll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    onValChange: (Double) -> Unit = {}
) {
    val totalCost = remember {
        mutableStateOf("")
    }

    val validState = remember(totalCost.value) {
        totalCost.value.trim().isNotEmpty()
    }

    val sliderState = remember {
        mutableStateOf(0f)
    }


    val splitByState = remember {
        mutableStateOf(1)
    }

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    val totalCostPerPerson = remember {
        mutableStateOf(0.0)
    }

    val tipPercentage = (sliderState.value * 100).toInt()
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color(0xFF9CB4CC))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InputField(
                valueState = totalCost,
                labelId = "Total Cost",
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    keyboardController?.hide()
                }
            )
            if (true) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleIconButton(
                        modifier = modifier.size(64.dp),
                        imageVector = Icons.Default.Remove,
                        onClick = {
                            if (splitByState.value > 1) --splitByState.value
                        },
                        contentDescription = "Minus icon button",
                    )
                    Text(
                        text = "${splitByState.value}",
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        style = MaterialTheme.typography.h4,
                        color = Color(0xFF748DA6),
                        fontWeight = FontWeight.Bold
                    )
                    CircleIconButton(
                        modifier = modifier.size(64.dp),
                        imageVector = Icons.Default.Add,
                        onClick = { ++splitByState.value },
                        contentDescription = "Minus icon button",
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(48.dp),
                        imageVector = Icons.Outlined.Toll,
                        contentDescription = "Tip Icon",
                        tint = Color(0xFF9CB4CC)
                    )
                    Text(
                        text = "$ ${tipAmountState.value}",
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Light
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$tipPercentage %",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Light
                    )

                    Slider(
                        value = sliderState.value,
                        steps = 4,
                        onValueChange = {
                            sliderState.value = it
                            tipAmountState.value =
                                calculateTotalTip(
                                    totalCost = totalCost.value.toDouble(),
                                    tipPercentage = tipPercentage.toDouble()
                                )
                            totalCostPerPerson.value =
                                calculateTotalPerPerson(
                                    totalCost = totalCost.value.toDouble(),
                                    tipPercentage = tipPercentage.toDouble(),
                                    splitBy = splitByState.value
                                )
                            onValChange.invoke(totalCostPerPerson.value)
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF748DA6),
                            activeTrackColor = Color(0xFF9CB4CC),
                            inactiveTrackColor = Color(0xFFD3CEDF)
                        ),
                        valueRange = 0f..0.25f
                    )
                }
            } else {
                Box {

                }
            }
        }
    }
}

fun calculateTotalTip(totalCost: Double, tipPercentage: Double): Double {
    return (totalCost * tipPercentage) / 100
}

fun calculateTotalPerPerson(totalCost: Double, tipPercentage: Double, splitBy: Int): Double {
    val bill = calculateTotalTip(totalCost = totalCost, tipPercentage = tipPercentage) + totalCost
    return bill / splitBy
}

@Preview
@Composable
fun BillForm() {

}