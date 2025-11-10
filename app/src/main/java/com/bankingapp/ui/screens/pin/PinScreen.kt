package com.bankingapp.ui.screens.pin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PinScreen(
    onPinVerified: () -> Unit,
    viewModel: PinViewModel = hiltViewModel()
) {
    val pinState by viewModel.pinState.collectAsState()
    val currentPin by viewModel.currentPin.collectAsState()

    LaunchedEffect(pinState) {
        if (pinState is PinState.Success) {
            kotlinx.coroutines.delay(300)
            onPinVerified()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = when (pinState) {
                        is PinState.Creating -> "Create Your PIN"
                        is PinState.Confirming -> "Confirm Your PIN"
                        is PinState.Verifying -> "Enter Your PIN"
                        is PinState.Success -> "Success!"
                        is PinState.Error -> "Error"
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (pinState is PinState.Error) {
                    Text(
                        text = (pinState as PinState.Error).message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    Text(
                        text = when (pinState) {
                            is PinState.Creating -> "Set a 4-digit PIN to secure your account"
                            is PinState.Confirming -> "Please enter your PIN again"
                            is PinState.Verifying -> "Enter your PIN to continue"
                            else -> ""
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }

            // PIN Dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .then(
                                if (index < currentPin.length) {
                                    Modifier.background(MaterialTheme.colorScheme.primary)
                                } else {
                                    Modifier.border(
                                        2.dp,
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                        CircleShape
                                    )
                                }
                            )
                    )
                }
            }

            // Number Pad
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (row in 0..2) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        for (col in 1..3) {
                            val number = row * 3 + col
                            NumberButton(
                                number = number,
                                onClick = { viewModel.addDigit(number) }
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.size(80.dp))

                    NumberButton(
                        number = 0,
                        onClick = { viewModel.addDigit(0) }
                    )

                    IconButton(
                        onClick = { viewModel.removeDigit() },
                        modifier = Modifier.size(80.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Backspace,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NumberButton(
    number: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
