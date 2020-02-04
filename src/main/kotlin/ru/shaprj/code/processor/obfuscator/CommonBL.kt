package ru.shaprj.code.processor.obfuscator

import ru.shaprj.code.processor.util.getRandomPrefix
import ru.shaprj.code.processor.util.getRandomPrefixWithPostfix
import java.util.*


/*
 * Created by O.Shalaevsky on 10/9/2018
 */
fun buildArgumentName() = buildString {
    append(getRandomPrefix("config/dictvars.yaml"));
    val rand = Random().nextInt();
    append(if (rand >= 0) rand else -rand);
}

fun buildMethodName() = buildString {
    append(getRandomPrefixWithPostfix("config/dictfuncs.yaml"));
    val rand = Random().nextInt();
    append(if (rand >= 0) rand else -rand);
}