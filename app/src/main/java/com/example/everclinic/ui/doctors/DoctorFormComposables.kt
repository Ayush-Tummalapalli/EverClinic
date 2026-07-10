package com.example.everclinic.ui.doctors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicInformationSection(
    fullName: String, onFullNameChange: (String) -> Unit,
    specialty: String, onSpecialtyChange: (String) -> Unit,
    experience: String, onExperienceChange: (String) -> Unit,
    phoneNumber: String, onPhoneNumberChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit
) {
    Column {
        Text("Basic Information", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = fullName, onValueChange = onFullNameChange, label = { Text("Full Name *") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = specialty, onValueChange = onSpecialtyChange, label = { Text("Specialty *") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = experience, onValueChange = onExperienceChange, label = { Text("Years of Experience *") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = phoneNumber, onValueChange = onPhoneNumberChange, label = { Text("Phone Number *") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email Address *") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AvailabilitySection(selectedDays: List<String>, onDaySelected: (String) -> Unit) {
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    Column {
        Text("Availability *", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("Select the days when the doctor is available", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))
        days.forEach { day ->
            DaySelector(day = day, isSelected = selectedDays.contains(day), onDaySelected = onDaySelected)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DaySelector(day: String, isSelected: Boolean, onDaySelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDaySelected(day) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFE1BEE7) else Color.White)
    ) {
        Text(text = day, modifier = Modifier.padding(16.dp), color = if (isSelected) Color(0xFF6A1B9A) else Color.Black)
    }
}
