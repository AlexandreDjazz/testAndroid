package com.bankingapp.ui.screens.security

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.bankingapp.ui.screens.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(
    onBack: () -> Unit,
    onChangePIN: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.collectAsState(initial = null)
    var showBiometricDialog by remember { mutableStateOf(false) }
    var biometricEnabled by remember { mutableStateOf(false) }
    var twoFactorEnabled by remember { mutableStateOf(false) }

    if (showBiometricDialog) {
        AlertDialog(
            onDismissRequest = { showBiometricDialog = false },
            title = { Text("Biometric Authentication") },
            text = { Text("Biometric authentication will be available in a future update.") },
            confirmButton = {
                TextButton(onClick = { showBiometricDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security") },
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
            // Authentication Section
            Text(
                text = "Authentication",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Card {
                Column {
                    SecuritySettingItem(
                        icon = Icons.Default.Pin,
                        title = "Change PIN",
                        subtitle = "Update your 4-digit security PIN",
                        onClick = onChangePIN
                    )

                    Divider()

                    SecuritySettingItem(
                        icon = Icons.Default.Fingerprint,
                        title = "Biometric Login",
                        subtitle = if (biometricEnabled) "Enabled" else "Disabled",
                        trailing = {
                            Switch(
                                checked = biometricEnabled,
                                onCheckedChange = {
                                    showBiometricDialog = true
                                }
                            )
                        }
                    )

                    Divider()

                    SecuritySettingItem(
                        icon = Icons.Default.Security,
                        title = "Two-Factor Authentication",
                        subtitle = if (twoFactorEnabled) "Enabled" else "Disabled",
                        trailing = {
                            Switch(
                                checked = twoFactorEnabled,
                                onCheckedChange = { twoFactorEnabled = it }
                            )
                        }
                    )
                }
            }

            // Account Security Section
            Text(
                text = "Account Security",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Card {
                Column {
                    SecuritySettingItem(
                        icon = Icons.Default.Lock,
                        title = "Password",
                        subtitle = "Last changed 30 days ago",
                        onClick = { /* TODO */ }
                    )

                    Divider()

                    SecuritySettingItem(
                        icon = Icons.Default.Devices,
                        title = "Trusted Devices",
                        subtitle = "Manage devices that can access your account",
                        onClick = { /* TODO */ }
                    )

                    Divider()

                    SecuritySettingItem(
                        icon = Icons.Default.History,
                        title = "Login History",
                        subtitle = "View recent login activity",
                        onClick = { /* TODO */ }
                    )
                }
            }

            // Privacy Section
            Text(
                text = "Privacy",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Card {
                Column {
                    SecuritySettingItem(
                        icon = Icons.Default.Visibility,
                        title = "Show Balance",
                        subtitle = "Display balance on home screen",
                        trailing = {
                            Switch(
                                checked = true,
                                onCheckedChange = { /* TODO */ }
                            )
                        }
                    )

                    Divider()

                    SecuritySettingItem(
                        icon = Icons.Default.Block,
                        title = "Blocked Contacts",
                        subtitle = "Manage blocked users",
                        onClick = { /* TODO */ }
                    )
                }
            }

            // Account Actions
            Text(
                text = "Account Actions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Card {
                SecuritySettingItem(
                    icon = Icons.Default.DeleteForever,
                    title = "Delete Account",
                    subtitle = "Permanently delete your account and data",
                    iconTint = MaterialTheme.colorScheme.error,
                    onClick = { /* TODO */ }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SecuritySettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconTint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        if (trailing != null) {
            trailing()
        } else if (onClick != null) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        }
    }
}
