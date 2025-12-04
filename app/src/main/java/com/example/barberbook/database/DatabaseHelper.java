package com.example.barberbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.barberbook.models.Barber;
import com.example.barberbook.models.Booking;
import com.example.barberbook.models.Service;
import com.example.barberbook.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "barberbook.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SERVICES = "services";
    private static final String TABLE_BARBERS = "barbers";
    private static final String TABLE_BOOKINGS = "bookings";

    // Common Columns
    private static final String COL_ID = "id";

    // Users Table Columns
    private static final String COL_FULL_NAME = "full_name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_GENDER = "gender";
    private static final String COL_PASSWORD = "password";

    // Services Table Columns
    private static final String COL_NAME = "name";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_PRICE = "price";

    // Barbers Table Columns
    private static final String COL_RATING = "rating";
    private static final String COL_EXPERIENCE = "experience";
    private static final String COL_AVAILABLE = "available";

    // Bookings Table Columns
    private static final String COL_USER_ID = "user_id";
    private static final String COL_BARBER_ID = "barber_id";
    private static final String COL_BARBER_NAME = "barber_name";
    private static final String COL_SERVICES = "services";
    private static final String COL_DATE = "date";
    private static final String COL_TIME = "time";
    private static final String COL_TOTAL_PRICE = "total_price";
    private static final String COL_STATUS = "status";
    private static final String COL_NOTES = "notes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FULL_NAME + " TEXT, " +
                COL_EMAIL + " TEXT UNIQUE, " +
                COL_PHONE + " TEXT, " +
                COL_GENDER + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // Create Services Table
        String createServicesTable = "CREATE TABLE " + TABLE_SERVICES + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_PRICE + " INTEGER)";
        db.execSQL(createServicesTable);

        // Create Barbers Table
        String createBarbersTable = "CREATE TABLE " + TABLE_BARBERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_RATING + " REAL, " +
                COL_EXPERIENCE + " INTEGER, " +
                COL_AVAILABLE + " INTEGER)";
        db.execSQL(createBarbersTable);

        // Create Bookings Table
        String createBookingsTable = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_ID + " INTEGER, " +
                COL_BARBER_ID + " INTEGER, " +
                COL_BARBER_NAME + " TEXT, " +
                COL_SERVICES + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT, " +
                COL_TOTAL_PRICE + " INTEGER, " +
                COL_STATUS + " TEXT, " +
                COL_NOTES + " TEXT)";
        db.execSQL(createBookingsTable);

        // Insert default services
        insertDefaultServices(db);

        // Insert default barbers
        insertDefaultBarbers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    private void insertDefaultServices(SQLiteDatabase db) {
        String[] names = {"Haircut", "Shaving", "Hair Wash", "Hair Coloring"};
        String[] descs = {"Classic haircut with styling", "Clean shave with hot towel",
                "Shampoo and head massage", "Premium hair dye service"};
        int[] prices = {35000, 15000, 20000, 100000};

        for (int i = 0; i < names.length; i++) {
            ContentValues values = new ContentValues();
            values.put(COL_NAME, names[i]);
            values.put(COL_DESCRIPTION, descs[i]);
            values.put(COL_PRICE, prices[i]);
            db.insert(TABLE_SERVICES, null, values);
        }
    }

    private void insertDefaultBarbers(SQLiteDatabase db) {
        String[] names = {"Mas Andi", "Mas Budi", "Mas Chandra"};
        double[] ratings = {4.8, 4.5, 4.9};
        int[] exps = {5, 3, 7};
        int[] available = {1, 1, 0};

        for (int i = 0; i < names.length; i++) {
            ContentValues values = new ContentValues();
            values.put(COL_NAME, names[i]);
            values.put(COL_RATING, ratings[i]);
            values.put(COL_EXPERIENCE, exps[i]);
            values.put(COL_AVAILABLE, available[i]);
            db.insert(TABLE_BARBERS, null, values);
        }
    }

    // ==================== USER OPERATIONS ====================

    public long registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FULL_NAME, user.getFullName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PHONE, user.getPhone());
        values.put(COL_GENDER, user.getGender());
        values.put(COL_PASSWORD, user.getPassword());
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    public User loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COL_EMAIL + "=? AND " + COL_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow(COL_FULL_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
            user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)));
            user.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)));
        }
        cursor.close();
        db.close();
        return user;
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COL_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow(COL_FULL_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
            user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)));
            user.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER)));
        }
        cursor.close();
        db.close();
        return user;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_ID},
                COL_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // ==================== SERVICE OPERATIONS ====================

    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SERVICES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Service service = new Service();
                service.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                service.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                service.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
                service.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRICE)));
                services.add(service);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return services;
    }

    // ==================== BARBER OPERATIONS ====================

    public List<Barber> getAllBarbers() {
        List<Barber> barbers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BARBERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Barber barber = new Barber();
                barber.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                barber.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                barber.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_RATING)));
                barber.setExperience(cursor.getInt(cursor.getColumnIndexOrThrow(COL_EXPERIENCE)));
                barber.setAvailable(cursor.getInt(cursor.getColumnIndexOrThrow(COL_AVAILABLE)) == 1);
                barbers.add(barber);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return barbers;
    }

    public Barber getBarberById(int barberId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BARBERS, null,
                COL_ID + "=?", new String[]{String.valueOf(barberId)},
                null, null, null);

        Barber barber = null;
        if (cursor.moveToFirst()) {
            barber = new Barber();
            barber.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            barber.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
            barber.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_RATING)));
            barber.setExperience(cursor.getInt(cursor.getColumnIndexOrThrow(COL_EXPERIENCE)));
            barber.setAvailable(cursor.getInt(cursor.getColumnIndexOrThrow(COL_AVAILABLE)) == 1);
        }
        cursor.close();
        db.close();
        return barber;
    }

    // ==================== BOOKING OPERATIONS ====================

    public long createBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, booking.getUserId());
        values.put(COL_BARBER_ID, booking.getBarberId());
        values.put(COL_BARBER_NAME, booking.getBarberName());
        values.put(COL_SERVICES, booking.getServices());
        values.put(COL_DATE, booking.getDate());
        values.put(COL_TIME, booking.getTime());
        values.put(COL_TOTAL_PRICE, booking.getTotalPrice());
        values.put(COL_STATUS, booking.getStatus());
        values.put(COL_NOTES, booking.getNotes());
        long result = db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return result;
    }

    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKINGS, null,
                COL_USER_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, COL_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)));
                booking.setBarberId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_BARBER_ID)));
                booking.setBarberName(cursor.getString(cursor.getColumnIndexOrThrow(COL_BARBER_NAME)));
                booking.setServices(cursor.getString(cursor.getColumnIndexOrThrow(COL_SERVICES)));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)));
                booking.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME)));
                booking.setTotalPrice(cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTAL_PRICE)));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS)));
                booking.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES)));
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookings;
    }

    public int cancelBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, "Cancelled");
        int result = db.update(TABLE_BOOKINGS, values, COL_ID + "=?",
                new String[]{String.valueOf(bookingId)});
        db.close();
        return result;
    }

    public int getBookingCountByStatus(int userId, String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKINGS, new String[]{"COUNT(*)"},
                COL_USER_ID + "=? AND " + COL_STATUS + "=?",
                new String[]{String.valueOf(userId), status}, null, null, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public int getTotalBookingsByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKINGS, new String[]{"COUNT(*)"},
                COL_USER_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
}
