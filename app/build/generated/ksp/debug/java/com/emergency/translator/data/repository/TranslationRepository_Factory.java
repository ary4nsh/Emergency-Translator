package com.emergency.translator.data.repository;

import com.emergency.translator.data.local.database.EmergencyPhraseDao;
import com.emergency.translator.data.local.database.TranslationEntryDao;
import com.emergency.translator.data.translation.MLKitTranslationSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class TranslationRepository_Factory implements Factory<TranslationRepository> {
  private final Provider<MLKitTranslationSource> mlKitProvider;

  private final Provider<EmergencyPhraseDao> phraseDaoProvider;

  private final Provider<TranslationEntryDao> entryDaoProvider;

  public TranslationRepository_Factory(Provider<MLKitTranslationSource> mlKitProvider,
      Provider<EmergencyPhraseDao> phraseDaoProvider,
      Provider<TranslationEntryDao> entryDaoProvider) {
    this.mlKitProvider = mlKitProvider;
    this.phraseDaoProvider = phraseDaoProvider;
    this.entryDaoProvider = entryDaoProvider;
  }

  @Override
  public TranslationRepository get() {
    return newInstance(mlKitProvider.get(), phraseDaoProvider.get(), entryDaoProvider.get());
  }

  public static TranslationRepository_Factory create(Provider<MLKitTranslationSource> mlKitProvider,
      Provider<EmergencyPhraseDao> phraseDaoProvider,
      Provider<TranslationEntryDao> entryDaoProvider) {
    return new TranslationRepository_Factory(mlKitProvider, phraseDaoProvider, entryDaoProvider);
  }

  public static TranslationRepository newInstance(MLKitTranslationSource mlKit,
      EmergencyPhraseDao phraseDao, TranslationEntryDao entryDao) {
    return new TranslationRepository(mlKit, phraseDao, entryDao);
  }
}
