package com.example.accessibilitysecuritydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.accessibilitysecuritydemo.data.AccessibilityRepository
import com.example.accessibilitysecuritydemo.presentation.AccessibilityViewModel
import com.example.accessibilitysecuritydemo.presentation.AccessibilityViewModelFactory
import com.example.accessibilitysecuritydemo.presentation.AccessibilityScreen
import com.example.accessibilitysecuritydemo.ui.theme.AccessibilitySecurityDemoTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: AccessibilityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = AccessibilityRepository(applicationContext)
        viewModel = ViewModelProvider(
            this,
            AccessibilityViewModelFactory(repository)
        )[AccessibilityViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            AccessibilitySecurityDemoTheme {
                AccessibilityScreen(viewModel)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        if (::viewModel.isInitialized) {
            viewModel.checkAccessibilityServices()
        }
    }
}