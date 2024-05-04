pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
    }
//    buildscript {
//        dependencies {
//            classpath("com.android.tools:r8:X.Y.Z")
//            classpath("com.google.guava:guava:30.1.1-jre")  // <-- THIS IS REQUIRED UNTIL R8 3.2.4-dev
//        }
//    }

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://jitpack.io")
    }
}

rootProject.name = "HeyGongC"
include(":app")
 