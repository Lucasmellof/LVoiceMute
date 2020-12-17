plugins {
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "wtf.lucasmellof.voicemute"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(files("./libs/spigot.jar"))
    compileOnly(files("./libs/voicechat.jar"))
    implementation("club.minnced:discord-webhooks:0.5.3")
}
