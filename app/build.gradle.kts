plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.gms.google.services)
}

android {
	namespace = "com.example.collegeerp"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.collegeerp"
		minSdk = 23
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
		debug {
			isMinifyEnabled = false
			isShrinkResources = false
		}
	}

	buildFeatures {
		compose = true
		buildConfig = true
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
	implementation(libs.hilt.navigation.compose)

	implementation(libs.room.runtime)
	implementation(libs.room.ktx)
	ksp(libs.room.compiler)

	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.auth)
	implementation(libs.firebase.firestore)
	implementation(libs.firebase.storage)

	implementation(libs.coil.compose)
	implementation(libs.mpandroidchart)

	implementation(libs.kotlinx.coroutines.core)
	implementation(libs.kotlinx.coroutines.android)

	// DataStore for theme preferences
	implementation("androidx.datastore:datastore-preferences:1.0.0")

	testImplementation(libs.junit)
	testImplementation(libs.mockk)
	testImplementation(libs.kotlinx.coroutines.test)

	androidTestImplementation(platform(libs.compose.bom))
	androidTestImplementation(libs.compose.ui.test.junit4)
	androidTestImplementation(libs.androidx.test.ext)
	androidTestImplementation(libs.espresso.core)

	debugImplementation(libs.compose.ui.tooling)
	debugImplementation(libs.compose.ui.test.manifest)
}
