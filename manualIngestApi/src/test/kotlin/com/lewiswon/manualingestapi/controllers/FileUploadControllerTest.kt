package com.lewiswon.manualingestapi.controllers

import com.lewiswon.manualingestapi.services.FileUploaderService
import com.lewiswon.manualingestapi.services.FileUploaderServiceImpl
import com.lewiswon.manualingestapi.services.FileUploaderServiceImplTest
import jakarta.annotation.PostConstruct
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.http.codec.multipart.FilePart
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import java.io.File

@ExtendWith(SpringExtension::class)
//@RunWith(SpringRunner::class)
@WebFluxTest(FileUploadController::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(classes = [FileUploadController::class, FileUploaderService::class])
@Import(FileUploaderServiceImpl::class)
@ContextConfiguration(classes = [FileUploadController::class, FileUploaderService::class])
@AutoConfigureWebTestClient
class FileUploadControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

//    @Autowired
//    private lateinit var fileUploaderController: FileUploadController

    @SpyBean
    private lateinit var fileUploaderService : FileUploaderServiceImpl

    companion object {
        private val logger
                = LoggerFactory.getLogger(FileUploadController::class.java)
    }

//    @PostConstruct
//    fun postConstruct() {
//        fileUploaderService = FileUploaderServiceImpl()
//    }

//    @BeforeEach
//    fun setup() {
//        this.webTestClient = WebTestClient.bindToController(fileUploaderController)
//                .build() }

    @Test
    @DisplayName("Testing Fileupload Controller. Expect Bad Request")
    fun handleManualFileUpload_ExpectBadRequest() {
        val filePart1 = File("//Users/lewis/Documents/springfiles/rawfiles/names.csv")
        val filePart2 = File("//Users/lewis/Documents/springfiles/rawfiles/test.json")
        val filePart3 = File("//Users/lewis/Documents/springfiles/rawfiles/output-onlinetools.json")
        val builder = MultipartBodyBuilder()
        builder.part("file", FileSystemResource(filePart1))
        builder.part("file", FileSystemResource(filePart2))
        builder.part("file", FileSystemResource(filePart3))

        val ans = webTestClient.put()
                .uri("/upload/manual")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest

        logger.info("The fileuploader controlller ans is $ans")
    }

    @Test
    @DisplayName("Testing Fileupload Controller. Expect isOk")
    fun handleManualFileUpload_ExpectIsOk() {
        val filePart1 = File("//Users/lewis/Documents/springfiles/rawfiles/names.csv")
        val builder = MultipartBodyBuilder()
        builder.part("file", FileSystemResource(filePart1))

        val ans = webTestClient.put()
                .uri("/upload/manual")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isOk

        logger.info("The fileuploader controlller ans is $ans")
    }
}