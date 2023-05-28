package com.lewiswon.manualingestapi.services

import com.lewiswon.manualingestapi.utils.Utils
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import java.nio.file.Paths

@Service
class FileUploaderServiceImpl :FileUploaderService  {

    private val utils = Utils()

    companion object {
        private val logger
                = LoggerFactory.getLogger(FileUploaderServiceImpl::class.java)
    }

    override fun store(filePart: Flux<FilePart>) : Mono<MutableMap<String, String>> {

        logger.info("The filePart at service level is $filePart")
        val taggingId = utils.generateRandomNumber()
        val basePath = "./upload/$taggingId"
        var wrongCount = 0
        val returnResult = mutableMapOf <String, String>()

        return filePart
                .doOnNext{ fp ->
                    logger.info("Received File: ${fp.filename()}")
                    if (checkCsv(fp)) wrongCount++
                }
                .collectList()
                .doOnNext {
                    logger.info("Processing $it and the wrong count is $wrongCount")
                    if (wrongCount != 0){
                        returnResult["message"] = "$wrongCount of ${it.size} are of the wrong filetype. Please upload again."
                    }
                    else {
                        for (x in it){
                            val path = Paths.get(basePath)
                            utils.createDirectory(path)
                            x.transferTo(File(basePath, x.filename())).subscribe {
                                logger.info("Have just transferred ${x.filename()}")
                            }
                            logger.info("The file is ${File(basePath, x.filename())}")
                        }
                        returnResult["message"] = "All files have been successfully uploaded."
                        returnResult["jobId"] = taggingId

                    }
                }.then(Mono.just(returnResult))

    }

    fun checkCsv(filePart: FilePart) : Boolean {
        return utils.getFileExtension(filePart.filename())!= "csv"
    }
}