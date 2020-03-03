package com.hjl.commonlib.base.mvvm

class ApiException(val errorCode: Int, val errorMessage: String) : RuntimeException(errorMessage)