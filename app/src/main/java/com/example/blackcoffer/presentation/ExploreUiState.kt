package com.example.blackcoffer.presentation

import com.example.blackcoffer.domain.explore.UserInformation

data class ExploreUiState(
    val userInformationList: List<UserInformation> = emptyList(),
    val isRequesting: Boolean = false,
    val isResponse: Boolean = false,
    val isRefreshing:Boolean = false,
    val errorMessage: String? = null
)