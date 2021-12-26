import org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig

val microutils_logging_version = "1.11.2"

buildscript {
    repositories {
        maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
        maven("https://artifactory.daiv.org/artifactory/gradle-dev-local")
    }
    dependencies {
        classpath("org.daiv.dependency:DependencyHandling:0.1.0")
    }
}


plugins {
    kotlin("multiplatform") version "1.6.10"
    id("com.jfrog.artifactory") version "4.17.2"
    id("org.daiv.dependency.VersionsPlugin") version "0.1.3"
    `maven-publish`
}

val versions = org.daiv.dependency.DefaultDependencyBuilder(org.daiv.dependency.Versions.current())

group = "org.daiv.util"
version = versions.setVersion { kutil }

versionPlugin {
    versionPluginBuilder = org.daiv.dependency.Versions.versionPluginBuilder {
        versionMember = { kutil }
        resetVersion = { copy(kutil = it) }
    }
    setDepending(tasks)
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
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting{
            dependencies {
                api("io.github.microutils:kotlin-logging-common:$microutils_logging_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting{
            dependencies {
                api("io.github.microutils:kotlin-logging:$microutils_logging_version")
                api("ch.qos.logback:logback-classic:1.3.0-alpha4")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("io.mockk:mockk:1.10.0")
            }
        }
        val jsMain by getting{
            dependencies {
                api("io.github.microutils:kotlin-logging-js:$microutils_logging_version")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting{
            dependencies {
                api("io.github.microutils:kotlin-logging-linuxx64:$microutils_logging_version")
            }
        }
        val nativeTest by getting
    }
}

artifactory {
    setContextUrl("${project.findProperty("daiv_contextUrl")}")
    publish(delegateClosureOf<PublisherConfig> {
        repository(delegateClosureOf<groovy.lang.GroovyObject> {
            setProperty("repoKey", "gradle-dev-local")
            setProperty("username", project.findProperty("daiv_user"))
            setProperty("password", project.findProperty("daiv_password"))
            setProperty("maven", true)
        })
        defaults(delegateClosureOf<groovy.lang.GroovyObject> {
            invokeMethod("publications", arrayOf("jvm", "js", "kotlinMultiplatform", "metadata", "linuxX64"))
            setProperty("publishPom", true)
            setProperty("publishArtifacts", true)
        })
    })
}
