plugins {
    kotlin("jvm")
}

group = "com.github.messieurMe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.17")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}