package com.ittoluca.lubinpc.practicafinal

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etusuario = findViewById<EditText>(R.id.ETUsuario)
        val etcontra = findViewById<EditText>(R.id.ETConstrana)


        var modeldialog = AlertDialog.Builder(this)
        val Dialogvista = layoutInflater.inflate(R.layout.fotg, null)
        modeldialog.setView(Dialogvista)
        var dialogo = modeldialog.create()
      //  dialogo.setCancelable(false)
       // dialogo.setCanceledOnTouchOutside(false)
        val intent = Intent(this, Botones::class.java)

        dialogo.setOnCancelListener {

            startActivity(intent)
        }

        val baceptar = findViewById<Button>(R.id.LAceptar)
            baceptar.setOnClickListener {
                var usuari: String = etusuario.text.toString()
                var contra: String = etcontra.text.toString()
                if (usuari == "Lubin" && contra == "DesMovil1p") {
                    Toast.makeText(this, "Bienvenido Lubin", Toast.LENGTH_LONG).show()
                    dialogo.show()

                } else {
                    Snackbar.make(it, "Tu usuario y/o contraseña son incorrectos", Snackbar.LENGTH_LONG).show()
                }
            }
            val bcancelar = findViewById<Button>(R.id.LCancelar)
            bcancelar.setOnClickListener {
                Toast.makeText(this, "Hasta luego gracias por visita", Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }
