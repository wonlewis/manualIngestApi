package com.lewiswon.manualingestapi.services

import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FileUploaderService {

    fun store(filePart: Flux<FilePart>) : Mono<MutableMap<String, String>>


}