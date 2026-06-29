package com.emergency.translator.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile EmergencyPhraseDao _emergencyPhraseDao;

  private volatile TranslationEntryDao _translationEntryDao;

  private volatile AudioCacheDao _audioCacheDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `emergency_phrases` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `englishText` TEXT NOT NULL, `arabicText` TEXT NOT NULL, `persianText` TEXT NOT NULL, `turkishText` TEXT NOT NULL, `russianText` TEXT NOT NULL, `ukrainianText` TEXT NOT NULL, `category` TEXT NOT NULL, `isFavorite` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `translation_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `englishWord` TEXT NOT NULL, `arabicWord` TEXT NOT NULL, `persianWord` TEXT NOT NULL, `turkishWord` TEXT NOT NULL, `russianWord` TEXT NOT NULL, `ukrainianWord` TEXT NOT NULL, `category` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `audio_cache` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `phraseId` INTEGER NOT NULL, `languageCode` TEXT NOT NULL, `filePath` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'fb2af4210f941de1c293cbb0ab8d50f3')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `emergency_phrases`");
        db.execSQL("DROP TABLE IF EXISTS `translation_entries`");
        db.execSQL("DROP TABLE IF EXISTS `audio_cache`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsEmergencyPhrases = new HashMap<String, TableInfo.Column>(9);
        _columnsEmergencyPhrases.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyPhrases.put("englishText", new TableInfo.Column("englishText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyPhrases.put("arabicText", new TableInfo.Column("arabicText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyPhrases.put("persianText", new TableInfo.Column("persianText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyPhrases.put("turkishText", new TableInfo.Column("turkishText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyPhrases.put("russianText", new TableInfo.Column("russianText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyPhrases.put("ukrainianText", new TableInfo.Column("ukrainianText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyPhrases.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyPhrases.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmergencyPhrases = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEmergencyPhrases = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEmergencyPhrases = new TableInfo("emergency_phrases", _columnsEmergencyPhrases, _foreignKeysEmergencyPhrases, _indicesEmergencyPhrases);
        final TableInfo _existingEmergencyPhrases = TableInfo.read(db, "emergency_phrases");
        if (!_infoEmergencyPhrases.equals(_existingEmergencyPhrases)) {
          return new RoomOpenHelper.ValidationResult(false, "emergency_phrases(com.emergency.translator.data.local.entity.EmergencyPhraseEntity).\n"
                  + " Expected:\n" + _infoEmergencyPhrases + "\n"
                  + " Found:\n" + _existingEmergencyPhrases);
        }
        final HashMap<String, TableInfo.Column> _columnsTranslationEntries = new HashMap<String, TableInfo.Column>(8);
        _columnsTranslationEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationEntries.put("englishWord", new TableInfo.Column("englishWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationEntries.put("arabicWord", new TableInfo.Column("arabicWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationEntries.put("persianWord", new TableInfo.Column("persianWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationEntries.put("turkishWord", new TableInfo.Column("turkishWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationEntries.put("russianWord", new TableInfo.Column("russianWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationEntries.put("ukrainianWord", new TableInfo.Column("ukrainianWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationEntries.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTranslationEntries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTranslationEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTranslationEntries = new TableInfo("translation_entries", _columnsTranslationEntries, _foreignKeysTranslationEntries, _indicesTranslationEntries);
        final TableInfo _existingTranslationEntries = TableInfo.read(db, "translation_entries");
        if (!_infoTranslationEntries.equals(_existingTranslationEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "translation_entries(com.emergency.translator.data.local.entity.TranslationEntryEntity).\n"
                  + " Expected:\n" + _infoTranslationEntries + "\n"
                  + " Found:\n" + _existingTranslationEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsAudioCache = new HashMap<String, TableInfo.Column>(4);
        _columnsAudioCache.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAudioCache.put("phraseId", new TableInfo.Column("phraseId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAudioCache.put("languageCode", new TableInfo.Column("languageCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAudioCache.put("filePath", new TableInfo.Column("filePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAudioCache = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAudioCache = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAudioCache = new TableInfo("audio_cache", _columnsAudioCache, _foreignKeysAudioCache, _indicesAudioCache);
        final TableInfo _existingAudioCache = TableInfo.read(db, "audio_cache");
        if (!_infoAudioCache.equals(_existingAudioCache)) {
          return new RoomOpenHelper.ValidationResult(false, "audio_cache(com.emergency.translator.data.local.entity.AudioCacheEntity).\n"
                  + " Expected:\n" + _infoAudioCache + "\n"
                  + " Found:\n" + _existingAudioCache);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "fb2af4210f941de1c293cbb0ab8d50f3", "bd11da0981d07dac81511f2a0faab224");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "emergency_phrases","translation_entries","audio_cache");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `emergency_phrases`");
      _db.execSQL("DELETE FROM `translation_entries`");
      _db.execSQL("DELETE FROM `audio_cache`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(EmergencyPhraseDao.class, EmergencyPhraseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TranslationEntryDao.class, TranslationEntryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AudioCacheDao.class, AudioCacheDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public EmergencyPhraseDao emergencyPhraseDao() {
    if (_emergencyPhraseDao != null) {
      return _emergencyPhraseDao;
    } else {
      synchronized(this) {
        if(_emergencyPhraseDao == null) {
          _emergencyPhraseDao = new EmergencyPhraseDao_Impl(this);
        }
        return _emergencyPhraseDao;
      }
    }
  }

  @Override
  public TranslationEntryDao translationEntryDao() {
    if (_translationEntryDao != null) {
      return _translationEntryDao;
    } else {
      synchronized(this) {
        if(_translationEntryDao == null) {
          _translationEntryDao = new TranslationEntryDao_Impl(this);
        }
        return _translationEntryDao;
      }
    }
  }

  @Override
  public AudioCacheDao audioCacheDao() {
    if (_audioCacheDao != null) {
      return _audioCacheDao;
    } else {
      synchronized(this) {
        if(_audioCacheDao == null) {
          _audioCacheDao = new AudioCacheDao_Impl(this);
        }
        return _audioCacheDao;
      }
    }
  }
}
