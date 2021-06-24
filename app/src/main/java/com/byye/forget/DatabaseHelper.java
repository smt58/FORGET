package com.byye.forget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/////leak oluyor bak

class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance = null;
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Versiyonu
    private static final int DATABASE_VERSION = 8;

    // Database Adi
    private static final String DATABASE_NAME = "forget.db";

    // Table Adlari
    private static final String TABLE_USER = "tabloforget";

    //User tablosunun sütunlari
    private static final String USER_ID = "id";
    private static final String ID_BASLIK= "baslik";
    private static final String ID_BILDIRIM = "bildirim";
    private static final String ID_X_KONUM = "xkonum";
    private static final String ID_Y_KONUM = "ykonum";
    private static final String ID_YIL = "yil";
    private static final String ID_AY = "ay";
    private static final String ID_GUN = "gun";
    private static final String ID_SAAT = "saat";
    private static final String ID_DK = "dakika";
    private static final String ID_PATH = "path";

    Context Mctx;

    // Table Create Statements
    // User table
    public static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_BASLIK
            + " TEXT, " + ID_BILDIRIM + " TEXT, " + ID_X_KONUM + " FLOAT, " + ID_Y_KONUM + " FLOAT, " + ID_YIL + " Int, "
            + ID_AY + " Int, " + ID_GUN + " Int, " + ID_SAAT + " Int, " + ID_DK + " Int, " + ID_PATH + " String )";

    /*
     * Bu siniftan sadece tek bir tane nesne olusmasini saglar.
     * Bu sayede memory leak meydana gelmez.
     *
     * @param context
     * @return DatabaseHelper nesnesi
    */
    static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //Mctx=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // create new tables
        onCreate(db);
    }

    /*
     * Veritabanini kapatir
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /*
     * Yeni kullanici eklemeyi saglar
     *
     * @param user1 eklenecek kullanici
     * @return eklenen kullanicinin id'si doner, hata durumunda -1 doner
    */
    long createUser(user user1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, user1.getID());
        values.put(ID_BASLIK, user1.getBaslık());
        values.put(ID_BILDIRIM, user1.getBildirim());
        values.put(ID_X_KONUM, user1.getX_konum());
        values.put(ID_Y_KONUM, user1.getY_konum());
        values.put(ID_YIL, user1.getYil());
        values.put(ID_AY, user1.getAy());
        values.put(ID_GUN, user1.getGun());
        values.put(ID_SAAT, user1.getSaat());
        values.put(ID_DK, user1.getDk());
        values.put(ID_PATH,user1.getPath());

        // insert row
        return db.insert(TABLE_USER, null, values);

    }

    /*
     * id'ye gore kullanici getirir.
     *
     * @param userId belirtilen id degerine gore kullaniciyi getirir
     * @return user eger userId ile belirtilen id'ye sahip kullanici varsa donderir,
     * aksi taktirde null doner
    */
    user getUser(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + userId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            user user2 = new user();
            user2.setID(c.getInt(c.getColumnIndex(USER_ID)));
            user2.setBaslık((c.getString(c.getColumnIndex(ID_BASLIK))));
            user2.setBildirim(c.getString(c.getColumnIndex(ID_BILDIRIM)));
            user2.setX_konum(c.getFloat(c.getColumnIndex(ID_X_KONUM)));
            user2.setY_konum(c.getFloat(c.getColumnIndex(ID_Y_KONUM)));
            user2.setYil(c.getInt(c.getColumnIndex(ID_YIL)));
            user2.setAy(c.getInt(c.getColumnIndex(ID_AY)));
            user2.setGun(c.getInt(c.getColumnIndex(ID_GUN)));
            user2.setSaat(c.getInt(c.getColumnIndex(ID_SAAT)));
            user2.setDk(c.getInt(c.getColumnIndex(ID_DK)));
            user2.setPath(c.getString(c.getColumnIndex(ID_PATH)));
            return user2;

        } else {
            return null;
        }

    }

    /*
     * Tum kullanicilari getirir
     * * @param user3 guncellenecek kullanici nesnesi
     * @return Kayitli kullanicilar
    */
    List<user> getAllUsers() {
        List<user> users = new ArrayList<user>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                user user3 = new user();
                user3.setID(c.getInt(c.getColumnIndex(USER_ID)));
                user3.setBaslık((c.getString(c.getColumnIndex(ID_BASLIK))));
                user3.setBildirim(c.getString(c.getColumnIndex(ID_BILDIRIM)));
                user3.setX_konum(c.getFloat(c.getColumnIndex(ID_X_KONUM)));
                user3.setY_konum(c.getFloat(c.getColumnIndex(ID_Y_KONUM)));
                user3.setYil(c.getInt(c.getColumnIndex(ID_YIL)));
                user3.setAy(c.getInt(c.getColumnIndex(ID_AY)));
                user3.setGun(c.getInt(c.getColumnIndex(ID_GUN)));
                user3.setSaat(c.getInt(c.getColumnIndex(ID_SAAT)));
                user3.setDk(c.getInt(c.getColumnIndex(ID_DK)));
                user3.setPath(c.getString(c.getColumnIndex(ID_PATH)));

                // adding to user list
                users.add(user3);
            } while (c.moveToNext());
        }

        return users;
    }


    /*
     * Kullaniciyi gunceller
     *
     * @param user4 guncellenecek kullanici nesnesi
     * @return etkilenen satir sayisi
    */
    int updateUser(user user4) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, user4.getID());
        values.put(ID_BASLIK, user4.getBaslık());
        values.put(ID_BILDIRIM, user4.getBildirim());
        values.put(ID_X_KONUM, user4.getX_konum());
        values.put(ID_Y_KONUM, user4.getY_konum());
        values.put(ID_YIL, user4.getYil());
        values.put(ID_AY, user4.getAy());
        values.put(ID_GUN, user4.getGun());
        values.put(ID_SAAT, user4.getSaat());
        values.put(ID_DK, user4.getDk());
        values.put(ID_PATH,user4.getPath());

        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[]{String.valueOf(user4.getID())});
    }

    /*
     * Kullaniciyi siler
     *
     * @param userId silinecek kullanici id'si
    */
    void deleteUser(Integer userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, USER_ID + " = ?",
                new String[]{String.valueOf(userId)});
    }
}

