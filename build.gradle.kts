import org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig

val microutils_logging_version = "2.1.23"

buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
    }
    dependencies {
        classpath("org.daiv.dependency:DependencyHandling:0.1.44")
    }
}


plugins {
    kotlin("multiplatform") version "1.6.10"
    id("com.jfrog.artifactory") version "4.17.2"
    id("org.daiv.dependency.VersionsPlugin") version "0.1.4"
    `maven-publish`
//    id("maven")
    id("signing")
}

val versions = org.daiv.dependency.DefaultDependencyBuilder(org.daiv.dependency.Versions.current())

group = "org.daiv.util"
version = versions.setVersion { kutil }

versionPlugin {
    versionPluginBuilder = org.daiv.dependency.Versions.versionPluginBuilder {
        versionMember = { kutil }
        resetVersion = { copy(kutil = it) }
        publishTaskName = "publish"
    }
    setDepending(tasks, "publish")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
//    val hostOs = System.getProperty("os.name")
//    val isMingwX64 = hostOs.startsWith("Windows")
//    val nativeTarget = when {
//        hostOs == "Mac OS X" -> macosX64("native")
//        hostOs == "Linux" -> linuxX64("native")
//        isMingwX64 -> mingwX64("native")
//        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("io.github.microutils:kotlin-logging:$microutils_logging_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
//                api("io.github.microutils:kotlin-logging:$microutils_logging_version")
                api(versions.logbackClassic())
                api(versions.logbackCore())
//                api("ch.qos.logback:logback-core:1.4.5")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("io.mockk:mockk:1.10.0")
            }
        }
        val jsMain by getting {
            dependencies {
//                api("io.github.microutils:kotlin-logging-js:$microutils_logging_version")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
//        val nativeMain by getting{
//            dependencies {
//                api("io.github.microutils:kotlin-logging-linuxx64:$microutils_logging_version")
//            }
//        }
//        val nativeTest by getting
    }
}
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}
signing {
    sign(publishing.publications)
}

publishing {
    publications.withType<MavenPublication> {
        artifact(javadocJar.get())
        pom {
            packaging = "jar"
            name.set("kutil")
            description.set("kotlin utils for org.daiv projects")
            url.set("https://github.com/henry1986/kutil")
            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            issueManagement {
                system.set("Github")
                url.set("https://github.com/henry1986/kutil/issues")
            }
            scm {
                connection.set("scm:git:https://github.com/henry1986/kutil.git")
                developerConnection.set("scm:git:https://github.com/henry1986/kutil.git")
                url.set("https://github.com/henry1986/kutil")
            }
            developers {
                developer {
                    id.set("henry86")
                    name.set("Martin Heinrich")
                    email.set("martin.heinrich.dresden@gmx.de")
                }
            }
        }
    }
    repositories {
        maven {
            name = "sonatypeRepository"
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)
        }
    }
}
