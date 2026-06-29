package com.emergency.translator;

import com.emergency.translator.data.repository.AudioRepository;
import com.emergency.translator.data.repository.PhraseRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class EmergencyTranslatorApp_MembersInjector implements MembersInjector<EmergencyTranslatorApp> {
  private final Provider<PhraseRepository> phraseRepositoryProvider;

  private final Provider<AudioRepository> audioRepositoryProvider;

  public EmergencyTranslatorApp_MembersInjector(Provider<PhraseRepository> phraseRepositoryProvider,
      Provider<AudioRepository> audioRepositoryProvider) {
    this.phraseRepositoryProvider = phraseRepositoryProvider;
    this.audioRepositoryProvider = audioRepositoryProvider;
  }

  public static MembersInjector<EmergencyTranslatorApp> create(
      Provider<PhraseRepository> phraseRepositoryProvider,
      Provider<AudioRepository> audioRepositoryProvider) {
    return new EmergencyTranslatorApp_MembersInjector(phraseRepositoryProvider, audioRepositoryProvider);
  }

  @Override
  public void injectMembers(EmergencyTranslatorApp instance) {
    injectPhraseRepository(instance, phraseRepositoryProvider.get());
    injectAudioRepository(instance, audioRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.emergency.translator.EmergencyTranslatorApp.phraseRepository")
  public static void injectPhraseRepository(EmergencyTranslatorApp instance,
      PhraseRepository phraseRepository) {
    instance.phraseRepository = phraseRepository;
  }

  @InjectedFieldSignature("com.emergency.translator.EmergencyTranslatorApp.audioRepository")
  public static void injectAudioRepository(EmergencyTranslatorApp instance,
      AudioRepository audioRepository) {
    instance.audioRepository = audioRepository;
  }
}
