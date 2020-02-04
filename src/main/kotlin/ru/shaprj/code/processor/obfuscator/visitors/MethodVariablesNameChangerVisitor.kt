package ru.shaprj.code.processor.obfuscator.visitors;

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.expr.VariableDeclarationExpr
import com.github.javaparser.ast.stmt.BlockStmt
import com.github.javaparser.ast.stmt.ForStmt
import com.github.javaparser.ast.stmt.IfStmt
import com.github.javaparser.ast.stmt.Statement
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import ru.shaprj.code.processor.util.changeNameExprNameWithinCatchClause
import kotlin.reflect.KClass

/*
 * Created by O.Shalaevsky on 10/5/2018
 */
class MethodVariablesNameChangerVisitor(val variableNameGenerator: () -> String) : VoidVisitorAdapter<Node>() {
    override fun visit(mth: MethodDeclaration, arg: Node) {
        if (mth.body.isPresent) {
            checkIfs(mth);
            checkStmts(mth, ForStmt::class);
            checkStmts(mth, BlockStmt::class);
        }
    }

    private fun checkIfs(
            mth: Node) = mth.findAll(IfStmt::class.java)
            .reversed()
            .forEach {
                checkStmts(it, ForStmt::class);
                checkStmts(it, BlockStmt::class);
            }

    private fun checkStmts(
            mth: Node,
            cls: KClass<out Statement>) = mth.findAll(cls.java)
            .reversed()
            .forEach {
                val variables = findVariableStatement(it);
                variables.forEach { oldValue, newValue ->
                    changeNameExprNameWithinCatchClause(it, oldValue, newValue);
                }
            };

    private fun findVariableStatement(stmt: Statement) = mutableMapOf<String, String>().apply {
        stmt.findAll(VariableDeclarationExpr::class.java)
                .forEach {
                    it.variables.forEach {
                        val newName = variableNameGenerator();
                        put(it.nameAsString, newName);
                        it.setName(newName);
                    }
                }
    };
}