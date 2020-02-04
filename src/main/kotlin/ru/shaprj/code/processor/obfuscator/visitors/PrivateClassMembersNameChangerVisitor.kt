package ru.shaprj.code.processor.obfuscator.visitors;

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.FieldDeclaration
import com.github.javaparser.ast.expr.FieldAccessExpr
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import ru.shaprj.code.processor.util.changeNameExprNameWithinCatchClause

/*
 * Created by O.Shalaevsky on 10/5/2018
 */
class PrivateClassMembersNameChangerVisitor(val variableNameGenerator: () -> String) : VoidVisitorAdapter<Node>() {
    override fun visit(cls: ClassOrInterfaceDeclaration, arg: Node) {
        mutableMapOf<String, String>().apply {
            cls.findAll(FieldDeclaration::class.java)
                    .filter {
                        it.isPrivate
                    }
                    .reversed()
                    .forEach {
                        it.variables.forEach {
                            val newName = variableNameGenerator();
                            changeFieldAccessExpr(cls, it.nameAsString, newName);
                            changeNameExprNameWithinCatchClause(cls, it.nameAsString, newName);
                            it.setName(newName)
                        }
                    }
        }
    }

    private fun changeFieldAccessExpr(node: Node, oldName: String, newName: String) = with(node.findAll(FieldAccessExpr::class.java)){
        reversed()
                .forEach {
                    if(oldName.equals(it.nameAsString)){
                        it.setName(newName);
                    }
                }
    }
}