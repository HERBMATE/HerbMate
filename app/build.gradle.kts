plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlin-parcelize")
}

android {
    namespace = "com.android.herbmate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.android.herbmate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://herbmate-backend-7784081244.asia-southeast2.run.app/\"")
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
        buildConfig = true
    }


}

dependencies {

//    implementation (libs.androidx.credentials)
//    implementation (libs.androidx.credentials.play.services.auth)
//    implementation (libs.androidx.credentials.play.services.auth.v100alpha03)
//    implementation (libs.androidx.credentials.v100alpha03)
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")
//    implementation (libs.androidx.preference)
//    implementation (libs.androidx.datastore.preferences)
    implementation ("de.hdodenhof:circleimageview:3.1.0")
//
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
//    implementation(libs.googleid)
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.29")
    implementation ("androidx.camera:camera-core:1.4.0")
    implementation ("androidx.camera:camera-camera2:1.4.0")
    implementation ("androidx.camera:camera-lifecycle:1.4.0")
    implementation ("androidx.camera:camera-view:1.4.0")

    implementation(libs.androidx.datastore.preferences)

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(libs.ucrop)

    implementation(libs.circleimageview)
    implementation (libs.glide)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.activity)

    implementation(libs.androidx.datastore.preferences.core)

//    implementation(libs.androidx.datastore.core.android)
//    implementation(libs.androidx.datastore.preferences.core.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.generativeai)
}