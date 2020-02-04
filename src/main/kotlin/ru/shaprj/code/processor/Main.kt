package ru.shaprj.code.processor

import com.github.javaparser.ast.CompilationUnit
import ru.shaprj.code.processor.obfuscator.obfuscateConstructors
import ru.shaprj.code.processor.obfuscator.obfuscateMethods
import ru.shaprj.code.processor.util.getParam
import java.nio.file.Paths


/*
 * Created by O.Shalaevsky on 10/4/2018
 */
fun main(args: Array<String>){
    obfuscateDirectory(
            Paths.get(obfuscateDirectory(args)),
            ::obfuscate)
}

private fun obfuscate(compilationUnit: CompilationUnit) = with(compilationUnit){
    obfuscateMethods(this);
    obfuscateConstructors(this);
}

private fun obfuscateFile(args: Array<String>) = getParam(args, { "-file" })

private fun obfuscateDirectory(args: Array<String>) = getParam(args, { "-dir" })