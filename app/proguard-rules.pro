######## Compose ########
-keep class androidx.compose.** { *; }
-keep class kotlin.Metadata { *; }

########## Room ##########
# Keep Room entities, DAOs, and database classes
#-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.* class * { *; }
-keepclasseswithmembers class * {
    @androidx.room.* <fields>;
    @androidx.room.* <methods>;
}

######## Navigation ########
-keep class androidx.navigation.** { *; }

######## Koin ########
-keep class org.koin.** { *; }

######## Kotlin Serialization ########
-keepclassmembers class ** {
    @kotlinx.serialization.* <fields>;
}
-keepattributes *Annotation*

######## Remove logs ########
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}