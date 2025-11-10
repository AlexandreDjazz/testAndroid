package com.bankingapp.ui.screens.help

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun HelpSupportScreen(
    onBack: () -> Unit
) {
    var expandedFAQ by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Contact Support Section
            item {
                Text(
                    text = "Contact Support",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card {
                    Column {
                        HelpContactItem(
                            icon = Icons.Default.Email,
                            title = "Email Support",
                            subtitle = "support@bankingapp.com",
                            onClick = { /* TODO */ }
                        )

                        Divider()

                        HelpContactItem(
                            icon = Icons.Default.Phone,
                            title = "Phone Support",
                            subtitle = "+1 (800) 123-4567",
                            onClick = { /* TODO */ }
                        )

                        Divider()

                        HelpContactItem(
                            icon = Icons.Default.Chat,
                            title = "Live Chat",
                            subtitle = "Chat with our support team",
                            onClick = { /* TODO */ }
                        )
                    }
                }
            }

            // Resources Section
            item {
                Text(
                    text = "Resources",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                Card {
                    Column {
                        HelpResourceItem(
                            icon = Icons.Default.Article,
                            title = "User Guide",
                            onClick = { /* TODO */ }
                        )

                        Divider()

                        HelpResourceItem(
                            icon = Icons.Default.VideoLibrary,
                            title = "Video Tutorials",
                            onClick = { /* TODO */ }
                        )

                        Divider()

                        HelpResourceItem(
                            icon = Icons.Default.Description,
                            title = "Terms of Service",
                            onClick = { /* TODO */ }
                        )

                        Divider()

                        HelpResourceItem(
                            icon = Icons.Default.PrivacyTip,
                            title = "Privacy Policy",
                            onClick = { /* TODO */ }
                        )
                    }
                }
            }

            // FAQ Section
            item {
                Text(
                    text = "Frequently Asked Questions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(faqItems) { faq ->
                FAQItem(
                    question = faq.question,
                    answer = faq.answer,
                    isExpanded = expandedFAQ == faq.question,
                    onToggle = {
                        expandedFAQ = if (expandedFAQ == faq.question) null else faq.question
                    }
                )
            }

            // App Info
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Banking App",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Version 1.0.0",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = "© 2024 Banking App. All rights reserved.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HelpContactItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
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
                color = MaterialTheme.colorScheme.primary
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )
    }
}

@Composable
fun HelpResourceItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )
    }
}

@Composable
fun FAQItem(
    question: String,
    answer: String,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = answer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

data class FAQItem(
    val question: String,
    val answer: String
)

private val faqItems = listOf(
    FAQItem(
        question = "How do I reset my PIN?",
        answer = "Go to Settings > Security > Change PIN. You'll need to enter your current PIN to set a new one."
    ),
    FAQItem(
        question = "How do I make a payment?",
        answer = "From the home screen, tap 'Send Money', enter the recipient details, amount, and category. Confirm the payment to complete the transaction."
    ),
    FAQItem(
        question = "How can I view my transaction history?",
        answer = "Your recent transactions are displayed on the home screen. You can scroll down to see all your past transactions."
    ),
    FAQItem(
        question = "What should I do if I notice suspicious activity?",
        answer = "Immediately contact our support team via phone or email. You can also freeze your account from Settings > Security."
    ),
    FAQItem(
        question = "How do I enable dark mode?",
        answer = "Go to Settings > Appearance and toggle the Dark Mode switch."
    ),
    FAQItem(
        question = "Are my transactions secure?",
        answer = "Yes, all transactions are encrypted and protected with your PIN. We use industry-standard security measures to protect your data."
    )
)
