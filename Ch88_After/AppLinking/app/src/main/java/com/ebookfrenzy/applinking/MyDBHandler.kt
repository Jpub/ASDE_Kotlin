package com.ebookfrenzy.applinking

import com.ebookfrenzy.applinking.provider.MyContentProvider

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.content.ContentResolver
import android.util.Log

import java.util.ArrayList

class MyDBHandler(context: Context?, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    private val myCR: ContentResolver?
    private val TAG = "Database"

    private val id_col = 0
    private val title_col = 1
    private val desc_col = 2
    private val personal_col = 3

    init {
        myCR = context?.contentResolver
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS)
        onCreate(db)
    }

    fun addLandmark(landmark: Landmark) {

        val values = ContentValues()
        values.put(COLUMN_ID, landmark.id)
        values.put(COLUMN_TITLE, landmark.title)
        values.put(COLUMN_DESCRIPTION, landmark.description)
        values.put(COLUMN_PERSONAL, landmark.personal)

        myCR?.insert(MyContentProvider.CONTENT_URI, values)

    }

    fun findAllLandmarks(): ArrayList<Landmark> {

        val landmarks = ArrayList<Landmark>()


        val projection = arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PERSONAL)

        val selection: String? = null

        val cursor = myCR?.query(MyContentProvider.CONTENT_URI,
                projection, selection, null, null)

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {

                val landmark = Landmark()

                landmark.id = cursor.getString(id_col)
                landmark.title = cursor.getString(title_col)
                landmark.description = cursor.getString(desc_col)
                landmark.personal = cursor.getInt(personal_col)
                landmarks.add(landmark)
                cursor.moveToNext()

            }
        }
        //cursor.close();
        return landmarks
    }

    fun findLandmark(landmarkId: String): Landmark? {
        val projection = arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PERSONAL)

        val selection = "_id = \"" + landmarkId + "\""

        Log.i(TAG, "SELECTION = " + selection)

        val cursor = myCR?.query(MyContentProvider.CONTENT_URI,
                projection, selection, null, null)

        var landmark: Landmark? = Landmark()

        if (cursor!!.moveToFirst()) {
            cursor.moveToFirst()
            landmark?.id = cursor.getString(0)
            landmark?.title = cursor.getString(1)
            landmark?.description = cursor.getString(2)
            landmark?.personal = cursor.getInt(3)
            cursor.close()
        } else {
            landmark = null
        }
        return landmark

    }

    fun deleteLandmark(landmarkId: String?): Boolean {

        var result = false

        val selection = "_id = \"" + landmarkId + "\""

        val rowsDeleted = myCR?.delete(MyContentProvider.CONTENT_URI,
                selection, null)

        if (rowsDeleted != 0)
            result = true

        return result
    }

    companion object {
        val TABLE_LOCATIONS = "locations"

        val COLUMN_ID = "_id"
        val COLUMN_TITLE = "title"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_PERSONAL = "personal"

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "landmarks.db"
    }

}

