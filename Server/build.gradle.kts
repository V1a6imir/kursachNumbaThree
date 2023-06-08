plugins {
    id("java-library")
    id("maven-publish")
}

group = "org.server"
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
        implementation("org.projectlombok:lombok:1.18.22")
        annotationProcessor("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok:1.18.22")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.0")
        implementation("org.springframework.boot:spring-boot:3.1.0")
        implementation("org.springframework.boot:spring-boot-autoconfigure:3.1.0")
        implementation("org.springframework.boot:spring-boot-starter:3.1.0")
        implementation("com.mysql:mysql-connector-j:8.0.33")
        implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
        implementation("org.hibernate:hibernate-core:6.2.4.Final")
        implementation("org.jsoup:jsoup:1.16.1")
        implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
        implementation("org.hibernate:hibernate-core:6.2.4.Final")


}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("file:/Users/vladimirmonastirsky/.m2/repository")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
