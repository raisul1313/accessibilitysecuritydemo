package com.example.accessibilitysecuritydemo.presentation

import com.example.accessibilitysecuritydemo.domain.EnabledAccessibilityService


data class AccessibilityUiState(
    val isLoading: Boolean = false,
    val services: List<EnabledAccessibilityService> = emptyList(),
    val showSecurityWarning: Boolean = false,
    val error: String? = null
)
