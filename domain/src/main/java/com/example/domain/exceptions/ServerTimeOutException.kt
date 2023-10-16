package com.example.domain.exceptions

import java.io.IOException

class ServerTimeOutException(e: Exception) : IOException(e)