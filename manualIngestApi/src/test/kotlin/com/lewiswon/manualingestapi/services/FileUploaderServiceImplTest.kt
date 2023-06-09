package com.lewiswon.manualingestapi.services

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.codec.multipart.FilePart
import com.lewiswon.manualingestapi.utils.MockFilePart
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@ExtendWith(MockitoExtension::class)
class FileUploaderServiceImplTest {

    @InjectMocks
    private lateinit var fileUploaderService : FileUploaderServiceImpl

    companion object {
        private val logger
                = LoggerFactory.getLogger(FileUploaderServiceImpl::class.java)
    }

    @Test
    fun store() {
    }

    @Test
    @DisplayName("Test of the file extension check for CSV - Expect false for csv")
    fun testCheckCsv_True() {

        val testFilePart = mock(FilePart::class.java)
        `when`(testFilePart.filename()).thenReturn("test.csv")

        val result = fileUploaderService.checkCsv(testFilePart)

        assertEquals(false, result)

    }

    @Test
    @DisplayName("Test of the file extension check for CSV - Expect true for txt")
    fun testCheckCsv_False() {

        val testFilePart = mock(FilePart::class.java)
        `when`(testFilePart.filename()).thenReturn("test.txt")

        val result = fileUploaderService.checkCsv(testFilePart)

        assertEquals(true, result)

    }

    @Test
    @DisplayName("Test of store method. Input contain files of the csv format, expect to pass")
    fun testStore_Pass() {

//        val filePart1 = File("/Users/lewis/Documents/springfiles/rawfiles/name.csv")
//        val filePart2 = File("/Users/lewis/Documents/springfiles/rawfiles/test.json")
//        val filePart3 = File("/Users/lewis/Documents/springfiles/rawfiles/output-onlinetools.json")
//
//
//        val builder = MultipartBodyBuilder()
////        builder.part("file", FileSystemResource(filePart1))
//        builder.part("file", FileSystemResource(filePart2))
//        builder.part("file", FileSystemResource(filePart3))
//        val ans = builder.build()
//        logger.info("The builder is $ans")
//        val multipartData = LinkedMultiValueMap<String, File>()
////        multipartData.add("file", filePart1)
//        multipartData.add("file", filePart2)
//        multipartData.add("file", filePart3)
//        val ans2 = BodyInserters.fromMultipartData(multipartData)
//        logger.info("The bodyinserter is ${ans2.toMono()}")

        val path = Paths.get("/Users/lewis/Documents/springfiles/rawfiles/names.csv")
        val file = File("/Users/lewis/Documents/springfiles/rawfiles/names.csv")
        val name = "name.csv"
        val originalFileName = "name.csv"
        val contentType = "text/csv"
        val content = Files.readAllBytes(path)
        val bufferFactory = DefaultDataBufferFactory()
        val buffer = bufferFactory.allocateBuffer(content.size)
        buffer.write(content)
        val filePart = MockFilePart(name, originalFileName, contentType, Flux.just(buffer), file) as FilePart
        val testFlux = Flux.just(filePart)

        StepVerifier.create(fileUploaderService.store(testFlux))
                .expect

    }
}