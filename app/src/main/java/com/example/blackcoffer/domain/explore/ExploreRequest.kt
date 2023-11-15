package com.example.blackcoffer.domain.explore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ExploreRequest {

    val userList: StateFlow<List<UserInformation>>
    val requesting: StateFlow<Boolean>
    val errors: SharedFlow<String>

    //Distance is in KM Filter
    suspend fun requestUserList(distanceRadius : Int, purposeListFilter: List<Purpose>):Flow<RequestResult>

    fun stopRequest()
}