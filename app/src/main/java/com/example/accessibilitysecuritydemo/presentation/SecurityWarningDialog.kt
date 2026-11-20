package com.example.accessibilitysecuritydemo.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.accessibilitysecuritydemo.domain.EnabledAccessibilityService


@Composable
fun SecurityWarningDialog(
    services: List<EnabledAccessibilityService>,
    onOpenSettings: () -> Unit,
    onExit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            // Prevent dismissing the security warning
        },
        title = {
            Text(
                text = "Security Risk Detected",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "An untrusted accessibility service is enabled."
                )

                Text(text = "Detected Apps:")

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    services.forEach { service ->
                        Text(
                            text = "• ${service.appName}"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Please disable this accessibility service and try again."
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onOpenSettings
            ) {
                Text("OPEN SETTINGS")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onExit
            ) {
                Text("EXIT")
            }
        }
    )
}
