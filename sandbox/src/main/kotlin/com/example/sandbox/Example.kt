package com.example.sandbox

import ConcurrentAccessorClassProvider
import java.util.*
import kotlin.concurrent.thread

fun main(){
    val threads = 3
    val repeats = 2

    val concurrentlyAccessedClass = ConcurrentAccessorClassProvider
        .provide<ExampleWithAnnotation>(ExampleWithAnnotationImpl())

    val normalClass = ExampleWithAnnotationImpl()

    println("With concurrent access")
    sampleTest(concurrentlyAccessedClass, threads, repeats)

    println("With normal access")
    sampleTest(normalClass, threads, repeats)

}

fun sampleTest(
    concurrentlyAccessedClass: ExampleWithAnnotation,
    threads: Int,
    repeats: Int
){
    val launchedThreads = LinkedList<Thread>()
    repeat(threads){
        thread {
            repeat(repeats) {
                with(concurrentlyAccessedClass) {
                    sampleFunction()
                    functionWithParameter(-1)
                    functionWithReturn()
                }
            }
        }.also(launchedThreads::add)
    }
    launchedThreads.forEach(Thread::join)
}
