package com.example.domain.exceptions

class ServerException(val status: String, val serverMessage: String, val statusCode: Int) :
    Exception(serverMessage)