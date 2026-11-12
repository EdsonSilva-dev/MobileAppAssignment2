package com.example.mobileappassignment2.exceptions

open class WordLengthException(message: String) : Exception(message)

class VeryLongWordException(message: String) : WordLengthException(message)