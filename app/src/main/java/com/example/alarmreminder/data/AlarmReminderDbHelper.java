//package com.example.alarmreminder.data;
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.widget.Toast;
//
//import com.example.alarmreminder.Model.ToDoModel;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static com.example.alarmreminder.data.AlarmReminderContract.AlarmReminderEntry.TABLE_NAME;
//
//
//
///*
// */
//
//public class AlarmReminderDbHelper extends SQLiteOpenHelper {
//    public static AlarmReminderDbHelper sqLiteManager;
//    private  SQLiteDatabase sqLiteDatabase;
//    Context context;
//
//    //private static final String DATABASE_NAME = "alarmreminder.db";
//    private static final String DATABASE_NAME="alarmreminder";
//
//    private static final int DATABASE_VERSION = 1;
//
//
//    private static final String Table_Notes = "mynotes";
//    private static final String ColumnId = "id";
//    private static final String ColumnTitle = "note_title";
//    private static final String ColumnDescription = "note_description";
//
//    private static  final String TABLE_TODO = "TODO_TABLE";
//    private static  final String COL_1 = "ID";
//    private static  final String COL_2 = "TASK";
//    private static  final String COL_3 = "STATUS";
//
//   // private static final String ID_FIELD = "id";
//
//// you were supposed to add a nullable before context
//
//
//    public AlarmReminderDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//    }
////    @SuppressLint("SimpleDateFormat")
////    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//
//    public static AlarmReminderDbHelper instanceOfDatabase(Context context)
//    {
//        if(sqLiteManager == null)
//            sqLiteManager = new AlarmReminderDbHelper(context);
//
//        return sqLiteManager;
//    }
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        // Create a String that contains the SQL statement to create the reminder table
//        String SQL_CREATE_ALARM_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
//                + AlarmReminderContract.AlarmReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + AlarmReminderContract.AlarmReminderEntry.KEY_TITLE + " TEXT, "
//                + AlarmReminderContract.AlarmReminderEntry.KEY_DATE + " TEXT, "
//                + AlarmReminderContract.AlarmReminderEntry.KEY_TIME + " TEXT, "
//                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT + " TEXT, "
//                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO + " TEXT, "
//                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE + " TEXT, "
//                + AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE + " TEXT " + " );";
//// STRING FOR NOTES TABLE
//
//        String query = " CREATE TABLE  " + Table_Notes +
//                " ( "
//                + ColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + ColumnTitle + " TEXT, "
//                + ColumnDescription + " TEXT);";
//
//
//
//        //creating table for tasks
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "
//                + TABLE_TODO +
//                "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
//
//        // Execute the SQL statement
//        sqLiteDatabase.execSQL(SQL_CREATE_ALARM_TABLE);
//        sqLiteDatabase.execSQL(query);
//
//
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME );
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_TODO);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Table_Notes);
//// create new table
//        onCreate(sqLiteDatabase);
//    }
////adding notes to the database
//  public static  void addNotes(String note_title, String note_description,Context context) {
//       AlarmReminderDbHelper mDbHelper = new AlarmReminderDbHelper(context);
//      SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//        cv.put(ColumnTitle, note_title);
//        cv.put(ColumnDescription, note_description);
//
//        long resultValue = sqLiteDatabase.insert(Table_Notes, null, cv);
//
//        if (resultValue == -1) {
//            Toast.makeText(context, "Data Not Added", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Data Added Successfully", Toast.LENGTH_SHORT).show();
//        }
//    }
//   //read notes from the database
//    public Cursor readAllData() {
//        String query = "SELECT * FROM " + Table_Notes;
//        sqLiteDatabase=this.getReadableDatabase();
//
//        Cursor cursor = null;
//        if (sqLiteDatabase != null) {
//            cursor = sqLiteDatabase.rawQuery(query, null);
//        }
//        return cursor;
//    }
//    //delete from the database
//    public void deleteAllNotes(){
//        sqLiteDatabase=this.getWritableDatabase();
//                String query="DELETE FROM " + Table_Notes;
//                sqLiteDatabase.execSQL(query);
//    }
//    //update data from the database
//    public void updateNotes(String note_title, String note_description, String id){
//        sqLiteDatabase = this.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//        cv.put(ColumnTitle, note_title);
//        cv.put(ColumnDescription, note_description);
//
//        long result = sqLiteDatabase.update(Table_Notes, cv, "id=?", new String[]{id});
//        if (result == -1) {
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    public void deleteSingleItem(String id) {
//        sqLiteDatabase = this.getWritableDatabase();
//
//        long result = sqLiteDatabase.delete(Table_Notes, "id=?", new String[]{id});
//        if (result == -1) {
//            Toast.makeText(context, "Item Not Deleted", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
//        }
//    }
//
////Tasks Operations
//    public void insertTask(ToDoModel model){
//        sqLiteDatabase = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COL_2 , model.getTask());
//        values.put(COL_3 , 0);
//        sqLiteDatabase.insert(TABLE_TODO , null , values);
//    }
//
//    public void updateTask(int id , String task){
//        sqLiteDatabase = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COL_2 , task);
//        sqLiteDatabase.update(TABLE_TODO , values , "ID=?" , new String[]{String.valueOf(id)});
//    }
//
//    public void updateStatus(int id , int status){
//        sqLiteDatabase = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COL_3 , status);
//        sqLiteDatabase.update(TABLE_TODO , values , "ID=?" , new String[]{String.valueOf(id)});
//    }
//    public void deleteTask(int id ){
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.delete(TABLE_TODO , "ID=?" , new String[]{String.valueOf(id)});
//    }
//
//    public List<ToDoModel> getAllTasks(){
//
//        sqLiteDatabase = this.getWritableDatabase();
//        Cursor cursor = null;
//        List<ToDoModel> modelList = new ArrayList<>();
//
//        sqLiteDatabase.beginTransaction();
//        try {
//            cursor = sqLiteDatabase.query(TABLE_TODO , null , null , null , null , null , null);
//            if (cursor !=null){
//                if (cursor.moveToFirst()){
//                    do {
//                        ToDoModel task = new ToDoModel();
//                        task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
//                        task.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
//                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
//                        modelList.add(task);
//
//                    }while (cursor.moveToNext());
//                }
//            }
//        }finally {
//            sqLiteDatabase.endTransaction();
//            cursor.close();
//        }
//        return modelList;
//    }
//}
