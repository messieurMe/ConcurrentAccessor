package com.example.sandbox

import ConcurrentAccessor
import java.lang.reflect.Proxy

@ConcurrentAccessor
interface ExampleWithAnnotation{
    fun sampleFunction()

    fun functionWithReturn() : Int

    fun functionWithParameter(parameter: Int)
}

class ExampleWithAnnotationImpl : ExampleWithAnnotation by Proxy.newProxyInstance(
    ExampleWithAnnotation::class.java.classLoader,
    arrayOf(ExampleWithAnnotation::class.java),
    { _, method, _ ->
        val template = "method with name ${method?.name} in thread ${Thread.currentThread().id}"
        println("Started $template")
        Thread.sleep(1_000)
        println("Ended $template")

        when (method?.returnType?.typeName) {
            "void" -> Unit
            "int" -> -1
            else -> error("Unexpected return type ${method?.returnType?.name}. Please, add default value for it")
        }
    }
) as ExampleWithAnnotation