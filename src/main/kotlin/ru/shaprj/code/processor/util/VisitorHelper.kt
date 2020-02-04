package ru.shaprj.code.processor.util

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.expr.NameExpr
import com.github.javaparser.ast.stmt.CatchClause
import com.github.javaparser.ast.stmt.TryStmt


/*
 * Created by O.Shalaevsky on 10/8/2018
 */
fun changeNameExprNameWithinCatchClause(node: Node, oldValue: String, newValue: String){
    if(node is NameExpr){
        if(node.nameAsString.equals(oldValue)){
            node.setName(newValue);
        }
    }
    if(!node.childNodes.isEmpty()){
        node.childNodes.forEach {
            changeNameExprNameWithinCatchClause(it, oldValue, newValue);
        }
    }
}

fun processTryCatchStmt(
        stmt: TryStmt,
        nameGenerator: () -> String){
    stmt.catchClauses.forEach {
        val oldCatchParamName = it.parameter.nameAsString
        val newName = nameGenerator();
        it.parameter = it.parameter.apply {
            setName(newName)
        }
        changeNameExprNameWithinCatchClause(it, oldCatchParamName, newName)
    }
}

fun changeNameExprName(node: Node, oldValue: String, newValue: String, catchClauseParam: () -> String){
    var catchClauseParamName = "";
    if(node is CatchClause){
        catchClauseParamName = node.parameter.nameAsString;
    }
    if(node is NameExpr){
        if(node.nameAsString.equals(oldValue)
                && oldValue != catchClauseParam()
        ){
            node.setName(newValue);
        }
    }
    if(!node.childNodes.isEmpty()){
        node.childNodes.forEach {
            changeNameExprName(it, oldValue, newValue) {
                if (catchClauseParamName != "")
                    catchClauseParamName
                else
                    catchClauseParam();
            };
        }
    }
}