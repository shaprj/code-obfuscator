# code-obfuscator for java


### General info


This project aims to provide ability for obfuscating java source code. 

The current realization is very limited - it process only:
 
 * local variables
 * method and constructor arguments
 * private member names
 * private method names

[javaparser](https://github.com/javaparser/javaparser) is used to build abstract syntax tree

It could be useful if you have to transfer your source codes to unreliable partner.

For example fragment:


``` java
private Rule createRule(Color outlineColor, Color fillColor) {
    Symbolizer symbolizer = null;
    Fill fill = null;
    Stroke stroke = sf.createStroke(ff.literal(outlineColor), ff.literal(outlineWidth));
    fill = sf.createFill(ff.literal(fillColor), ff.literal(fillTransparacy));
    symbolizer = sf.createPolygonSymbolizer(stroke, fill, geometryAttributeName);
    Rule rule = sf.createRule();
    rule.symbolizers().add(symbolizer);
    return rule;
}

```

   is modified to:

``` java
private Rule createRule(Color e1780272, Color c165976967) {
    Symbolizer f2145744846 = null;
    Fill b633338081 = null;
    Stroke value145600714 = sf.createStroke(ff.literal(e1780272), ff.literal(outlineWidth));
    b633338081 = sf.createFill(ff.literal(c165976967), ff.literal(fillTransparacy));
    f2145744846 = sf.createPolygonSymbolizer(value145600714, b633338081, geometryAttributeName);
    Rule index1032099618 = sf.createRule();
    index1032099618.symbolizers().add(f2145744846);
    return index1032099618;
}
```

This code is much less readable


### Run info

```

java -jar code-obfuscator-1.0-SNAPSHOT-jar-with-dependencies.jar -dir <target dir name>

```

Folder 'config' with dictionaries MUST be placed near to jar file