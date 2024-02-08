object ConcurrentAccessorClassProvider {

    val classLoader = this.javaClass.classLoader!! // it cannot be loaded through system classloader, so it's not null

    inline fun <reified T> provide(baseClass: T) : T {
        println(T::class.java.name + "ConcurrentAccessed")
        return classLoader
            .loadClass(T::class.java.name + "ConcurrentAccessed")
            .constructors
            .first()
            .newInstance(baseClass) as T
    }
}