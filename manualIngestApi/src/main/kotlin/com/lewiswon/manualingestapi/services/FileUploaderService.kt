package com.lewiswon.manualingestapi.services

import org.springframework.context.annotation.Bean
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FileUploaderService {

    fun store(filePart: Flux<FilePart>) : Mono<MutableMap<String, String>>


}