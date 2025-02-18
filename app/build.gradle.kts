import java.util.Properties
import java.io.FileInputStream


plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose")

    id("com.google.firebase.crashlytics") version "3.0.3" apply false
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.sooj.today_music"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sooj.today_music"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "LAST_FM_API_KEY", "${localProperties["LAST_FM_API_KEY"]}")
//        buildConfigField("String", "LAST_FM_API_KEY", "\"${localProperties["LAST_FM_API_KEY"]?.replace("\"", "")}\"")

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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion // = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
//    implementation("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui:1.3.2") // Compose 의존성 추가
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.media3:media3-extractor:1.4.0")
    implementation("com.google.firebase:protolite-well-known-types:18.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // compose
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-tooling:1.6.8")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.23.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    // retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // okhttp3
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

    // kapt
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.6.0") // 0.5.0는 2.0 까지 밖에 안돼서 0.6.0으로 업데이트

    // dagger2
    implementation("com.google.dagger:dagger:2.55") // 핵심 라이브러리 | 객체 생성, 의존성 주입 등 기본적인 기능 제공
    implementation("com.google.dagger:dagger-android:2.55") // 안드환경에서 Dagger 쉽게 사용 도와주는 모듈
    implementation("com.google.dagger:dagger-android-support:2.55")
    kapt("com.google.dagger:dagger-compiler:2.55") // dagger 어노테이션 처리하여 필요한 코드 자동으로 생성하는 컴파일러
    kapt("com.google.dagger:dagger-android-processor:2.55")

    // Hilt dependencies
//    implementation("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-compiler:2.55")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.55")
//    kapt("com.google.dagger:dagger-compiler:2.55")
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Coil
    implementation("io.coil-kt:coil:1.2.0")
    implementation("io.coil-kt:coil-svg:1.2.0")
    implementation("io.coil-kt:coil-compose:2.5.0")

    //Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

    //room
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //lotte
    implementation("com.airbnb.android:lottie-compose:6.5.0")

    //paging
    val paging_version = "3.3.5"

    implementation("androidx.paging:paging-runtime:$paging_version")

    // alternatively - without Android dependencies for tests
    testImplementation("androidx.paging:paging-common:$paging_version")

    // optional - RxJava2 support
    implementation("androidx.paging:paging-rxjava2:$paging_version")

    // optional - RxJava3 support
    implementation("androidx.paging:paging-rxjava3:$paging_version")

    // optional - Guava ListenableFuture support
    implementation("androidx.paging:paging-guava:$paging_version")

    // optional - Jetpack Compose integration
    implementation("androidx.paging:paging-compose:3.3.2")

//    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.7")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))

    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
}
kapt {
    correctErrorTypes = true
}