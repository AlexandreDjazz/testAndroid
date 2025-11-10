package com.bankingapp.ui.screens.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onBack: () -> Unit
) {
    var pushNotifications by remember { mutableStateOf(true) }
    var emailNotifications by remember { mutableStateOf(true) }
    var transactionAlerts by remember { mutableStateOf(true) }
    var paymentReminders by remember { mutableStateOf(false) }
    var securityAlerts by remember { mutableStateOf(true) }
    var promotions by remember { mutableStateOf(false) }
    var budgetAlerts by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // General Section
            Text(
                text = "General",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Card {
                Column {
                    NotificationSettingItem(
                        icon = Icons.Default.Notifications,
                        title = "Push Notifications",
                        subtitle = "Receive push notifications on your device",
                        checked = pushNotifications,
                        onCheckedChange = { pushNotifications = it }
                    )

                    Divider()

                    NotificationSettingItem(
                        icon = Icons.Default.Email,
                        title = "Email Notifications",
                        subtitle = "Receive notifications via email",
                        checked = emailNotifications,
                        onCheckedChange = { emailNotifications = it }
                    )
                }
            }

            // Transaction Notifications
            Text(
                text = "Transactions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Card {
                Column {
                    NotificationSettingItem(
                        icon = Icons.Default.SwapHoriz,
                        title = "Transaction Alerts",
                        subtitle = "Get notified for every transaction",
                        checked = transactionAlerts,
                        onCheckedChange = { transactionAlerts = it }
                    )

                    Divider()

                    NotificationSettingItem(
                        icon = Icons.Default.Payment,
                        title = "Payment Reminders",
                        subtitle = "Reminders for upcoming payments",
                        checked = paymentReminders,
                        onCheckedChange = { paymentReminders = it }
                    )

                    Divider()

                    NotificationSettingItem(
                        icon = Icons.Default.TrendingUp,
                        title = "Budget Alerts",
                        subtitle = "Alerts when approaching budget limits",
                        checked = budgetAlerts,
                        onCheckedChange = { budgetAlerts = it }
                    )
                }
            }

            // Security & Account
            Text(
                text = "Security & Account",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Card {
                Column {
                    NotificationSettingItem(
                        icon = Icons.Default.Security,
                        title = "Security Alerts",
                        subtitle = "Important security notifications",
                        checked = securityAlerts,
                        onCheckedChange = { securityAlerts = it },
                        enabled = false // Always enabled
                    )

                    Divider()

                    NotificationSettingItem(
                        icon = Icons.Default.LocalOffer,
                        title = "Promotions & Offers",
                        subtitle = "Special offers and promotional content",
                        checked = promotions,
                        onCheckedChange = { promotions = it }
                    )
                }
            }

            // Info Card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        text = "Security alerts cannot be disabled to keep your account safe.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun NotificationSettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}
