package ru.shaprj.code.processor.util

import java.nio.file.Files
import java.nio.file.Path


/*
 * Created by O.Shalaevsky on 10/5/2018
 */
fun javaCodeFileContent(path: Path) = if (!path.toString().endsWith(".java")) {
    println("file $path is not a java code");
    null
} else {
    buildString {
        Files.readAllLines(path).forEach {
            append(it + "\r\n");
        }
    }
}

private val proceededJavaCodeFiles = mutableListOf<Path>();

fun isJavaCodeFileContentAlreadyProceeded(path: Path): Boolean = with(proceededJavaCodeFiles){
    return if(contains(path)) true else !add(path);
}