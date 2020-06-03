package com.example.onetachi.data

data class User (val id: String)
data class SignupUser (
    val id: String,
    val regNum1: String,
    val regNum2: String
)

data class SignupUserResult(
    val result: Boolean,
    val resultStr: String
)


data class Papers(
    var papers: ArrayList<Array<String>>
)