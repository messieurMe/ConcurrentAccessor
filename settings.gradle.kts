plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "ConcurrentAccessor"
include("concurrentAccessorKsp")
include("sandbox")
include("concurrentAccessorApi")
