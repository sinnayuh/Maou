plugins {
    id("java")
}

group = "wtf.gun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.22") { exclude(module = "opus-java") }
}