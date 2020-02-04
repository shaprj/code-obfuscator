package ru.shaprj.code.processor.util

/*
 * Created by O.Shalaevsky on 8/31/2018
 */
fun getParam(
        args: Array<String>,
        getParamName: () -> String): String = arrayListOf<String>()
        .apply {
            var value: String? = null;
            var flag = false;
            args.forEach {
                if(flag){
                    value = it;
                    flag = false;
                }
                if(it.toLowerCase().equals(getParamName().toLowerCase())){
                    flag = true;
                }
            }
            add(value ?: throw ExceptionInInitializerError("Please add input param \"${getParamName()}\" ex.: progName.jar ${getParamName()} <value>"));
        }[0];