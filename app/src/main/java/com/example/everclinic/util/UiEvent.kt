
/**
 * This file defines the UiEvent sealed class, which is used for one-time events
 * from ViewModels to the UI.
 */
package com.example.everclinic.util

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
}
