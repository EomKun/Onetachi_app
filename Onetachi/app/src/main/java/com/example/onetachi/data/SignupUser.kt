package com.example.onetachi.data

data class SignupUser (
    val id: String,
    val regNum1: String,
    val regNum2: String
)

data class SignupUserResult(
    val result: Boolean,
    val resultStr: String
)