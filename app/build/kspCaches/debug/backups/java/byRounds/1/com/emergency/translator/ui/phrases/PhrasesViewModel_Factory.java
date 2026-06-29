package com.emergency.translator.ui.phrases;

import com.emergency.translator.data.repository.PhraseRepository;
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
public final class PhrasesViewModel_Factory implements Factory<PhrasesViewModel> {
  private final Provider<PhraseRepository> phraseRepositoryProvider;

  public PhrasesViewModel_Factory(Provider<PhraseRepository> phraseRepositoryProvider) {
    this.phraseRepositoryProvider = phraseRepositoryProvider;
  }

  @Override
  public PhrasesViewModel get() {
    return newInstance(phraseRepositoryProvider.get());
  }

  public static PhrasesViewModel_Factory create(
      Provider<PhraseRepository> phraseRepositoryProvider) {
    return new PhrasesViewModel_Factory(phraseRepositoryProvider);
  }

  public static PhrasesViewModel newInstance(PhraseRepository phraseRepository) {
    return new PhrasesViewModel(phraseRepository);
  }
}
