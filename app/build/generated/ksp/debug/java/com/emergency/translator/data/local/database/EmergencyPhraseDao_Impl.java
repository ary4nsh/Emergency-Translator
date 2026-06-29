package com.emergency.translator.data.local.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.emergency.translator.data.local.entity.EmergencyPhraseEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EmergencyPhraseDao_Impl implements EmergencyPhraseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EmergencyPhraseEntity> __insertionAdapterOfEmergencyPhraseEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavorite;

  public EmergencyPhraseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmergencyPhraseEntity = new EntityInsertionAdapter<EmergencyPhraseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `emergency_phrases` (`id`,`englishText`,`arabicText`,`persianText`,`turkishText`,`russianText`,`ukrainianText`,`category`,`isFavorite`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EmergencyPhraseEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getEnglishText());
        statement.bindString(3, entity.getArabicText());
        statement.bindString(4, entity.getPersianText());
        statement.bindString(5, entity.getTurkishText());
        statement.bindString(6, entity.getRussianText());
        statement.bindString(7, entity.getUkrainianText());
        statement.bindString(8, entity.getCategory());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(9, _tmp);
      }
    };
    this.__preparedStmtOfUpdateFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE emergency_phrases SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<EmergencyPhraseEntity> phrases,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEmergencyPhraseEntity.insert(phrases);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFavorite(final long id, final boolean isFavorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EmergencyPhraseEntity>> getAllPhrases() {
    final String _sql = "SELECT * FROM emergency_phrases ORDER BY category, id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"emergency_phrases"}, new Callable<List<EmergencyPhraseEntity>>() {
      @Override
      @NonNull
      public List<EmergencyPhraseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEnglishText = CursorUtil.getColumnIndexOrThrow(_cursor, "englishText");
          final int _cursorIndexOfArabicText = CursorUtil.getColumnIndexOrThrow(_cursor, "arabicText");
          final int _cursorIndexOfPersianText = CursorUtil.getColumnIndexOrThrow(_cursor, "persianText");
          final int _cursorIndexOfTurkishText = CursorUtil.getColumnIndexOrThrow(_cursor, "turkishText");
          final int _cursorIndexOfRussianText = CursorUtil.getColumnIndexOrThrow(_cursor, "russianText");
          final int _cursorIndexOfUkrainianText = CursorUtil.getColumnIndexOrThrow(_cursor, "ukrainianText");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<EmergencyPhraseEntity> _result = new ArrayList<EmergencyPhraseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmergencyPhraseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEnglishText;
            _tmpEnglishText = _cursor.getString(_cursorIndexOfEnglishText);
            final String _tmpArabicText;
            _tmpArabicText = _cursor.getString(_cursorIndexOfArabicText);
            final String _tmpPersianText;
            _tmpPersianText = _cursor.getString(_cursorIndexOfPersianText);
            final String _tmpTurkishText;
            _tmpTurkishText = _cursor.getString(_cursorIndexOfTurkishText);
            final String _tmpRussianText;
            _tmpRussianText = _cursor.getString(_cursorIndexOfRussianText);
            final String _tmpUkrainianText;
            _tmpUkrainianText = _cursor.getString(_cursorIndexOfUkrainianText);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new EmergencyPhraseEntity(_tmpId,_tmpEnglishText,_tmpArabicText,_tmpPersianText,_tmpTurkishText,_tmpRussianText,_tmpUkrainianText,_tmpCategory,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllPhrasesOnce(
      final Continuation<? super List<EmergencyPhraseEntity>> $completion) {
    final String _sql = "SELECT * FROM emergency_phrases ORDER BY category, id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EmergencyPhraseEntity>>() {
      @Override
      @NonNull
      public List<EmergencyPhraseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEnglishText = CursorUtil.getColumnIndexOrThrow(_cursor, "englishText");
          final int _cursorIndexOfArabicText = CursorUtil.getColumnIndexOrThrow(_cursor, "arabicText");
          final int _cursorIndexOfPersianText = CursorUtil.getColumnIndexOrThrow(_cursor, "persianText");
          final int _cursorIndexOfTurkishText = CursorUtil.getColumnIndexOrThrow(_cursor, "turkishText");
          final int _cursorIndexOfRussianText = CursorUtil.getColumnIndexOrThrow(_cursor, "russianText");
          final int _cursorIndexOfUkrainianText = CursorUtil.getColumnIndexOrThrow(_cursor, "ukrainianText");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<EmergencyPhraseEntity> _result = new ArrayList<EmergencyPhraseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmergencyPhraseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEnglishText;
            _tmpEnglishText = _cursor.getString(_cursorIndexOfEnglishText);
            final String _tmpArabicText;
            _tmpArabicText = _cursor.getString(_cursorIndexOfArabicText);
            final String _tmpPersianText;
            _tmpPersianText = _cursor.getString(_cursorIndexOfPersianText);
            final String _tmpTurkishText;
            _tmpTurkishText = _cursor.getString(_cursorIndexOfTurkishText);
            final String _tmpRussianText;
            _tmpRussianText = _cursor.getString(_cursorIndexOfRussianText);
            final String _tmpUkrainianText;
            _tmpUkrainianText = _cursor.getString(_cursorIndexOfUkrainianText);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new EmergencyPhraseEntity(_tmpId,_tmpEnglishText,_tmpArabicText,_tmpPersianText,_tmpTurkishText,_tmpRussianText,_tmpUkrainianText,_tmpCategory,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EmergencyPhraseEntity>> getPhrasesByCategory(final String category) {
    final String _sql = "SELECT * FROM emergency_phrases WHERE category = ? ORDER BY id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"emergency_phrases"}, new Callable<List<EmergencyPhraseEntity>>() {
      @Override
      @NonNull
      public List<EmergencyPhraseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEnglishText = CursorUtil.getColumnIndexOrThrow(_cursor, "englishText");
          final int _cursorIndexOfArabicText = CursorUtil.getColumnIndexOrThrow(_cursor, "arabicText");
          final int _cursorIndexOfPersianText = CursorUtil.getColumnIndexOrThrow(_cursor, "persianText");
          final int _cursorIndexOfTurkishText = CursorUtil.getColumnIndexOrThrow(_cursor, "turkishText");
          final int _cursorIndexOfRussianText = CursorUtil.getColumnIndexOrThrow(_cursor, "russianText");
          final int _cursorIndexOfUkrainianText = CursorUtil.getColumnIndexOrThrow(_cursor, "ukrainianText");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<EmergencyPhraseEntity> _result = new ArrayList<EmergencyPhraseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmergencyPhraseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEnglishText;
            _tmpEnglishText = _cursor.getString(_cursorIndexOfEnglishText);
            final String _tmpArabicText;
            _tmpArabicText = _cursor.getString(_cursorIndexOfArabicText);
            final String _tmpPersianText;
            _tmpPersianText = _cursor.getString(_cursorIndexOfPersianText);
            final String _tmpTurkishText;
            _tmpTurkishText = _cursor.getString(_cursorIndexOfTurkishText);
            final String _tmpRussianText;
            _tmpRussianText = _cursor.getString(_cursorIndexOfRussianText);
            final String _tmpUkrainianText;
            _tmpUkrainianText = _cursor.getString(_cursorIndexOfUkrainianText);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new EmergencyPhraseEntity(_tmpId,_tmpEnglishText,_tmpArabicText,_tmpPersianText,_tmpTurkishText,_tmpRussianText,_tmpUkrainianText,_tmpCategory,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EmergencyPhraseEntity>> getFavorites() {
    final String _sql = "SELECT * FROM emergency_phrases WHERE isFavorite = 1 ORDER BY category, id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"emergency_phrases"}, new Callable<List<EmergencyPhraseEntity>>() {
      @Override
      @NonNull
      public List<EmergencyPhraseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEnglishText = CursorUtil.getColumnIndexOrThrow(_cursor, "englishText");
          final int _cursorIndexOfArabicText = CursorUtil.getColumnIndexOrThrow(_cursor, "arabicText");
          final int _cursorIndexOfPersianText = CursorUtil.getColumnIndexOrThrow(_cursor, "persianText");
          final int _cursorIndexOfTurkishText = CursorUtil.getColumnIndexOrThrow(_cursor, "turkishText");
          final int _cursorIndexOfRussianText = CursorUtil.getColumnIndexOrThrow(_cursor, "russianText");
          final int _cursorIndexOfUkrainianText = CursorUtil.getColumnIndexOrThrow(_cursor, "ukrainianText");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<EmergencyPhraseEntity> _result = new ArrayList<EmergencyPhraseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmergencyPhraseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEnglishText;
            _tmpEnglishText = _cursor.getString(_cursorIndexOfEnglishText);
            final String _tmpArabicText;
            _tmpArabicText = _cursor.getString(_cursorIndexOfArabicText);
            final String _tmpPersianText;
            _tmpPersianText = _cursor.getString(_cursorIndexOfPersianText);
            final String _tmpTurkishText;
            _tmpTurkishText = _cursor.getString(_cursorIndexOfTurkishText);
            final String _tmpRussianText;
            _tmpRussianText = _cursor.getString(_cursorIndexOfRussianText);
            final String _tmpUkrainianText;
            _tmpUkrainianText = _cursor.getString(_cursorIndexOfUkrainianText);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new EmergencyPhraseEntity(_tmpId,_tmpEnglishText,_tmpArabicText,_tmpPersianText,_tmpTurkishText,_tmpRussianText,_tmpUkrainianText,_tmpCategory,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EmergencyPhraseEntity>> searchPhrases(final String query) {
    final String _sql = "SELECT * FROM emergency_phrases\n"
            + "              WHERE LOWER(englishText)   LIKE '%' || LOWER(?) || '%'\n"
            + "                 OR arabicText           LIKE '%' || ? || '%'\n"
            + "                 OR persianText          LIKE '%' || ? || '%'\n"
            + "                 OR LOWER(turkishText)   LIKE '%' || LOWER(?) || '%'\n"
            + "                 OR LOWER(russianText)   LIKE '%' || LOWER(?) || '%'\n"
            + "                 OR LOWER(ukrainianText) LIKE '%' || LOWER(?) || '%'\n"
            + "              ORDER BY category, id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 6);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    _argIndex = 4;
    _statement.bindString(_argIndex, query);
    _argIndex = 5;
    _statement.bindString(_argIndex, query);
    _argIndex = 6;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"emergency_phrases"}, new Callable<List<EmergencyPhraseEntity>>() {
      @Override
      @NonNull
      public List<EmergencyPhraseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEnglishText = CursorUtil.getColumnIndexOrThrow(_cursor, "englishText");
          final int _cursorIndexOfArabicText = CursorUtil.getColumnIndexOrThrow(_cursor, "arabicText");
          final int _cursorIndexOfPersianText = CursorUtil.getColumnIndexOrThrow(_cursor, "persianText");
          final int _cursorIndexOfTurkishText = CursorUtil.getColumnIndexOrThrow(_cursor, "turkishText");
          final int _cursorIndexOfRussianText = CursorUtil.getColumnIndexOrThrow(_cursor, "russianText");
          final int _cursorIndexOfUkrainianText = CursorUtil.getColumnIndexOrThrow(_cursor, "ukrainianText");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<EmergencyPhraseEntity> _result = new ArrayList<EmergencyPhraseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmergencyPhraseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEnglishText;
            _tmpEnglishText = _cursor.getString(_cursorIndexOfEnglishText);
            final String _tmpArabicText;
            _tmpArabicText = _cursor.getString(_cursorIndexOfArabicText);
            final String _tmpPersianText;
            _tmpPersianText = _cursor.getString(_cursorIndexOfPersianText);
            final String _tmpTurkishText;
            _tmpTurkishText = _cursor.getString(_cursorIndexOfTurkishText);
            final String _tmpRussianText;
            _tmpRussianText = _cursor.getString(_cursorIndexOfRussianText);
            final String _tmpUkrainianText;
            _tmpUkrainianText = _cursor.getString(_cursorIndexOfUkrainianText);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new EmergencyPhraseEntity(_tmpId,_tmpEnglishText,_tmpArabicText,_tmpPersianText,_tmpTurkishText,_tmpRussianText,_tmpUkrainianText,_tmpCategory,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object findByEnglishText(final String text,
      final Continuation<? super EmergencyPhraseEntity> $completion) {
    final String _sql = "SELECT * FROM emergency_phrases WHERE englishText = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, text);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EmergencyPhraseEntity>() {
      @Override
      @Nullable
      public EmergencyPhraseEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEnglishText = CursorUtil.getColumnIndexOrThrow(_cursor, "englishText");
          final int _cursorIndexOfArabicText = CursorUtil.getColumnIndexOrThrow(_cursor, "arabicText");
          final int _cursorIndexOfPersianText = CursorUtil.getColumnIndexOrThrow(_cursor, "persianText");
          final int _cursorIndexOfTurkishText = CursorUtil.getColumnIndexOrThrow(_cursor, "turkishText");
          final int _cursorIndexOfRussianText = CursorUtil.getColumnIndexOrThrow(_cursor, "russianText");
          final int _cursorIndexOfUkrainianText = CursorUtil.getColumnIndexOrThrow(_cursor, "ukrainianText");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final EmergencyPhraseEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEnglishText;
            _tmpEnglishText = _cursor.getString(_cursorIndexOfEnglishText);
            final String _tmpArabicText;
            _tmpArabicText = _cursor.getString(_cursorIndexOfArabicText);
            final String _tmpPersianText;
            _tmpPersianText = _cursor.getString(_cursorIndexOfPersianText);
            final String _tmpTurkishText;
            _tmpTurkishText = _cursor.getString(_cursorIndexOfTurkishText);
            final String _tmpRussianText;
            _tmpRussianText = _cursor.getString(_cursorIndexOfRussianText);
            final String _tmpUkrainianText;
            _tmpUkrainianText = _cursor.getString(_cursorIndexOfUkrainianText);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _result = new EmergencyPhraseEntity(_tmpId,_tmpEnglishText,_tmpArabicText,_tmpPersianText,_tmpTurkishText,_tmpRussianText,_tmpUkrainianText,_tmpCategory,_tmpIsFavorite);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM emergency_phrases";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
