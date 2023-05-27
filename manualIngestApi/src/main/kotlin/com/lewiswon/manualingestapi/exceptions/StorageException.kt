package com.lewiswon.manualingestapi.exceptions

open class StorageException: RuntimeException {

    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}