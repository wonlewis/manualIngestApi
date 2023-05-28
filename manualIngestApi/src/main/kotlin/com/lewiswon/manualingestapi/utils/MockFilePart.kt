/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lewiswon.manualingestapi.utils
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.multipart.FilePart
import org.springframework.util.Assert
import org.springframework.util.FileCopyUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path


class MockFilePart(name: String, originalFilename: String, contentType: String, content: Flux<DataBuffer>, file: File) : FilePart {

    private val name: String
    private val originalFilename: String
    private val contentType: String
    private val content: Flux<DataBuffer>
    private val file: File

    init {
        Assert.hasLength(name, "Name must not be empty")
        this.name = name
        this.originalFilename = originalFilename ?: ""
        this.contentType = contentType
        this.content = content
        this.file = file
    }
    override fun filename(): String {
        return originalFilename
    }

    override fun name(): String {
        return name
    }

    override fun headers(): HttpHeaders {
        return HttpHeaders()
    }

    @Throws(IOException::class, IllegalStateException::class)
    override fun transferTo(dest: File) : Mono<Void> {
//        val outputStream = PipedOutputStream()
//        val inputStream = PipedInputStream()
//        inputStream.connect(outputStream)
//        DataBufferUtils.write(content, outputStream).subscribe()
//        val thisContent = readContent(inputStream)
        FileCopyUtils.copy(file, dest)
        return Mono.empty()
    }

    override fun transferTo(dest: Path): Mono<Void> {
        return Mono.empty()
    }


    override fun content(): Flux<DataBuffer> {
        return content
    }

    @Throws(IOException::class)
    fun readContent(stream: InputStream): String {
        val contentStringBuffer = StringBuffer()
        val tmp = ByteArray(stream.available())
        val byteCount = stream.read(tmp, 0, tmp.size)
        contentStringBuffer.append(String(tmp))
        return contentStringBuffer.toString()
    }

}
