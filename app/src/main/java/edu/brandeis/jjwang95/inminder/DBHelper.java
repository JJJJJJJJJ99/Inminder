package edu.brandeis.jjwang95.inminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by WangJingjing on 11/10/16.
 */


//Jingjing Wang: jjwang95@brandeis.edu
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "InminderData.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    // Table names cannot capitalized !!!!!
    private static final String PASSWORD_TABLE = "password_table";
    private static final String BILL_TABLE = "bill_table";
    private static final String REMINDER_TABLE = "reminder_table";
    private static final String BUDGET_TABLE = "budget_table";

    // Common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_CREATED_AT = "created_at";

    // PASSWORD
    private static final String PASSWORD_ID = "_id";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    // Statement for creating Password table
    private static final String PASSWORD_CREATE =
            "CREATE TABLE " + PASSWORD_TABLE + " (" +
                    PASSWORD_ID + " integer PRIMARY KEY AUTOINCREMENT," +
                    KEY_WEBSITE + "," +
                    KEY_EMAIL + "," +
                    KEY_PASSWORD + ");";
                    /*" UNIQUE (" + KEY_CODE +"));";*/


    private static final String BILL_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_BILLNOTE = "note";
    private static Double budget = 0.0;
    private static Double sum = 0.0;

    // Statement for creating Bill table
    private static final String BILL_CREATE =
            "CREATE TABLE if not exists " + BILL_TABLE + " (" +
                    BILL_ID + " integer PRIMARY KEY AUTOINCREMENT," +
                    KEY_TITLE + "," +
                    KEY_AMOUNT + " REAL," +
                    KEY_BILLNOTE + ");";
    private static final String BUDGET_ID = "_id";
    private static final String KEY_BUDGET_AMOUNT = "budgetAmount";

    private static final String BUDGET_CREATE =
            "CREATE TABLE if not exists " + BUDGET_TABLE + "(" +
                    BUDGET_ID + " integer PRIMARY KEY AUTOINCREMENT," +
                    KEY_BUDGET_AMOUNT + " REAL);";

    // REMINDER
    private static final String REMINDER_ID = "_id";
    private static final String KEY_TIME = "date";
    private static final String KEY_NAME = "name";
    private static final String KEY_NOTE = "note";

    //Statement for creating Reminder table
    private static final String REMINDER_CREATE =
            "CREATE TABLE if not exists " + REMINDER_TABLE + " (" +
                    REMINDER_ID + " integer PRIMARY KEY AUTOINCREMENT," +
                    KEY_TIME + "," +
                    KEY_NAME + "," +
                    KEY_NOTE + ");";

    //private final Context context;
    private static DBHelper dbHelper;
    private SQLiteDatabase db;

    private DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context){
        if(dbHelper == null){
            dbHelper = new DBHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    // Override method, create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REMINDER_CREATE);
        db.execSQL(BILL_CREATE);
        db.execSQL(PASSWORD_CREATE);
        db.execSQL(BUDGET_CREATE);
    }

    // Used when drop the older tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PASSWORD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BILL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REMINDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BUDGET_TABLE);

        // Create new tables
        onCreate(db);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    // Jingjing Wang: jjwang95@brandeis.edu
    //Create tables
    // Password tables ********************************************************************
    public long createPassword(PasswordObject password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initPassword = new ContentValues();
        initPassword.put(KEY_WEBSITE, password.getWebsite());
        initPassword.put(KEY_EMAIL, password.getEmail());
        initPassword.put(KEY_PASSWORD, password.getPassword());
        Log.d("Initialize Password", "Initialized");
        return db.insert(PASSWORD_TABLE, null, initPassword);
    }
    public PasswordObject getPassword(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        // Check PASSWORD_ID
        String selectQuery = "SELECT  * FROM " + PASSWORD_TABLE + " WHERE "
                + KEY_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
        }
        PasswordObject p = new PasswordObject();
        p.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        p.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
        p.setWebsite(c.getString(c.getColumnIndex(KEY_WEBSITE)));
        p.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
        return p;
    }
    public Cursor getAllPasswords(){
        //ArrayList<PasswordObject> objects = new ArrayList<PasswordObject>();
        String selectQuery = "SELECT  * FROM " + PASSWORD_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        /*if (c.moveToFirst()) {
            do {
                PasswordObject po = new PasswordObject();
                po.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                po.setWebsite((c.getString(c.getColumnIndex(KEY_WEBSITE))));
                po.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                objects.add(po);
            } while (c.moveToNext());
        }*/

        return c;
    }

    public Cursor getPasswordsByAccountName(String constraint) throws SQLException {
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if (constraint==null||constraint.length()==0) {
            c = db.query(PASSWORD_TABLE,
                    new String[] {PASSWORD_ID,KEY_WEBSITE,KEY_EMAIL,KEY_PASSWORD},
                    null,null,null,null,null);
        } else {
            c = db.query(true,PASSWORD_TABLE,
                    new String[] {PASSWORD_ID,KEY_WEBSITE,KEY_EMAIL,KEY_PASSWORD},
                    KEY_EMAIL+" like '%"+constraint+"%'",
                    null,null,null,null,null);
        }
        if (c!=null) {
            c.moveToFirst();
        }
        return c;

    }

    public int updatePassword(PasswordObject p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, p.getPassword());
        // Based on if website can change or not
        values.put(KEY_WEBSITE, p.getWebsite());
        values.put(KEY_EMAIL,p.getEmail());
        return db.update(PASSWORD_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(p.getId()) });
    }
    public void deletePassword(long p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PASSWORD_TABLE, KEY_ID + "=" + p_id, null);
    }

    public void deleteAllPassword(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ PASSWORD_TABLE);
    }

    // Budget tables *****************************************************************
    public long createBudget(Double budget){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(BUDGET_CREATE);
        ContentValues initBudget = new ContentValues();
        initBudget.put(KEY_BUDGET_AMOUNT, budget);
        return db.insert(BUDGET_TABLE, null, initBudget);
    }
    public Double getBudget(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + BUDGET_TABLE;
        Double budget = 0.0;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do{
                budget = c.getDouble(c.getColumnIndex(KEY_BUDGET_AMOUNT));
            } while (c.moveToNext());
        }
        return budget;
    }

    // Bill tables ********************************************************************

    public long createBill(BillObject bill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initBill = new ContentValues();
        initBill.put(KEY_TITLE, bill.getTitle());
        Log.d("ADD BILL TITLE", bill.getTitle());
        initBill.put(KEY_AMOUNT, bill.getAmount());
        //Log.d("ADD BILL AMOUNT", bill.getAmount());
        initBill.put(KEY_BILLNOTE, bill.getNote());
        Log.d("ADD BILL NOTE", bill.getNote());
        return db.insert(BILL_TABLE, null, initBill);
    }
    public BillObject getBill(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + BILL_TABLE + " WHERE "
                + KEY_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
        }
        BillObject b = new BillObject();
        b.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        b.setAmount(c.getDouble(c.getColumnIndex(KEY_AMOUNT)));
        b.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
        b.setNote(c.getString((c.getColumnIndex(KEY_BILLNOTE))));
        return b;
    }
    public Cursor getAllBills(){
        //ArrayList<BillObject> objects = new ArrayList<BillObject>();
        String selectQuery = "SELECT  * FROM " + BILL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        /*if (c.moveToFirst()) {
            do {
                BillObject bo = new BillObject();
                bo.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                bo.setTitle((c.getString(c.getColumnIndex(KEY_TITLE))));
                bo.setAmount(c.getString(c.getColumnIndex(KEY_AMOUNT)));
                objects.add(bo);
            } while (c.moveToNext());
        }*/

        return c;
    }

    public Double getSum(){
        String selectQuery = "SELECT amount FROM " + BILL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Double sum = 0.0;
        if (c.moveToFirst()){
            do{
                sum = sum + c.getDouble(c.getColumnIndex(KEY_AMOUNT));
            } while (c.moveToNext());
        }

        return sum;
    }


    public int updateBill(BillObject b){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, b.getAmount());
        values.put(KEY_TITLE, b.getTitle());
        values.put(KEY_BILLNOTE, b.getNote());
        values.put(KEY_ID, b.getId());
        return db.update(BILL_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(b.getId()) });
    }
    public void deleteBill(long b_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BILL_TABLE, KEY_ID + "=" + b_id, null);
    }
    public void deleteAllBill(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + BILL_TABLE);
    }

    // Reminder tables ******************************************************************** zhen shi tai chang le !!!
    // Ren wu ke ren!!!!!!
    public long createReminder(ReminderObject reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initReminder = new ContentValues();
        initReminder.put(KEY_TIME, reminder.getTime());
        initReminder.put(KEY_NAME, reminder.getName());
        initReminder.put(KEY_NOTE, reminder.getNote());

        return db.insert(REMINDER_TABLE, null, initReminder);
    }
    public ReminderObject getReminder(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + REMINDER_TABLE + " WHERE "
                + KEY_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
        }
        ReminderObject r = new ReminderObject();
        r.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        r.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        r.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
        r.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
        return r;
    }
    public Cursor getAllReminders(){
        //ArrayList<ReminderObject> objects = new ArrayList<ReminderObject>();
        String selectQuery = "SELECT  * FROM " + REMINDER_TABLE + " ORDER BY " + REMINDER_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        /*if (c.moveToFirst()) {
            do {
                ReminderObject ro = new ReminderObject();
                ro.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                ro.setNote((c.getString(c.getColumnIndex(KEY_NOTE))));
                ro.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
                ro.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                objects.add(ro);
            } while (c.moveToNext());
        }*/

        return c;
    }
    public int updateReminder(ReminderObject r){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, r.getNote());
        values.put(KEY_NAME, r.getName());
        values.put(KEY_TIME, r.getTime());
        return db.update(REMINDER_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(r.getId()) });
    }
    public void deleteReminder(long r_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(REMINDER_TABLE, KEY_ID + "=" + r_id, null);
    }
    public void deleteAllReminder(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ REMINDER_TABLE);
    }

}
