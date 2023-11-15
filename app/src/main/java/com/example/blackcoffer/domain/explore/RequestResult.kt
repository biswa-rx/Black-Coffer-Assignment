package com.example.blackcoffer.domain.explore

sealed interface RequestResult {
    object RequestSuccessful: RequestResult
    data class Error(val message:String): RequestResult
}