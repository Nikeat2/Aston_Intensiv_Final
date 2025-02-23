plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.astonfinalproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.astonfinalproject"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.swiperefreshlayout)
    implementation (libs.com.github.moxy.community.moxy)
    implementation (libs.moxy.androidx)
    implementation (libs.moxy.community.moxy.material)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    kapt (libs.moxy.compiler)
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation (libs.adapter.rxjava3)
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation (libs.retrofit)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation (libs.glide)
    kapt (libs.compiler)
    implementation(libs.retrofit)
    implementation (libs.androidx.core.splashscreen)
    implementation ("com.airbnb.android:lottie:6.0.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}