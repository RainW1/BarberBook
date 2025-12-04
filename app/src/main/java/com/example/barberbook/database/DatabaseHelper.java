package com.example.barberbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.barberbook.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tag untuk logging
    private static final String TAG = "DatabaseHelper";

    // Database Info
    private static final String DATABASE_NAME = "BarberBookDB.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";

    // User Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";

    // Singleton instance
    private static DatabaseHelper instance;

    // Private constructor (Singleton pattern)
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Get singleton instance
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT NOT NULL,"
                + KEY_EMAIL + " TEXT UNIQUE NOT NULL,"
                + KEY_PASSWORD + " TEXT NOT NULL,"
                + KEY_PHONE + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    // ========== USER CRUD OPERATIONS ==========

    /**
     * Insert new user into database
     * @param user User object to insert
     * @return row ID of newly inserted user, or -1 if error
     */
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PHONE, user.getPhone());

        long id = db.insert(TABLE_USERS, null, values);
        db.close();

        Log.d(TAG, "User inserted with ID: " + id);
        return id;
    }

    /**
     * Get user by email
     * @param email User email
     * @return User object if found, null otherwise
     */
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(TABLE_USERS,
                null,
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE))
            );
            cursor.close();
        }

        db.close();
        return user;
    }

    /**
     * Check if user exists with email and password (for login)
     * @param email User email
     * @param password User password
     * @return true if user exists and password matches
     */
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                null,
                KEY_EMAIL + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    /**
     * Check if email already exists in database
     * @param email Email to check
     * @return true if email exists
     */
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    /**
     * Update user information
     * @param user User object with updated information
     * @return number of rows affected
     */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PHONE, user.getPhone());

        int rows = db.update(TABLE_USERS, values,
                KEY_ID + "=?",
                new String[]{String.valueOf(user.getId())});

        db.close();
        Log.d(TAG, "User updated: " + rows + " rows affected");
        return rows;
    }

    /**
     * Delete user from database
     * @param userId User ID to delete
     * @return number of rows affected
     */
    public int deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rows = db.delete(TABLE_USERS,
                KEY_ID + "=?",
                new String[]{String.valueOf(userId)});

        db.close();
        Log.d(TAG, "User deleted: " + rows + " rows affected");
        return rows;
    }
}
