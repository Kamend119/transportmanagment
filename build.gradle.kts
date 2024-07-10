import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.8.20" // Используйте последнюю совместимую версию Kotlin
    kotlin("plugin.serialization") version "1.8.20" // Если используется
    id("org.jetbrains.compose") version "1.4.0" // Используйте последнюю версию плагина Compose
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    maven(url = "https://repo.spring.io/milestone")
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.2")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")

    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.AppImage, TargetFormat.Exe)
            packageName = "transportmanagment"
            packageVersion = "1.0.0"


            windows {
                iconFile.set(project.file("src/main/resources/images/delivery-truck512-512.png"))
            }
            linux {
                iconFile.set(project.file("src/main/resources/images/delivery-truck512-512.png"))
            }
            macOS {
                iconFile.set(project.file("src/main/resources/images/delivery-truck512-512.png"))
            }
        }
    }
}
