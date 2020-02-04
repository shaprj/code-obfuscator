package ru.shaprj.code.processor.obfuscator

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.MethodDeclaration
import ru.shaprj.code.processor.obfuscator.visitors.MethodArgumentsChangerVisitor
import ru.shaprj.code.processor.obfuscator.visitors.MethodVariablesNameChangerVisitor


/*
 * Created by O.Shalaevsky on 10/5/2018
 */
fun obfuscateMethods(node: Node): Unit = with(node.findAll(MethodDeclaration::class.java)) {
    forEach {
        it.accept(MethodArgumentsChangerVisitor(::buildArgumentName),  node);
    }

    forEach {
        it.accept(MethodVariablesNameChangerVisitor(::buildArgumentName), node);
    }

//    forEach {
//        it.accept(PrivateMethodNameChangerVisitor(::buildMethodName), compilationUnit);
//    }

}