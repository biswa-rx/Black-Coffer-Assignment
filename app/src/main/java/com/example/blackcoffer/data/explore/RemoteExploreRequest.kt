package com.example.blackcoffer.data.explore

import android.Manifest
import android.os.SystemClock
import com.example.blackcoffer.domain.explore.ExploreRequest
import com.example.blackcoffer.domain.explore.Purpose
import com.example.blackcoffer.domain.explore.RequestResult
import com.example.blackcoffer.domain.explore.Sex
import com.example.blackcoffer.domain.explore.UserInformation
import com.example.blackcoffer.utils.FakeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import java.io.IOException
import java.util.UUID

class RemoteExploreRequest : ExploreRequest{

    private val _userList = MutableStateFlow<List<UserInformation>>(emptyList())
    override val userList: StateFlow<List<UserInformation>>
        get() = _userList.asStateFlow()

    private val _isRequesting = MutableStateFlow<Boolean>(false)
    override val requesting: StateFlow<Boolean>
        get() = _isRequesting

    private val _errors = MutableSharedFlow<String>()
    override val errors: SharedFlow<String>
        get() = _errors


    override suspend fun requestUserList(
        distanceRadius: Int,
        purposeListFilter: List<Purpose>,
    ): Flow<RequestResult>{
        return flow {
            try {
                _isRequesting.value = true

                //Here we have to do network call but demonstration purpose I use fake network call
                //Here I implemented to mimicking network request
                SystemClock.sleep(2000);

                val shuffledList = FakeDataSource.fakeUserList.shuffled()
                _userList.value = shuffledList

                _isRequesting.value = false
                emit(RequestResult.RequestSuccessful)
            } catch (e : Exception){
                emit(RequestResult.Error("Request interrupted"))
            }
        }.flowOn(Dispatchers.IO)
    }


    override fun stopRequest() {
        TODO("Not yet implemented")
    }


}


