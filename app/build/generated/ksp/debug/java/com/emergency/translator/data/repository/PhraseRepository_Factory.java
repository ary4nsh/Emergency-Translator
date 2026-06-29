package com.emergency.translator.data.repository;

import com.emergency.translator.data.local.database.AppDatabase;
import com.emergency.translator.data.local.database.TranslationEntryDao;
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
public final class PhraseRepository_Factory implements Factory<PhraseRepository> {
  private final Provider<AppDatabase> databaseProvider;

  private final Provider<TranslationEntryDao> translationEntryDaoProvider;

  public PhraseRepository_Factory(Provider<AppDatabase> databaseProvider,
      Provider<TranslationEntryDao> translationEntryDaoProvider) {
    this.databaseProvider = databaseProvider;
    this.translationEntryDaoProvider = translationEntryDaoProvider;
  }

  @Override
  public PhraseRepository get() {
    return newInstance(databaseProvider.get(), translationEntryDaoProvider.get());
  }

  public static PhraseRepository_Factory create(Provider<AppDatabase> databaseProvider,
      Provider<TranslationEntryDao> translationEntryDaoProvider) {
    return new PhraseRepository_Factory(databaseProvider, translationEntryDaoProvider);
  }

  public static PhraseRepository newInstance(AppDatabase database,
      TranslationEntryDao translationEntryDao) {
    return new PhraseRepository(database, translationEntryDao);
  }
}
