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

    viewBinding {
        enable = true
    }
}

dependencies {

    //Moxy
    implementation (libs.moxy.community.moxy.material)
    implementation (libs.com.github.moxy.community.moxy)
    implementation (libs.moxy.androidx)
    kapt (libs.moxy.compiler)

    val daggerVersion = "2.55"

    //Dagger
    implementation ("com.google.dagger:dagger:$daggerVersion")
    kapt ("com.google.dagger:dagger-compiler:$daggerVersion")
    implementation ("com.google.dagger:dagger-android:$daggerVersion")
    implementation ("com.google.dagger:dagger-android-support:$daggerVersion")
    kapt ("com.google.dagger:dagger-android-processor:$daggerVersion")

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common)
    kapt(libs.androidx.room.compiler)
    implementation(libs.room.ktx)

    //RxJava
    implementation (libs.io.reactivex.rxjava3.rxjava)
    implementation (libs.rxandroid)
    implementation (libs.adapter.rxjava3)



    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.work.runtime.ktx)



    implementation(libs.retrofit2.converter.gson)
    implementation (libs.retrofit)
    implementation(libs.okhttp)
    implementation (libs.glide)
    kapt (libs.compiler)
    implementation(libs.retrofit)
    implementation (libs.androidx.core.splashscreen)
    implementation (libs.lottie)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
