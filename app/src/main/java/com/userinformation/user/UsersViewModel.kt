package com.userinformation.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userinformation.data.entities.User
import com.userinformation.data.repository.UserRepository
import com.userinformation.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class UserViewModel @ViewModelInject constructor(private val repository: UserRepository) :
    ViewModel() {

    private val _userData = MutableLiveData<State<List<User>>>()
    private val _userTitle = MutableLiveData<String>()

    val postsUsers: LiveData<State<List<User>>>
        get() = _userData

    val userTitle: LiveData<String>
        get() = _userTitle

    fun getUsers() {
        viewModelScope.launch {
            repository.getAllUser().collect {
                _userData.value = it
                _userTitle.value = repository.getUserTitle()
            }
        }
    }
}