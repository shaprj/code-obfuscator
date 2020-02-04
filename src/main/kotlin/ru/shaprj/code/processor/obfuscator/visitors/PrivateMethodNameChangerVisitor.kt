package ru.shaprj.code.processor.obfuscator.visitors;

import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.Parameter
import com.github.javaparser.ast.expr.Expression
import com.github.javaparser.ast.expr.MethodCallExpr
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

/*
 * Created by O.Shalaevsky on 10/5/2018
 */
class PrivateMethodNameChangerVisitor(val methodNameGenerator: () -> String): VoidVisitorAdapter<CompilationUnit>() {
    override fun visit(n: MethodDeclaration, arg: CompilationUnit) {
        if(n.isPrivate){
            val compilationUnit = arg;
            val newPrivateMethodName = methodNameGenerator();
            compilationUnit.findAll(MethodCallExpr::class.java)
                    .forEach {
                        if(it.nameAsString.equals(n.nameAsString) && parametersIdentical(it.arguments, n.parameters)){
                            it.setName(newPrivateMethodName)
                        }
                    }
            n.setName(newPrivateMethodName);
        }
    }

    private fun parametersIdentical(pars1: NodeList<Expression>, pars2: NodeList<Parameter>) = arrayOf(false).apply {
        if(pars1.size != pars2.size){
            this[0] = false;
        }

        this[0] = true;
    }[0];
}