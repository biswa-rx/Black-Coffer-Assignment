package com.example.blackcoffer.domain.explore

enum class Sex{
    Male,
    Female,
    Transgender
}

enum class Purpose{
    Coffee,
    Business,
    Hobbies,
    Friendship,
    Movies,
    Dinning,
    Dating,
    Matrimony
}

typealias UserInformation = UserDetail

data class UserDetail(
    val userId: Int,
    val name: String,
    val location: String,
    val distance: Float,
    val sex: Sex,
    val profession: String,
    val purposeList: List<Purpose>,
    val email: String,
    val about: String,
    val profilePicUrl:String? = null
)