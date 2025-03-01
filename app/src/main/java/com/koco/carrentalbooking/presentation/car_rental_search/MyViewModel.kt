package com.koco.carrentalbooking.presentation.car_rental_search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(): ViewModel() {

    private val _pickUpLocation = MutableStateFlow("")
    private val _dropOffLocation = MutableStateFlow("")
    private val _pickUpDate = MutableStateFlow(LocalDate.now())
    private val _dropOffDate = MutableStateFlow(LocalDate.now())
    private val _kayakUrl = MutableStateFlow("")

    val pickUpLocation: StateFlow<String> = _pickUpLocation
    val dropOffLocation: StateFlow<String> = _dropOffLocation
    val pickUpDate: StateFlow<LocalDate> = _pickUpDate
    val dropOffDate: StateFlow<LocalDate> = _dropOffDate
    val kayakUrl: StateFlow<String> = _kayakUrl

    fun pickUpLocationChanged(newText: String) {
        _pickUpLocation.value = newText
    }

    fun dropOffLocationChanged(newText: String) {
        _dropOffLocation.value = newText
    }

    fun pickUpChanged(newText: LocalDate) {
        _pickUpDate.value = newText
    }

    fun dropOffChanged(newText: LocalDate) {
        _dropOffDate.value = newText
    }

    fun generateKayakUrl(pickupLocation: String, dropoffLocation: String, pickupDate: String, dropoffDate: String): String {
        Log.d("CheckURI","https://www.kayak.com/cars/$pickupLocation/$dropoffLocation/$pickupDate/$dropoffDate")
        return "https://www.kayak.com/cars/$pickupLocation/$dropoffLocation/$pickupDate/$dropoffDate"
    }

    fun generateKayakLink() {
        viewModelScope.launch {
            _kayakUrl.value = generateKayakUrl(
                _pickUpLocation.value,
                _dropOffLocation.value,
                _pickUpDate.value.toString(),
                _dropOffDate.value.toString()
            )
        }
    }

}