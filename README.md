Project which uses ksp to generated thread-safe classes by interface

Interesting files:
- [example](sandbox/src/main/kotlin/com/example/sandbox/Example.kt)
- [processor](concurrentAccessorKsp/src/main/kotlin/concurrentAccessor/CASymbolProcessor.kt)

# How to use

- Extract interface from your class
- Add annotation [ConcurrentAccessor](concurrentAccessorApi/src/main/kotlin/ConcurrentAccessor.kt) on interface
- Change your build.gradle file:
```kotlin
plugin {
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

implementation {
    implementation(project(":concurrentAccessorApi"))
    ksp(project(":concurrentAccessorKsp"))
}
```
- You can access your thread-safe version of class via 
```kotlin
    ConcurrentAccessorClassProvider
    .provide<ExampleWithAnnotation>(ExampleWithAnnotationImpl())
//           ^^^^^^^^^^^^^^^^^^^^^ |^^^^^^^^^^^^^^^^^^^^^^^^^^^
//              Type of interface  | Your realisation of interface which methods will be called thead-safely
``` 

# TODOs:

- if your method returns flow then only one of subscribers will receive emitted data
- parameters still not thread-safe. Only methods
- right now your methods should not have any type parameters