package com.example.bankapp.util

sealed class ApiState {
    object Loading : ApiState()
    class Failure(val msg: String) : ApiState()
    class Success<T>(val data: T) : ApiState()
    object Empty : ApiState()
}
