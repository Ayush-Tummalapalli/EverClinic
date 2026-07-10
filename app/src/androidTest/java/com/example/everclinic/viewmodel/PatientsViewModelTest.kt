
/**
 * This file contains the unit tests for the PatientsViewModel.
 */
package com.example.everclinic.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.everclinic.data.repository.ClinicRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

class PatientsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PatientsViewModel
    private lateinit var repository: ClinicRepository

    @Before
    fun setup() {
        repository = mockk()
        viewModel = PatientsViewModel(repository)
    }

    // Add tests here
}
