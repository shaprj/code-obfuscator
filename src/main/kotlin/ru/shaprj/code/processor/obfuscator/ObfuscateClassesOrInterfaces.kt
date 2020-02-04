package ru.shaprj.code.processor.obfuscator

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import ru.shaprj.code.processor.obfuscator.visitors.PrivateClassMembersNameChangerVisitor


/*
 * Created by O.Shalaevsky on 10/9/2018
 */
fun obfuscateClassOrInterface(node: Node): Unit = with(node.findAll(ClassOrInterfaceDeclaration::class.java)) {
    reversed()
            .forEach {
        it.accept(PrivateClassMembersNameChangerVisitor(::buildArgumentName), node);
    }
}