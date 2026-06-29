package com.emergency.translator.ui.phrases;

import com.emergency.translator.data.repository.AudioRepository;
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
public final class PhrasesFragment_MembersInjector implements MembersInjector<PhrasesFragment> {
  private final Provider<AudioRepository> audioRepositoryProvider;

  public PhrasesFragment_MembersInjector(Provider<AudioRepository> audioRepositoryProvider) {
    this.audioRepositoryProvider = audioRepositoryProvider;
  }

  public static MembersInjector<PhrasesFragment> create(
      Provider<AudioRepository> audioRepositoryProvider) {
    return new PhrasesFragment_MembersInjector(audioRepositoryProvider);
  }

  @Override
  public void injectMembers(PhrasesFragment instance) {
    injectAudioRepository(instance, audioRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.emergency.translator.ui.phrases.PhrasesFragment.audioRepository")
  public static void injectAudioRepository(PhrasesFragment instance,
      AudioRepository audioRepository) {
    instance.audioRepository = audioRepository;
  }
}
