package ru.shaprj.code.processor.obfuscator

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.ConstructorDeclaration
import ru.shaprj.code.processor.obfuscator.visitors.ConstructorArgumentsChangerVisitor


/*
 * Created by O.Shalaevsky on 10/10/2018
 */
fun obfuscateConstructors(node: Node): Unit = with(node.findAll(ConstructorDeclaration::class.java)){
    forEach {
        it.accept(ConstructorArgumentsChangerVisitor(::buildArgumentName),  node);
    }
}