package com.example.sandbox

import concurrentAccessor.ConcurrentAccessor


fun main(){
    println("Hi")
}


@ConcurrentAccessor
interface ExampleWithAnnotation{
    fun lol()

    fun lolReturned() : Int

    fun lolParameters(x: Int)
}

class ExampleWithAnnotationImpl{

    fun lol() {
        val int = 0
        println(int)
    }

    fun lolReturned() : Int {
        return -1
    }

    fun lolParameters(x: Int) {
        println(x)
    }
}