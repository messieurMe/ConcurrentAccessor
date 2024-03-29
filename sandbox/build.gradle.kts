plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

group = "com.github.messieurMe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")

    implementation(project(":concurrentAccessorApi"))
    ksp(project(":concurrentAccessorKsp"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}