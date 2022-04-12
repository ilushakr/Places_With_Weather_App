object Dependencies {

    object Logger {
        private const val timber_version = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:${timber_version}"
    }

    object Ktor {
        private const val ktor_version = "1.6.8"

        const val core = "io.ktor:ktor-client-core:$ktor_version"
        const val android = "io.ktor:ktor-client-android:$ktor_version"
        const val serialization = "io.ktor:ktor-client-serialization:$ktor_version"
        const val logging = "io.ktor:ktor-client-logging:$ktor_version"
    }

    object Dagger {
        private const val dagger_version = "2.41"
        const val dagger = "com.google.dagger:dagger:$dagger_version"
        const val android = "com.google.dagger:dagger-android:$dagger_version"
        const val support = "com.google.dagger:dagger-android-support:$dagger_version"
        const val compiler = "com.google.dagger:dagger-compiler:$dagger_version"
        const val processor = "com.google.dagger:dagger-android-processor:$dagger_version"
    }

    object Serialization {
        private const val serialization_version = "1.3.2"
        const val serialization_json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version"

        private const val gsonVersion = "2.9.0"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object Maps {
        private const val maps_compose_version = "1.0.0"
        private const val play_services_version = "18.0.2"
        const val compose = "com.google.maps.android:maps-compose:$maps_compose_version"
        const val services = "com.google.android.gms:play-services-maps:$play_services_version"
    }

    object Compose {
        const val version = "1.1.1"
        const val ui = "androidx.compose.ui:ui:$version"
        const val material = "androidx.compose.material:material:$version"
        const val tooling = "androidx.compose.ui:ui-tooling-preview:$version"
        const val livedata = "androidx.compose.runtime:runtime-livedata:$version"
        const val toolingTest = "androidx.compose.ui:ui-tooling:$version"
        const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"
        const val activityCompose = "androidx.activity:activity-compose:1.3.1"
        const val navigationCompose = "androidx.navigation:navigation-compose:2.4.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0"

        // accompanist
        const val accompanistSystemController = "com.google.accompanist:accompanist-systemuicontroller:0.18.0"
        const val accompanistPermissions = "com.google.accompanist:accompanist-permissions:0.24.5-alpha"
    }

    object Lifecycle {
        const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
    }

    object Room {
        private const val version = "2.4.2"
        const val runtime = "androidx.room:room-runtime:$version"
        const val compiler = "androidx.room:room-compiler:$version"
        const val coroutines = "androidx.room:room-ktx:$version"
    }

    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.0"
        const val material = "com.google.android.material:material:1.4.0"
    }

    object Test {
        const val jUnit = "junit:junit:4.+"
        const val androidJUnit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}