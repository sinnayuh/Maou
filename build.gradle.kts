plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "wtf.gun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.22") { exclude(module = "opus-java") }
    implementation("io.github.cdimascio:dotenv-java:2.3.1")
    implementation("com.google.code.gson:gson:2.10")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    jar {
        manifest {
            attributes["Main-Class"] = "wtf.gun.maou.Main"
        }
    }
}
