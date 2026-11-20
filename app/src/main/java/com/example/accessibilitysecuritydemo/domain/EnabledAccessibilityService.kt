package com.example.accessibilitysecuritydemo.domain

data class EnabledAccessibilityService(
    val packageName: String,
    val appName: String,
    val serviceName: String,
    val isSystemApp: Boolean
)
