plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

// 버전: CI에서 -PversionName=1.2.3 -PversionCode=42 로 주입. 미지정 시 기본값.
val appVersionName = (findProperty("versionName") as String?)?.takeIf { it.isNotBlank() } ?: "0.0.0-dev"
val appVersionCode = (findProperty("versionCode") as String?)?.toIntOrNull() ?: 1

// 서명: 환경변수 4개가 모두 있을 때만 release 서명. 없으면 무서명 release 빌드.
val keystorePath: String? = System.getenv("KEYSTORE_PATH")?.takeIf { it.isNotBlank() }
val keystorePassword: String? = System.getenv("KEYSTORE_PASSWORD")?.takeIf { it.isNotBlank() }
val signingKeyAlias: String? = System.getenv("KEY_ALIAS")?.takeIf { it.isNotBlank() }
val signingKeyPassword: String? = System.getenv("KEY_PASSWORD")?.takeIf { it.isNotBlank() }
val hasReleaseSigning = listOf(keystorePath, keystorePassword, signingKeyAlias, signingKeyPassword).all { it != null }

android {
    namespace = "io.github.ares_wjd.nihongo"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.ares_wjd.nihongo"
        minSdk = 26
        targetSdk = 35
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        if (hasReleaseSigning) {
            create("release") {
                storeFile = file(keystorePath!!)
                storePassword = keystorePassword
                keyAlias = signingKeyAlias
                keyPassword = signingKeyPassword
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            if (hasReleaseSigning) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    testImplementation(libs.junit)
}
