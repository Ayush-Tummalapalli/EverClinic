package com.example.everclinic.ui.doctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.local.dao.DoctorDao
import com.example.everclinic.data.local.entities.DoctorEntity
import kotlinx.coroutines.launch

class AddDoctorViewModel(private val doctorDao: DoctorDao) : ViewModel() {
    fun addDoctor(doctor: DoctorEntity) {
        viewModelScope.launch {
            doctorDao.insert(doctor)
        }
    }
}

class AddDoctorViewModelFactory(private val doctorDao: DoctorDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDoctorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddDoctorViewModel(doctorDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}