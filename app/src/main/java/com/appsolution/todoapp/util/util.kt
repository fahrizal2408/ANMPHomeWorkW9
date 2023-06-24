package com.appsolution.todoapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.appsolution.todoapp.model.TodoDatabase

val DB_Name="tododb"

val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null");
    }
}
val MIGRATION_2_3 = object: Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 not null"); // SQLite tidak dapat menggunakan tipe data boolean, sehingga apabila ada kolom dengan tipe boolean maka true dan false akan diganti dengan 0 untuk false dan 1 untuk true
    }
}
fun buildDB(context:Context):TodoDatabase{
    val db= Room.databaseBuilder(context, TodoDatabase::class.java, DB_Name).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
    return db
}