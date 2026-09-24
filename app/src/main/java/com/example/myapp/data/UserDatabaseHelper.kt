package com.example.myapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "auto_care_users.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL_OR_PHONE = "email_or_phone"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL_OR_PHONE TEXT NOT NULL UNIQUE,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        // Insert default sample account
        val values = ContentValues().apply {
            put(COLUMN_NAME, "Nguyễn Trần Tài Phương")
            put(COLUMN_EMAIL_OR_PHONE, "nguyentrantaiphuong@gmail.com")
            put(COLUMN_PASSWORD, "12345678")
        }
        db.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Register new user
    fun registerUser(name: String, emailOrPhone: String, password: String, isAgreed: Boolean): RegisterResult {
        if (name.isBlank() || emailOrPhone.isBlank() || password.isBlank()) {
            return RegisterResult.EmptyFields
        }
        if (!isAgreed) {
            return RegisterResult.TermsNotAgreed
        }

        if (checkUserExists(emailOrPhone.trim())) {
            return RegisterResult.AlreadyExists
        }

        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name.trim())
            put(COLUMN_EMAIL_OR_PHONE, emailOrPhone.trim())
            put(COLUMN_PASSWORD, password)
        }

        val id = db.insert(TABLE_USERS, null, values)
        return if (id != -1L) {
            RegisterResult.Success(id)
        } else {
            RegisterResult.Error("Không thể tạo tài khoản vào CSDL.")
        }
    }

    // Login user
    fun loginUser(emailOrPhone: String, password: String): LoginResult {
        if (emailOrPhone.isBlank() || password.isBlank()) {
            return LoginResult.EmptyFields
        }

        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL_OR_PHONE = ?"
        val cursor = db.rawQuery(query, arrayOf(emailOrPhone.trim()))

        return if (cursor.moveToFirst()) {
            val storedPasswordIndex = cursor.getColumnIndex(COLUMN_PASSWORD)
            val storedPassword = cursor.getString(storedPasswordIndex)

            if (storedPassword == password) {
                val idIndex = cursor.getColumnIndex(COLUMN_ID)
                val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
                val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL_OR_PHONE)

                val user = User(
                    id = cursor.getLong(idIndex),
                    name = cursor.getString(nameIndex),
                    emailOrPhone = cursor.getString(emailIndex),
                    password = storedPassword
                )
                cursor.close()
                LoginResult.Success(user)
            } else {
                cursor.close()
                LoginResult.WrongPassword
            }
        } else {
            cursor.close()
            LoginResult.UserNotFound
        }
    }

    // Check if user exists
    fun checkUserExists(emailOrPhone: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ID FROM $TABLE_USERS WHERE $COLUMN_EMAIL_OR_PHONE = ?"
        val cursor = db.rawQuery(query, arrayOf(emailOrPhone.trim()))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // Reset password
    fun updatePassword(emailOrPhone: String, newPassword: String): Boolean {
        if (emailOrPhone.isBlank() || newPassword.isBlank()) return false

        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PASSWORD, newPassword)
        }
        val rows = db.update(TABLE_USERS, values, "$COLUMN_EMAIL_OR_PHONE = ?", arrayOf(emailOrPhone.trim()))
        return rows > 0
    }
}
