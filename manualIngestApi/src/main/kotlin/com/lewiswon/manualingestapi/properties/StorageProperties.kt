package com.lewiswon.manualingest.storage.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("storage")
class StorageProperties {

    private var location: String = "upload-dir"

    fun getLocation(): String{
        return location
    }

    fun setLocation(location: String){
        this.location = location
    }
}