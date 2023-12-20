import com.example.finncase.ConfigurationData
import com.example.finncase.Versions

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.finncase"
    compileSdk = ConfigurationData.COMPILE_SDK

    defaultConfig {
        applicationId = ConfigurationData.APPLICATION_ID
        minSdk = ConfigurationData.MIN_SDK
        targetSdk = ConfigurationData.TARGET_SDK
        versionCode = ConfigurationData.VERSION_CODE
        versionName = ConfigurationData.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:ads"))
    implementation(project(":library:style"))
    implementation(project(":library:repository"))

    implementation("androidx.core:core-ktx:${Versions.CORE_KTX}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}")

    implementation("io.insert-koin:koin-android:${Versions.KOIN}")

    implementation("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}")

    implementation(platform("androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}