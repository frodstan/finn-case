import com.example.finncase.ConfigurationData
import com.example.finncase.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.repository"
    compileSdk = ConfigurationData.COMPILE_SDK

    defaultConfig {
        minSdk = ConfigurationData.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}")

    implementation("com.squareup.retrofit2:retrofit:${Versions.RETROFIT}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.MOSHI_CONVERTER_RETROFIT}")
    implementation("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3}")

    implementation("androidx.room:room-runtime:${Versions.ROOM}")
    implementation("androidx.room:room-ktx:${Versions.ROOM}")
    ksp("androidx.room:room-compiler:${Versions.ROOM}")

    implementation("androidx.datastore:datastore-preferences:${Versions.DATASTORE}")
}