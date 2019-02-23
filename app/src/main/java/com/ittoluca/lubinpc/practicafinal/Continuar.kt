package com.ittoluca.lubinpc.practicafinal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.Toast

class Continuar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continuar)
        val bcon=findViewById<Button>(R.id.Bcontinua)
        bcon.setOnClickListener{
            val intent= Intent(this,Botones::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this,"Bienvenido Lubin",Toast.LENGTH_LONG).show()
    }
}
