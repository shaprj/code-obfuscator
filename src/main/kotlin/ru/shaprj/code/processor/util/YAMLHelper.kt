package ru.shaprj.code.processor.util

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.FileSystems
import java.nio.file.Files
import kotlin.reflect.KClass


/*
 * Created by O.Shalaevsky on 9/4/2018
 */
object YAMLParse {
    private val mapper = let {
        JsonFactory()
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        mapper
    }

    fun <T: Any> parseDto(fileName: String, dto: KClass<T>): T {
        return Files.newBufferedReader(FileSystems.getDefault().getPath(fileName)).use { mapper.readValue(it, dto.java) }
    }
}