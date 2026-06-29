package com.emergency.translator.di;

import com.emergency.translator.data.local.database.AppDatabase;
import com.emergency.translator.data.local.database.AudioCacheDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideAudioCacheDaoFactory implements Factory<AudioCacheDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideAudioCacheDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AudioCacheDao get() {
    return provideAudioCacheDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideAudioCacheDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideAudioCacheDaoFactory(databaseProvider);
  }

  public static AudioCacheDao provideAudioCacheDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAudioCacheDao(database));
  }
}
