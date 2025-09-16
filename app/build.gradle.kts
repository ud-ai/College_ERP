plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
}

android {
	namespace = "com.example.collegeerp"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.collegeerp"
		minSdk = 21
		targetSdk = 34
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

	buildFeatures {
		compose = true
	}

	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	kotlin {
		jvmToolchain(17)
	}
}

dependencies {
	implementation(project(":domain"))
	implementation(project(":data"))
	implementation(project(":ui"))

	implementation(platform(libs.compose.bom))
	implementation(libs.compose.ui)
	implementation(libs.compose.ui.graphics)
	implementation(libs.compose.ui.tooling.preview)
	implementation(libs.compose.material3)
	implementation(libs.activity.compose)
	implementation(libs.nav.compose)

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime)
	implementation(libs.androidx.lifecycle.viewmodel)

	implementation(libs.hilt.android)
	ksp(libs.hilt.compiler)

	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.auth)
	implementation(libs.firebase.firestore)
	implementation(libs.firebase.storage)

	implementation(libs.coil.compose)

	testImplementation(libs.junit)
	testImplementation(libs.mockk)

	androidTestImplementation(platform(libs.compose.bom))
	androidTestImplementation(libs.compose.ui.test.junit4)
	androidTestImplementation(libs.androidx.test.ext)
	androidTestImplementation(libs.espresso.core)

	debugImplementation(libs.compose.ui.tooling)
	debugImplementation(libs.compose.ui.test.manifest)
}

apply(plugin = "com.google.gms.google-services")
