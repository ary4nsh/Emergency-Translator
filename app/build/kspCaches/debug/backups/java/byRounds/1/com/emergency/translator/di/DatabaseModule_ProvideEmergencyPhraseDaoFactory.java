package com.emergency.translator.di;

import com.emergency.translator.data.local.database.AppDatabase;
import com.emergency.translator.data.local.database.EmergencyPhraseDao;
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
public final class DatabaseModule_ProvideEmergencyPhraseDaoFactory implements Factory<EmergencyPhraseDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideEmergencyPhraseDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public EmergencyPhraseDao get() {
    return provideEmergencyPhraseDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideEmergencyPhraseDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideEmergencyPhraseDaoFactory(databaseProvider);
  }

  public static EmergencyPhraseDao provideEmergencyPhraseDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideEmergencyPhraseDao(database));
  }
}
