package com.example.crudbdsqlite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudbdsqlite.SQLiteHelper
import com.example.crudbdsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = SQLiteHelper(this)

        mostrarVehiculos()

        binding.btnCrear.setOnClickListener {
            val placa = binding.etPlaca.text.toString()
            val marca = binding.etMarca.text.toString()
            val numeroPuertas = binding.etNumeroPuertas.text.toString().toIntOrNull()
            val peso = binding.etPeso.text.toString().toDoubleOrNull()

            if (placa.isNotBlank() && marca.isNotBlank() && numeroPuertas != null && peso != null) {
                dbHelper.insertarVehiculo(placa, marca, numeroPuertas, peso)
                Toast.makeText(this, "Vehículo creado", Toast.LENGTH_SHORT).show()

                mostrarVehiculos()
            } else {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }


        // Actualizar vehículo
        binding.btnActualizar.setOnClickListener {
            val id = binding.etId.text.toString().toIntOrNull()
            val placa = binding.etPlaca.text.toString()
            val marca = binding.etMarca.text.toString()
            val numeroPuertas = binding.etNumeroPuertas.text.toString().toIntOrNull()
            val peso = binding.etPeso.text.toString().toDoubleOrNull()

            if (id != null && placa.isNotBlank() && marca.isNotBlank() && numeroPuertas != null && peso != null) {
                val rowsUpdated = dbHelper.actualizarVehiculo(id, placa, marca, numeroPuertas, peso)
                if (rowsUpdated > 0) {
                    Toast.makeText(this, "Vehículo actualizado", Toast.LENGTH_SHORT).show()

                    mostrarVehiculos()
                } else {
                    Toast.makeText(this, "No se encontró el vehículo con ID: $id", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }

        // Borrar vehículo
        binding.btnBorrar.setOnClickListener {
            val id = binding.etId.text.toString().toIntOrNull()

            if (id != null) {
                val rowsDeleted = dbHelper.borrarVehiculo(id)
                if (rowsDeleted > 0) {
                    Toast.makeText(this, "Vehículo borrado", Toast.LENGTH_SHORT).show()

                    mostrarVehiculos()
                } else {
                    Toast.makeText(this, "No se encontró el vehículo con ID: $id", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor ingresa un ID válido", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnConsultar.setOnClickListener {
            mostrarVehiculos()
        }

    }

    fun mostrarVehiculos() {
        val vehiculos = dbHelper.obtenerVehiculos()
        binding.tvResultados.text = vehiculos.joinToString("\n")
    }
}
