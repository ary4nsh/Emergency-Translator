package com.emergency.translator.data.repository;

import android.content.Context;
import com.emergency.translator.data.local.database.AudioCacheDao;
import com.emergency.translator.data.local.database.EmergencyPhraseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AudioRepository_Factory implements Factory<AudioRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<AudioCacheDao> audioCacheDaoProvider;

  private final Provider<EmergencyPhraseDao> phraseDaoProvider;

  public AudioRepository_Factory(Provider<Context> contextProvider,
      Provider<AudioCacheDao> audioCacheDaoProvider,
      Provider<EmergencyPhraseDao> phraseDaoProvider) {
    this.contextProvider = contextProvider;
    this.audioCacheDaoProvider = audioCacheDaoProvider;
    this.phraseDaoProvider = phraseDaoProvider;
  }

  @Override
  public AudioRepository get() {
    return newInstance(contextProvider.get(), audioCacheDaoProvider.get(), phraseDaoProvider.get());
  }

  public static AudioRepository_Factory create(Provider<Context> contextProvider,
      Provider<AudioCacheDao> audioCacheDaoProvider,
      Provider<EmergencyPhraseDao> phraseDaoProvider) {
    return new AudioRepository_Factory(contextProvider, audioCacheDaoProvider, phraseDaoProvider);
  }

  public static AudioRepository newInstance(Context context, AudioCacheDao audioCacheDao,
      EmergencyPhraseDao phraseDao) {
    return new AudioRepository(context, audioCacheDao, phraseDao);
  }
}
