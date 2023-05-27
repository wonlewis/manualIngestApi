package com.lewiswon.manualingestapi.utils

// Java Program To use UUID to generate long positive values with BigInteger
import org.apache.commons.io.FilenameUtils
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class Utils {

    fun generateRandomNumber(): String{

        // The UUID.randomUUID().toString() of length
        // consist of digits ,alphabets which will be handled
        // to get digits using BigInteger and "-" which needs
        // to be replaced with "". Inside  new
        // BigInteger("%010d", new
        // BigInteger(UUID.randomUUID().toString().replace("-",
        // ""), 16)) 16 represent radix .
        val generateUUIDNo = String.format("%010d", BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))

        // To decide length of unique positive long number
        // generateUUIDNo.length() - uniqueNoSize is being
        // used
        return generateUUIDNo.substring(generateUUIDNo.length - 10)
    }

    fun getFileExtension(string: String): String {
        return FilenameUtils.getExtension(string)
    }

    fun createDirectory(path: Path)  {
        Files.createDirectories(path)
    }
}