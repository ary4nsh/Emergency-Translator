# Add project specific ProGuard rules here.
# Keep Retrofit models
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.emergency.translator.data.remote.dto.** { *; }
# Keep Room entity classes
-keep class com.emergency.translator.data.local.entity.** { *; }
