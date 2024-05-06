// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("com.google.gms:google-services:4.4.1")
    }
    repositories {
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}

//buildscript {
//    dependencies {
////        classpath ("com.google.gms:google-services:4.4.0")
//    }
//
//    repositories {
//        jcenter()
//        mavenCentral()
//        maven { url ; "https://jitpack.io" }
//    }
//
//}

