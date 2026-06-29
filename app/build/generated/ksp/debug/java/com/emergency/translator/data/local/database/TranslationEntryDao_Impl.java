package com.emergency.translator.data.local.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.emergency.translator.data.local.entity.TranslationEntryEntity;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TranslationEntryDao_Impl implements TranslationEntryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TranslationEntryEntity> __insertionAdapterOfTranslationEntryEntity;

  public TranslationEntryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTranslationEntryEntity = new EntityInsertionAdapter<TranslationEntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `translation_entries` (`id`,`englishWord`,`arabicWord`,`persianWord`,`turkishWord`,`russianWord`,`ukrainianWord`,`category`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TranslationEntryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getEnglishWord());
        statement.bindString(3, entity.getArabicWord());
        statement.bindString(4, entity.getPersianWord());
        statement.bindString(5, entity.getTurkishWord());
        statement.bindString(6, entity.getRussianWord());
        statement.bindString(7, entity.getUkrainianWord());
        statement.bindString(8, entity.getCategory());
      }
    };
  }

  @Override
  public Object insertAll(final List<TranslationEntryEntity> entries,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTranslationEntryEntity.insert(entries);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM translation_entries";
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

  @Override
  public Object getAllEntries(
      final Continuation<? super List<TranslationEntryEntity>> $completion) {
    final String _sql = "SELECT * FROM translation_entries";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TranslationEntryEntity>>() {
      @Override
      @NonNull
      public List<TranslationEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEnglishWord = CursorUtil.getColumnIndexOrThrow(_cursor, "englishWord");
          final int _cursorIndexOfArabicWord = CursorUtil.getColumnIndexOrThrow(_cursor, "arabicWord");
          final int _cursorIndexOfPersianWord = CursorUtil.getColumnIndexOrThrow(_cursor, "persianWord");
          final int _cursorIndexOfTurkishWord = CursorUtil.getColumnIndexOrThrow(_cursor, "turkishWord");
          final int _cursorIndexOfRussianWord = CursorUtil.getColumnIndexOrThrow(_cursor, "russianWord");
          final int _cursorIndexOfUkrainianWord = CursorUtil.getColumnIndexOrThrow(_cursor, "ukrainianWord");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<TranslationEntryEntity> _result = new ArrayList<TranslationEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TranslationEntryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEnglishWord;
            _tmpEnglishWord = _cursor.getString(_cursorIndexOfEnglishWord);
            final String _tmpArabicWord;
            _tmpArabicWord = _cursor.getString(_cursorIndexOfArabicWord);
            final String _tmpPersianWord;
            _tmpPersianWord = _cursor.getString(_cursorIndexOfPersianWord);
            final String _tmpTurkishWord;
            _tmpTurkishWord = _cursor.getString(_cursorIndexOfTurkishWord);
            final String _tmpRussianWord;
            _tmpRussianWord = _cursor.getString(_cursorIndexOfRussianWord);
            final String _tmpUkrainianWord;
            _tmpUkrainianWord = _cursor.getString(_cursorIndexOfUkrainianWord);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item = new TranslationEntryEntity(_tmpId,_tmpEnglishWord,_tmpArabicWord,_tmpPersianWord,_tmpTurkishWord,_tmpRussianWord,_tmpUkrainianWord,_tmpCategory);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
