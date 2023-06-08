plugins {
    id("java")
}

group = "kur3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.vladsch.javafx-webview-debugger:javafx-webview-debugger:0.8.6")
    implementation("com.vladsch.javafx-webview-debugger:javafx-webview-debugger:0.8.6")
    implementation("com.github.javakam:webview:5.0.0")
    implementation("com.vladsch.javafx-webview-debugger:javafx-webview-debugger:0.7.6")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}