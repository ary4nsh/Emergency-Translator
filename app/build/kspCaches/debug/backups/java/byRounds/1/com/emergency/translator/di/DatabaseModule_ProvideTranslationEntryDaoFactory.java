package com.emergency.translator.di;

import com.emergency.translator.data.local.database.AppDatabase;
import com.emergency.translator.data.local.database.TranslationEntryDao;
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
public final class DatabaseModule_ProvideTranslationEntryDaoFactory implements Factory<TranslationEntryDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideTranslationEntryDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TranslationEntryDao get() {
    return provideTranslationEntryDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTranslationEntryDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTranslationEntryDaoFactory(databaseProvider);
  }

  public static TranslationEntryDao provideTranslationEntryDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTranslationEntryDao(database));
  }
}
