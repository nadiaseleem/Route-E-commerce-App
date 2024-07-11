package com.example.domain.exceptions

class ServerException(val msg: String, val serverMessage: String, val statusCode: Int) :
    Exception(serverMessage)