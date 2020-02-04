package ru.shaprj.code.processor.obfuscator.visitors

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.stmt.TryStmt
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import ru.shaprj.code.processor.util.changeNameExprName
import ru.shaprj.code.processor.util.processTryCatchStmt

/*
 * Created by O.Shalaevsky on 10/4/2018
 *
 * bug - working with lambdas
 */
class MethodArgumentsChangerVisitor(val argumentNameGenerator: () -> String): VoidVisitorAdapter<Node>(){
    override fun visit(mth: MethodDeclaration, arg: Node) {
        val paramNamesMap = mutableMapOf<String, String>().apply {
            mth.parameters.forEach {
                val newName = argumentNameGenerator();
                put(it.nameAsString, newName);
                it.setName(newName);
            }
        }

        if(mth.body.isPresent){
            mth.body.get().statements.forEach {
                if(it.isTryStmt){
                    processTryCatchStmt(it as TryStmt, argumentNameGenerator);
                }
                paramNamesMap.forEach { oldValue, newValue ->
                    changeNameExprName(it, oldValue, newValue) { "" }
                }
            }
        }
    }
}