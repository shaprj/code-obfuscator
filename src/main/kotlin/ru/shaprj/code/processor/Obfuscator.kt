package ru.shaprj.code.processor

import com.github.javaparser.JavaParser
import com.github.javaparser.ast.CompilationUnit
import ru.shaprj.code.processor.util.isJavaCodeFileContentAlreadyProceeded
import ru.shaprj.code.processor.util.javaCodeFileContent
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


/*
 * Created by O.Shalaevsky on 10/4/2018
 */
fun obfuscateFile(
        path: String,
        content: () -> String?,
        obfuscate: (CompilationUnit) -> Unit) {

    val filePath = Paths.get(path);

    val compilationUnit = JavaParser.parse(content() ?: return);

    obfuscate(compilationUnit);

    Files.delete(filePath);
    val newFile = Files.createFile(filePath)
    Files.write(newFile, compilationUnit.toString().toByteArray())

    println("obfuscated file ${path}");
}

fun obfuscateDirectory(
        path: Path,
        obfuscate: (CompilationUnit) -> Unit) {

    if (isJavaCodeFileContentAlreadyProceeded(path)) {
        return;
    }

    if (!Files.isDirectory(path)) {
        obfuscateFile(path.toString(), {
            javaCodeFileContent(path);
        }, obfuscate);
    } else {
        Files.newDirectoryStream(path).forEach {
            obfuscateDirectory(it, obfuscate);
        }
    }
}