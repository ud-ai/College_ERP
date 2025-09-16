plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
}

android {
	namespace = "com.example.collegeerp.ui"
	compileSdk = 34

	defaultConfig {
		minSdk = 21
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
	implementation(platform(libs.compose.bom))
	implementation(libs.compose.ui)
	implementation(libs.compose.ui.graphics)
	implementation(libs.compose.ui.tooling.preview)
	implementation(libs.compose.material3)
	implementation(libs.activity.compose)
}
