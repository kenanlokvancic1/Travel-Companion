plugins {
	id ("com.android.application")
	id ("org.jetbrains.kotlin.android")
	id ("com.google.dagger.hilt.android")
	id ("io.realm.kotlin")
	id("com.google.devtools.ksp")


}

android {
	namespace = "com.productivity.myapplication"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.productivity.myapplication"
		minSdk = 28
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner =
			"androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
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
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.5"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {

	//Additional Kotlin Coroutine Dependencies
	implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core: 1.7.3")
	implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android: 1.7.3")


	//navigation
	implementation ("androidx.navigation:navigation-compose:2.5.3")

	//Compose ViewModel
	implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
	implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.5.1")


	//Constraint Layout
	implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")


	//Dagger-hilt
	implementation ("com.google.firebase:firebase-auth-ktx:21.1.0")
	implementation ("com.google.dagger:hilt-android:2.44")
    implementation("com.google.android.material:material:1.11.0")
    ksp ("com.google.dagger:dagger-compiler:2.48") // Dagger compiler
	ksp ("com.google.dagger:hilt-compiler:2.48")   // Hilt compiler

	implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

	//Retrofit 2
	implementation ("com.squareup.retrofit2:retrofit:2.9.0")
	implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

	//Realm Database
	implementation ("io.realm.kotlin:library-base:1.12.0")

	//Local Test Dependencies
	testImplementation ("junit:junit:4.13.2")
	testImplementation ("androidx.test:core:1.5.0")
	testImplementation ("org.mockito:mockito-core:5.1.0")
	testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")
	testImplementation ("io.mockk:mockk:1.13.4")

	//hilt testing
	androidTestImplementation ("com.google.dagger:hilt-android-testing:2.44")

	//navigation testing
	androidTestImplementation ("androidx.navigation:navigation-testing:2.5.3")

	//splash screen api
	implementation ("androidx.core:core-splashscreen:1.0.0")
val compose_version = "1.6.0"


	implementation ("androidx.core:core-ktx:1.8.0")
	implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
	implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
	implementation ("androidx.activity:activity-compose:1.5.1")
	implementation (platform("androidx.compose:compose-bom:2022.10.00"))
	implementation ("androidx.compose.ui:ui: $compose_version")
	implementation ("androidx.compose.ui:ui-graphics: $compose_version")
	implementation ("androidx.compose.ui:ui-tooling-preview")
	implementation ("androidx.compose.material3:material3:1.1.1")
	implementation ("androidx.compose.material:material:$compose_version")
	testImplementation ("junit:junit:4.13.2")
	androidTestImplementation ("androidx.test.ext:junit:1.1.5")
	androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
	androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
	androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
	debugImplementation ("androidx.compose.ui:ui-tooling")
	debugImplementation ("androidx.compose.ui:ui-test-manifest")
	implementation("androidx.compose.material:material-icons-extended")

	//Instrumentation Test
	//androidTestImplementation 'junit:junit:4.13.2'
	//androidTestImplementation 'org.jetbrains.kotlin:kotlinx-coroutines-test:1.7.0'
	//androidTestImplementation 'androidx.arch.core:core-testing:2.2.0'





}


