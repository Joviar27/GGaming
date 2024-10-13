import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // Keep this as is
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField ("String", "BASE_URL", "\"${getProperty("local.properties", "BASE_URL")}\"")
        buildConfigField ("String", "API_KEY", "\"${getProperty("local.properties", "API_KEY")}\"")
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
    buildFeatures{
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //UI
    api(libs.glide.compose)

    //Paging
    api(libs.paging.runtime)
    api(libs.paging.compose)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    //Dagger-hilt
    api(libs.hilt)
    kapt(libs.hilt.compiler)
}

kapt {
    correctErrorTypes = true
}

fun getProperty(fileName: String, propName: String): String {
    val propsFile = rootProject.file(fileName)
    if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        if (props[propName] != null) {
            try {
                return props[propName] as String
            }catch (e: Exception){
                throw GradleException("Error returning properties: ${e.message}")
            }
        } else {
            throw GradleException("No such property $propName in file $fileName")
        }
    } else {
        throw GradleException("$fileName does not exist!")
    }
}