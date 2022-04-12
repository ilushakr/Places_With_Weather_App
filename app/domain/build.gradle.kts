plugins {
    id("com.android.library")
    kotlin("android")
}

repositories{
    google()
    mavenCentral()
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSDK
        targetSdk = Config.targetSDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("proguard-rules.pro")
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

    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.Test.jUnit)
    implementation(Dependencies.Test.androidJUnit)
    implementation(Dependencies.Test.espresso)

    //Ktor
    api(Dependencies.Ktor.core)
    api(Dependencies.Ktor.android)
    api(Dependencies.Ktor.serialization)
    api(Dependencies.Ktor.logging)

    //Timber
    api(Dependencies.Logger.timber)
}