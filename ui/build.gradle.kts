plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.compose.compiler)
}

android {
	namespace = "com.example.collegeerp.ui"
	compileSdk = 34

	defaultConfig {
		minSdk = 23
	}

	buildFeatures { compose = true }
	composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlin { jvmToolchain(17) }
}

dependencies {
	implementation(project(":data"))
	implementation(project(":domain"))

	implementation(platform(libs.compose.bom))
	implementation(libs.compose.ui)
	implementation(libs.compose.ui.graphics)
	implementation(libs.compose.ui.tooling.preview)
	implementation(libs.compose.material3)
	implementation(libs.activity.compose)

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime)
	implementation(libs.androidx.lifecycle.viewmodel)

	implementation(libs.room.runtime)
	implementation(libs.room.ktx)
}
