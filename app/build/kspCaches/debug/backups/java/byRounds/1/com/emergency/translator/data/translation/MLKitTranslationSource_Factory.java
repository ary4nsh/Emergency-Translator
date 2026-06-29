package com.emergency.translator.data.translation;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MLKitTranslationSource_Factory implements Factory<MLKitTranslationSource> {
  @Override
  public MLKitTranslationSource get() {
    return newInstance();
  }

  public static MLKitTranslationSource_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static MLKitTranslationSource newInstance() {
    return new MLKitTranslationSource();
  }

  private static final class InstanceHolder {
    private static final MLKitTranslationSource_Factory INSTANCE = new MLKitTranslationSource_Factory();
  }
}
