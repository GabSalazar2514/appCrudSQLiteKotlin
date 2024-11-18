package com.example.crudbdsqlite;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
SQLiteOpenHelper(context, "vehiculos.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE vehiculo (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                placa TEXT,
                marca TEXT,
                numeroPuertas INTEGER,
                peso REAL
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS vehiculo")
        onCreate(db)
    }

    fun insertarVehiculo(placa: String, marca: String, numeroPuertas: Int, peso: Double): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("placa", placa)
            put("marca", marca)
            put("numeroPuertas", numeroPuertas)
            put("peso", peso)
        }
        return db.insert("vehiculo", null, values)
    }

    fun actualizarVehiculo(id: Int, placa: String, marca: String, numeroPuertas: Int, peso: Double): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("placa", placa)
            put("marca", marca)
            put("numeroPuertas", numeroPuertas)
            put("peso", peso)
        }
        return db.update("vehiculo", values, "id=?", arrayOf(id.toString()))
    }

    fun borrarVehiculo(id: Int): Int {
        val db = writableDatabase
        return db.delete("vehiculo", "id=?", arrayOf(id.toString()))
    }

    fun obtenerVehiculos(): List<String> {
        val db = readableDatabase
        val cursor = db.query("vehiculo", null, null, null, null, null, null)
        val vehiculos = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val placa = cursor.getString(cursor.getColumnIndexOrThrow("placa"))
            val marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"))
            val numeroPuertas = cursor.getInt(cursor.getColumnIndexOrThrow("numeroPuertas"))
            val peso = cursor.getDouble(cursor.getColumnIndexOrThrow("peso"))
            vehiculos.add("ID: $id\nPlaca: $placa\nMarca: $marca\nPuertas: $numeroPuertas\nPeso: $peso")
        }
        cursor.close()
        return vehiculos
    }
}
