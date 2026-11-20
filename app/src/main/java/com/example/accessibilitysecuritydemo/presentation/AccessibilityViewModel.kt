package com.example.accessibilitysecuritydemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.accessibilitysecuritydemo.data.AccessibilityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AccessibilityViewModel(
    private val repository: AccessibilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccessibilityUiState())
    val uiState: StateFlow<AccessibilityUiState> = _uiState.asStateFlow()


    fun checkAccessibilityServices() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val services = repository.getEnabledThirdPartyServices()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        services = services,
                        showSecurityWarning = services.isNotEmpty(),
                        error = null
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Something went wrong"
                    )
                }
            }
        }
    }

}


class AccessibilityViewModelFactory(
    private val repository: AccessibilityRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccessibilityViewModel::class.java)) {
            return AccessibilityViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

