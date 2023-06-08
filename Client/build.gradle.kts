plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "org.client"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(project(mapOf("path" to ":Server")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework:spring-web:6.0.8")
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("org.springframework:spring-core:6.0.8")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    implementation("com.liferay:com.fasterxml.jackson.databind:2.10.5.1.LIFERAY-PATCHED-1")


}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml", "javafx.web")
}

tasks.test {
    useJUnitPlatform()
}
