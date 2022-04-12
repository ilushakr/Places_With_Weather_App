plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("android")
    kotlin("kapt")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSDK
        targetSdk = Config.targetSDK
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(project(":app:domain"))
    implementation(project(":app:data"))

    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.material)

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.livedata)
    implementation(Dependencies.Compose.viewModelCompose)
    implementation(Dependencies.Compose.activityCompose)
    implementation(Dependencies.Compose.navigationCompose)
    implementation(Dependencies.Compose.constraintLayout)
    debugImplementation(Dependencies.Compose.toolingTest)
    androidTestImplementation(Dependencies.Compose.uiTest)
    // Accompanist
    implementation(Dependencies.Compose.accompanistSystemController)
    implementation(Dependencies.Compose.accompanistPermissions)

    // Lifecycle
    implementation(Dependencies.Lifecycle.lifecycleKtx)

    // Serialization
    implementation(Dependencies.Serialization.gson)

    // Dagger
    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.compiler)
//    implementation(Dependencies.Dagger.support)
//    implementation(Dependencies.Dagger.android)
//    kapt(Dependencies.Dagger.processor)

}