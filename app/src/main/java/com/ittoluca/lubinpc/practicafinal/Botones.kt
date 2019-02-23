package com.ittoluca.lubinpc.practicafinal

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.io.File
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class Botones : AppCompatActivity(){

    private val PermisoFineLocation= android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCamara=android.Manifest.permission.CAMERA
    private val permisoEscritura=android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val permisoLectura=android.Manifest.permission.READ_EXTERNAL_STORAGE

    val Codigo_Permisos=100

    var fuseLocationLine: FusedLocationProviderClient? = null
    var locationRequest: LocationRequest?=null

    var callback: LocationCallback?=null

    var lat:String=""
    var log:String=""
    var  urlimagen=""

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_botones)
        fuseLocationLine= FusedLocationProviderClient(this)
        inicalicacionLocation()
        val bacerca=findViewById<Button>(R.id.Bacercade)
        bacerca.setOnClickListener{
            var modeldialog= AlertDialog.Builder(this)
            val Dialogvista=layoutInflater.inflate(R.layout.acercami,null)
            modeldialog.setView(Dialogvista)
            var dialogo=modeldialog.create()
            dialogo.show()
        }
        val bweb=findViewById<Button>(R.id.Bweb)
        bweb.setOnClickListener{
            var modeldialog= AlertDialog.Builder(this)
            val Dialogvista=layoutInflater.inflate(R.layout.cuadrodialogo,null)
            var BWeb=Dialogvista.findViewById<Button>(R.id.AceptarDialog)
            var ETweb=Dialogvista.findViewById<EditText>(R.id.ETWeb)
            modeldialog.setView(Dialogvista)
            var dialogo=modeldialog.create()
            dialogo.show()
            BWeb.setOnClickListener {
                val web=ETweb.text.toString()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"+web))
                startActivity(intent)
            }
        }
        val bsuma=findViewById<Button>(R.id.BSuma)
        bsuma.setOnClickListener{operaciones(1)}
        val bresta=findViewById<Button>(R.id.BResta)
        bresta.setOnClickListener{operaciones(2)}
        val bmul=findViewById<Button>(R.id.Bmulti)
        bmul.setOnClickListener{operaciones(3)}
        val bdiv=findViewById<Button>(R.id.Bdiv)
        bdiv.setOnClickListener{operaciones(4)}

        val bcamara=findViewById<Button>(R.id.Bcamara)
        bcamara.setOnClickListener{
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var archivoFoto:File?=null
            archivoFoto=creararchivofoto()
            if(archivoFoto!=null){
                val urfoto= FileProvider.getUriForFile(this,"com.ittoluca.lubinpc.practicafinal",archivoFoto)
                intent.putExtra(MediaStore.EXTRA_OUTPUT,urfoto)
                startActivityForResult(intent,1)
            }

        }


        val bgps=findViewById<Button>(R.id.BGps)
        bgps.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:"+lat+","+log )
            startActivity(intent)
        }
        val bsalir=findViewById<Button>(R.id.Bsalir)
        bsalir.setOnClickListener{
            finish()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intent=Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val file=File(urlimagen)
        val uri= Uri.fromFile(file)
        intent.setData(uri)
        Toast.makeText(this,"La foto se guardo en la carpeta Pictures",Toast.LENGTH_LONG).show()
        this.sendBroadcast(intent)
        val intnt = Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"))
        startActivity(intnt)
    }

    fun operaciones(op:Int){

        var num1=0.0
        var num2=0.0
        var r=0.0
        var b=false
        var modeldialog= AlertDialog.Builder(this)
        val Dialogvista=layoutInflater.inflate(R.layout.operaciones,null)
        var Blim=Dialogvista.findViewById<Button>(R.id.Blim)
        var Bres=Dialogvista.findViewById<Button>(R.id.Bresul)
        var Bcan=Dialogvista.findViewById<Button>(R.id.Bcan)
        var ETnum1=Dialogvista.findViewById<EditText>(R.id.num1)
        var ETnum2=Dialogvista.findViewById<EditText>(R.id.num2)
        var operador=Dialogvista.findViewById<TextView>(R.id.operador)
        var Res=Dialogvista.findViewById<TextView>(R.id.resultado)

        when(op){
            1-> operador.setText("+")
            2-> operador.setText("-")
            3-> operador.setText("x")
            4-> operador.setText("/")
        }
        modeldialog.setView(Dialogvista)
        var dialogo=modeldialog.create()
        dialogo.show()
        Blim.setOnClickListener{
            ETnum1.setText("")
            ETnum2.setText("")
            Res.setText("")
        }
        Bcan.setOnClickListener {  dialogo.cancel()}
        Bres.setOnClickListener {
            try {
                num1=ETnum1.text.toString().toDouble()
                num2=ETnum2.text.toString().toDouble()
            }catch (e:Exception){
                Res.setText("Dato Erroneo")
                b=true
            }
            when(op){
                1->  r=num1+num2
                2->  r=num1-num2
                3->  r=num1*num2
                4-> {
                    if(num2!=0.0)
                        r=num1/num2
                    else{
                        Res.setText("Dato Erroneo")
                        b=true
                    }}
            }
            if(b){
                b=false
            }else{
            Res.setText(r.toString())
        }

        }
    }


    fun creararchivofoto():File? {
        val time=SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val nombreImg="JPEG_"+time+"_"
        //val directorio= getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val directorio=Environment.getExternalStorageDirectory()
        val dir=File(directorio.absolutePath+"/Pictures/")
        val imagen=File.createTempFile(nombreImg,".jpg",dir)
          urlimagen="file://"+imagen.absolutePath
        return imagen
    }


    @SuppressLint("RestrictedApi")
    private fun inicalicacionLocation() {
        locationRequest= LocationRequest()
        locationRequest?.interval=10000
        locationRequest?.fastestInterval=5000
        locationRequest?.priority=LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    override fun onStart() {
        super.onStart()
        if(ValidarPermisos()) {
            ObtenetUbicacion()
        } else {
            //solicitud de permisos
            requestPermissions(arrayOf(PermisoFineLocation,permisoCamara,permisoEscritura,permisoLectura),Codigo_Permisos)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            Codigo_Permisos -> {
                var permiso=true
                for(i in grantResults)
                    permiso=(i==PackageManager.PERMISSION_GRANTED)&&permiso
                if(grantResults.size>0&& permiso)
                    ObtenetUbicacion()
                else
                    Toast.makeText(this,"No diste permiso",Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun ObtenetUbicacion() {
        callback=object:LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                for (ubicacion in p0?.locations!!){
                    lat=ubicacion.latitude.toString()
                    log=ubicacion.longitude.toString()
                    //Toast.makeText(applicationContext,ubicacion.longitude.toString()+" - "+ubicacion.latitude.toString(),Toast.LENGTH_SHORT).show()
                }

            }
        }
        fuseLocationLine?.requestLocationUpdates(locationRequest,callback,null)
    }

    private fun ValidarPermisos(): Boolean {
        return ActivityCompat.checkSelfPermission(this, PermisoFineLocation) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, permisoCamara) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, permisoEscritura) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, permisoLectura) == PackageManager.PERMISSION_GRANTED
    }

    override fun onPause() { super.onPause(); fuseLocationLine?.removeLocationUpdates(callback) }
}
