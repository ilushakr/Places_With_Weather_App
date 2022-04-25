plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("kapt")
}

repositories {
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

        val weatherKey = System.getenv("ENV_WEATHER_KEY")
        val googleMapsKey = System.getenv("ENV_GOOGLE_MAPS_KEY")
        forEach { buildType ->
            buildType.buildConfigField("String", "WEATHER_KEY", weatherKey)
            buildType.resValue("string", "google_maps_api_key", googleMapsKey)
        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    kapt {
        correctErrorTypes = true
    }

}

dependencies {

    implementation(project(path = ":app:domain"))

    //Serialization
    implementation(Dependencies.Serialization.serialization_json)

    //Maps
    api(Dependencies.Maps.compose)
    api(Dependencies.Maps.services)

    // Room
    api(Dependencies.Room.runtime)
    api(Dependencies.Room.coroutines)
    kapt(Dependencies.Room.compiler)

    // Dagger
    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.compiler)
    //    implementation(Dependencies.Dagger.support)
//    implementation(Dependencies.Dagger.android)
//    kapt(Dependencies.Dagger.processor)
}