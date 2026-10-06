package com.example.model

data class UserSession(
    val studentName: String = "",
    val studentClass: String = "",
    val studentAbsentNumber: String = "",
    val teacherName: String = "YULIANTO HARSONO"
) {
    val isLoggedIn: Boolean
        get() = studentName.isNotBlank() && studentClass.isNotBlank() && studentAbsentNumber.isNotBlank()
}
