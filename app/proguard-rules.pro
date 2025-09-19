# Keep Hilt generated classes
-keep class dagger.hilt.internal.** { *; }
-keep class dagger.hilt.android.internal.managers.** { *; }
-dontwarn dagger.hilt.internal.**

# Firebase rules
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# Coroutines rules
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Keep domain models
-keep class com.example.collegeerp.domain.model.** { *; }

# Keep data entities
-keep class com.example.collegeerp.data.local.entity.** { *; }
