package com.nhinhnguyenuit.jetpackproject.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val userFlow = userRepository.getUsersStream().cachedIn(viewModelScope)

    private val _userDetail = MutableStateFlow<User?>(null)
    val userDetail: StateFlow<User?> = _userDetail

    private val _localUsers = MutableStateFlow<List<User>>(emptyList())
    val localUsers: StateFlow<List<User>> = _localUsers

    init {
        viewModelScope.launch {
            val localUsers = userRepository.getLocalUsers()
            if (localUsers.isNotEmpty()){
                _localUsers.value = localUsers
            }
        }
    }

    fun getUserDetail(login: String){
        viewModelScope.launch {
            try {
                _userDetail.value = userRepository.getUserDetail(login)
            } catch (e: Exception){
                //Handle the error accordingly
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            userRepository.clearLocalUsers()
            userRepository.saveUsers((_localUsers.value))
        }
    }

}