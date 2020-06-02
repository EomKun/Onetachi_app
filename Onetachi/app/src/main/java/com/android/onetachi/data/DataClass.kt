package com.android.onetachi.data

data class User (val id: String)

data class authenticatorSelection(
    val authenticatorAttachment: String,
    val userVerification: String
)
data class SignupUser (
    val attestation: String,
    val authenticatorSelection: authenticatorSelection,
    val id: String,
    val username: String,
    val idPart1: String,
    val idPart2: String
)
data class SignupResult (
    val response: String
)

data class SignupUserResult(
    val result: Boolean,
    val resultStr: String
)

data class Papers(
    var papers: ArrayList<Array<String>>
)