package ru.shaprj.code.processor.util

import java.util.*


/*
 * Created by O.Shalaevsky on 10/4/2018
 */
data class Prefix(val prefix: String);
data class Postfix(val postfix: String);
data class PrefixList(val prefixes: List<Prefix>);
data class PrefixWithPostfixList(val prefixes: List<Prefix>, val postfixes: List<Postfix>);

fun getRandomPrefix(yamlFileName: String) = buildString {
    val prefixeList = YAMLParse.parseDto(yamlFileName, PrefixList::class);
    val randomIndex = Random().nextInt() % prefixeList.prefixes.size;
    append(prefixeList.prefixes.get(Math.abs(randomIndex)).prefix);
}

fun getRandomPrefixWithPostfix(yamlFileName: String) = buildString {
    val prefixWithPostfixList = YAMLParse.parseDto(yamlFileName, PrefixWithPostfixList::class);
    val randomIndexPrefix = Random().nextInt() % prefixWithPostfixList.prefixes.size;
    val randomIndexPostfix = Random().nextInt() % prefixWithPostfixList.postfixes.size;
    append(
            prefixWithPostfixList.prefixes.get(Math.abs(randomIndexPrefix)).prefix
                    + prefixWithPostfixList.postfixes.get(Math.abs(randomIndexPostfix)).postfix);
}