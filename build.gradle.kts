plugins {
    id("java")
}

group = "org.n0throw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("jcifs:jcifs:1.3.17")

}

tasks.test {
    useJUnitPlatform()
    useJUnit()
}