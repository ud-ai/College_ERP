plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
}

android {
	namespace = "com.example.collegeerp.data"
	compileSdk = 34

	defaultConfig {
		minSdk = 21
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

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime)

	implementation(libs.room.runtime)
	implementation(libs.room.ktx)
	ksp(libs.room.compiler)

	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.auth)
	implementation(libs.firebase.firestore)
	implementation(libs.firebase.storage)

	implementation(libs.hilt.android)
	ksp(libs.hilt.compiler)

	implementation(libs.kotlinx.serialization.json)

	implementation(libs.kotlinx.coroutines.core)
	implementation(libs.kotlinx.coroutines.android)

	testImplementation(libs.junit)
}
