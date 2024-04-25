plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.cctv.heygongc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cctv.heygongc"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.1.1"
//    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    
    // 뷰바인딩
    buildFeatures { 
        viewBinding = true
    }

    // 데이터바인딩
    buildFeatures {
        dataBinding = true
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("androidx.core:core-ktx:+")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

//    implementation("androidx.compose.material3:material3:1.1.2")
//    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")

    implementation("com.google.android.material:material:1.3.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.tbuonomo:dotsindicator:5.0")

    // Google Play services
    implementation("com.google.gms:google-services:4.3.15")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
//    implementation("com.google.firebase:firebase-auth:22.0.0")
//    implementation("com.google.firebase:firebase-bom:32.0.0")

    // Retrofit 라이브러리
    implementation("com.squareup.retrofit2:retrofit:2.6.4")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Gson 변환기 라이브러리
    implementation("com.squareup.retrofit2:converter-gson:2.6.4")

    // Scalars 변환기 라이브러리
    implementation("com.squareup.retrofit2:converter-scalars:2.6.4")

    // by viewModels() 사용
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.2")

    // 차트
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")

    //Datastore
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //Fcm
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging:23.4.1")

    //QR
    implementation("com.journeyapps:zxing-android-embedded:4.2.0")

    //TedPermission
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")
    implementation("io.github.ParkSangGwon:tedpermission-coroutine:3.3.0")
}

kapt {
    correctErrorTypes = true
}