package com.example.blackcoffer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackcoffer.domain.explore.ExploreRequest
import com.example.blackcoffer.domain.explore.Purpose
import com.example.blackcoffer.domain.explore.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteExplore : ExploreRequest
) : ViewModel() {

    private val _state = MutableStateFlow(ExploreUiState())
    val state = combine(
        remoteExplore.userList,
        _state
    ) { userList, state ->
        state.copy(
            userInformationList = userList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    private var requestResultJob: Job? = null

    init {
//        remoteExplore.requesting.onEach { requesting->
//            _state.update { it.copy(isRequesting = requesting) }
//        }.launchIn(viewModelScope)

        remoteExplore.errors.onEach { error->
            _state.update {
                it.copy(
                    errorMessage = error
                )
            }
        }.launchIn(viewModelScope)

        requestUserList(2, listOf(Purpose.Friendship,Purpose.Coffee))
    }



    fun requestUserList(
        distanceRadius: Int,
        purposeListFilter: List<Purpose>,
    ) {
        _state.update { it.copy(
            isRequesting = true,
            isResponse = false,
        )}

        viewModelScope.launch {
            requestResultJob = remoteExplore.requestUserList(distanceRadius,purposeListFilter).listen()
        }
    }

    fun refreshUserList() {
        viewModelScope.launch {
            requestResultJob = remoteExplore.requestUserList(4,listOf(Purpose.Friendship,Purpose.Coffee)).listen()
        }
        _state.update { it.copy(
            isRefreshing = true,
            isRequesting = false
        )}
    }


    fun stopRequest() {
        TODO("Not yet implemented")
    }

    private fun Flow<RequestResult>.listen(): Job {
        return onEach { result ->
            when(result) {
                RequestResult.RequestSuccessful -> {
                    _state.update { it.copy(
                        isResponse = true,
                        isRequesting = false,
                        errorMessage = null,
                        isRefreshing = false,
                    ) }
                }
                is RequestResult.Error -> {
                    _state.update { it.copy(
                        isRequesting = false,
                        isResponse = true,
                        isRefreshing = false,
                        errorMessage = result.message
                    ) }
                }
            }
        }.catch { throwable ->
                _state.update { it.copy(
                    isRequesting = false,
                    isResponse = false,
                    isRefreshing = false
                ) }
            }
            .launchIn(viewModelScope)
    }

}