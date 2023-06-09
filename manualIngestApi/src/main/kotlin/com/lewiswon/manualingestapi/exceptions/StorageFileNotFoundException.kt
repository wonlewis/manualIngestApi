import com.lewiswon.manualingestapi.exceptions.StorageException

class StorageFileNotFoundException : StorageException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
