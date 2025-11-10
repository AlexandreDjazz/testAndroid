# Add project specific ProGuard rules here.
-keep class com.bankingapp.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
