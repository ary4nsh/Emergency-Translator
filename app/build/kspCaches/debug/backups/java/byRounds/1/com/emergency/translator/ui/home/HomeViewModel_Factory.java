package com.emergency.translator.ui.home;

import com.emergency.translator.data.repository.PhraseRepository;
import com.emergency.translator.data.repository.TranslationRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<TranslationRepository> translationRepositoryProvider;

  private final Provider<PhraseRepository> phraseRepositoryProvider;

  public HomeViewModel_Factory(Provider<TranslationRepository> translationRepositoryProvider,
      Provider<PhraseRepository> phraseRepositoryProvider) {
    this.translationRepositoryProvider = translationRepositoryProvider;
    this.phraseRepositoryProvider = phraseRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(translationRepositoryProvider.get(), phraseRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<TranslationRepository> translationRepositoryProvider,
      Provider<PhraseRepository> phraseRepositoryProvider) {
    return new HomeViewModel_Factory(translationRepositoryProvider, phraseRepositoryProvider);
  }

  public static HomeViewModel newInstance(TranslationRepository translationRepository,
      PhraseRepository phraseRepository) {
    return new HomeViewModel(translationRepository, phraseRepository);
  }
}
