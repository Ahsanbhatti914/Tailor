package com.example.tailor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tailor.Fragments.PendingOrderFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MeasurementsActivity extends AppCompatActivity implements View.OnClickListener {
    String ID="",Name="",Number="",kameez="",kameezFrac="",tera="",teraFrac="",bazo="",bazoFrac="",moda="",modaFrac="",chati="",chatiFrac="",fitting="",fittingFrac="",gera="",geraFrac="",gala="",galaFrac="",shalwar="",shalwarFrac="",poncha="",ponchaFrac="",fair="",fairFrac="",asan="",asanFrac="",trouser="",trouserFrac="",trouserPoncha="",trouserPonchaFrac="",trouserFair="",trouserFairFrac="",trouserAsan="",trouserAsanFrac="",collar,collarWidth="",fracCollarWidth="",collarLength="",fracCollarLength="",kaff="",kaffWidth="",fracKaffWidth="",kaffLength="",fracKaffLength="",salaei="",gheera="",dhaga="",frontPocket = "",sidePocket  = "",shalwarPocket = "",Location;
    TextView tvID,tvName,tvNumber,tvKameez,tvKameezFrac,tvTera,tvTeraFrac,tvBazo,tvBazoFrac,tvModa,tvModaFrac,tvChati,tvChatiFrac,tvFitting,tvFittingFrac,tvGera,tvGeraFrac,tvGala,tvGalaFrac,tvShalwar,tvShalwarFrac,tvPoncha,tvPonchaFrac,tvFair,tvFairFrac,tvAsan,tvAsanFrac,tvTrouser,tvTrouserFrac,tvTrouserPoncha,tvTrouserPonchaFrac,tvTrouserFair,tvTrouserFairFrac,tvTrouserAsan,tvTrouserAsanFrac,collarTv,kaffTv,salaeiTv,gheeraTv,dhagaTv,frontPocketTv,sidePocketTv,shalwarPocketTv;
    Button createPdfBtn,sharePdfBtn,viewPdfBtn,sendSmsBtn;
    TextView sendSmsTv;
    int pageWidth = 1200,pageHeight = 2610;
    int height = 70;
    int x = 0,x1 = 0,y = 0;
    Bitmap bmp,adress,email,phone,scaledbmp,scaledaddress,scaledemail,scaledphone;
    String mPath = "";
    SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy");
    String curDate = formDate.format(new Date());
    int red = 91,green = 188,blue = 73;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements);

        fetchIntentData();
        Initialization();
        setOnItemSelectedListener();
        setTextToViews();
    }

    public void fetchIntentData(){
        Intent intent = getIntent();
        if (intent != null){
            ID = intent.getStringExtra("CustID");
            Name = intent.getStringExtra("CustName");
            Number = intent.getStringExtra("CustNumber");
            kameez = intent.getStringExtra("CustKameez");
            kameezFrac = intent.getStringExtra("CustKameezFrac");
            tera = intent.getStringExtra("CustTera");
            teraFrac = intent.getStringExtra("CustTeraFrac");
            bazo = intent.getStringExtra("CustBazo");
            bazoFrac = intent.getStringExtra("CustBazoFrac");
            moda = intent.getStringExtra("CustModa");
            modaFrac = intent.getStringExtra("CustModaFrac");
            chati = intent.getStringExtra("CustChati");
            chatiFrac = intent.getStringExtra("CustChatiFrac");
            fitting = intent.getStringExtra("CustFitting");
            fittingFrac = intent.getStringExtra("CustFittingFrac");
            gera = intent.getStringExtra("CustGera");
            geraFrac = intent.getStringExtra("CustGeraFrac");
            gala = intent.getStringExtra("CustGala");
            galaFrac = intent.getStringExtra("CustGalaFrac");
            shalwar = intent.getStringExtra("CustShalwar");
            shalwarFrac = intent.getStringExtra("CustShalwarFrac");
            poncha = intent.getStringExtra("CustPoncha");
            ponchaFrac = intent.getStringExtra("CustPonchaFrac");
            fair = intent.getStringExtra("CustFair");
            fairFrac = intent.getStringExtra("CustFairFrac");
            asan = intent.getStringExtra("CustAsan");
            asanFrac = intent.getStringExtra("CustAsanFrac");
            trouser = intent.getStringExtra("CustTrouser");
            trouserFrac = intent.getStringExtra("CustTrouserFrac");
            trouserPoncha = intent.getStringExtra("CustTrouserPoncha");
            trouserPonchaFrac = intent.getStringExtra("CustTrouserPonchaFrac");
            trouserFair = intent.getStringExtra("CustTrouserFair");
            trouserFairFrac = intent.getStringExtra("CustTrouserFairFrac");
            trouserAsan = intent.getStringExtra("CustTrouserAsan");
            trouserAsanFrac = intent.getStringExtra("CustTrouserAsanFrac");
            collar = intent.getStringExtra("CustCollar");
            collarWidth = intent.getStringExtra("CustCollarWidth");
            fracCollarWidth = intent.getStringExtra("CustCollarWidthFrac");
            collarLength = intent.getStringExtra("CustCollarLength");
            fracCollarLength = intent.getStringExtra("CustCollarLengthFrac");
            kaff = intent.getStringExtra("CustKaff");
            kaffWidth = intent.getStringExtra("CustKaffWidth");
            fracKaffWidth = intent.getStringExtra("CustKaffWidthFrac");
            kaffLength = intent.getStringExtra("CustKaffLength");
            fracKaffLength = intent.getStringExtra("CustKaffLengthFrac");
            salaei = intent.getStringExtra("CustSalaei");
            gheera = intent.getStringExtra("CustGheera");
            dhaga = intent.getStringExtra("CustDhaga");
            frontPocket = intent.getStringExtra("frontPocket");
            sidePocket = intent.getStringExtra("sidePocket");
            shalwarPocket = intent.getStringExtra("shalwarPocket");
            Location = intent.getStringExtra("LOCATION");
        }
    }
    public void Initialization(){
        tvID = findViewById(R.id.customerIDMeasure);
        tvName = findViewById(R.id.customerNameMeasure);
        tvNumber = findViewById(R.id.customerNumberMeasure);
        tvKameez = findViewById(R.id.kameeSizeMeasure);
        tvKameezFrac = findViewById(R.id.kameeFracMeasure);
        tvTera = findViewById(R.id.teraSizeMeasure);
        tvTeraFrac = findViewById(R.id.teraFracMeasure);
        tvBazo = findViewById(R.id.bazoSizeMeasure);
        tvBazoFrac = findViewById(R.id.bazoFracMeasure);
        tvModa = findViewById(R.id.modaSizeMeasure);
        tvModaFrac = findViewById(R.id.modaFracMeasure);
        tvChati = findViewById(R.id.chatiSizeMeasure);
        tvChatiFrac = findViewById(R.id.chatiFracMeasure);
        tvFitting = findViewById(R.id.fittingSizeMeasure);
        tvFittingFrac = findViewById(R.id.fittingFracMeasure);
        tvGera = findViewById(R.id.geraSizeMeasure);
        tvGeraFrac = findViewById(R.id.geraFracMeasure);
        tvGala = findViewById(R.id.galaSizeMeasure);
        tvGalaFrac = findViewById(R.id.galaFracMeasure);
        tvShalwar = findViewById(R.id.shalwarSizeMeasure);
        tvShalwarFrac = findViewById(R.id.shalwarFracMeasure);
        tvPoncha = findViewById(R.id.ponchaSizeMeasure);
        tvPonchaFrac = findViewById(R.id.ponchaFracMeasure);
        tvFair = findViewById(R.id.fairSizeMeasure);
        tvFairFrac = findViewById(R.id.fairFracMeasure);
        tvAsan = findViewById(R.id.asanSizeMeasure);
        tvAsanFrac = findViewById(R.id.asanFracMeasure);
        tvTrouser = findViewById(R.id.trouserSizeMeasure);
        tvTrouserFrac = findViewById(R.id.trouserFracMeasure);
        tvTrouserPoncha  =findViewById(R.id.trouserPonchaSizeMeasure);
        tvTrouserPonchaFrac = findViewById(R.id.trouserPonchaFracMeasure);
        tvTrouserFair = findViewById(R.id.trouserFairSizeMeasure);
        tvTrouserFairFrac = findViewById(R.id.trouserFairFracMeasure);
        tvTrouserAsan = findViewById(R.id.trouserAsanSizeMeasure);
        tvTrouserAsanFrac = findViewById(R.id.trouserAsanFracMeasure);
        collarTv = findViewById(R.id.collarTvMeasure);
        kaffTv = findViewById(R.id.kaffTvMeasure);
        salaeiTv = findViewById(R.id.salaeiTvMeasure);
        gheeraTv = findViewById(R.id.gheeraTvMeasure);
        dhagaTv = findViewById(R.id.dhagaTvMeasure);
        frontPocketTv = findViewById(R.id.frontPocketTvMeasure);
        sidePocketTv = findViewById(R.id.sidePocketTvMeasure);
        shalwarPocketTv = findViewById(R.id.shalwarPocketTvMeasure);

        createPdfBtn = findViewById(R.id.createPdfBtnMeasure);
        viewPdfBtn = findViewById(R.id.viewPdfBtnMeasure);
        sharePdfBtn = findViewById(R.id.sharePdfBtnMeasure);
        sendSmsBtn = findViewById(R.id.sendSmsBtnMeasure);

        linearLayout = findViewById(R.id.mainLayoutMeasurements);
        linearLayout.getBackground().setAlpha(25);

        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.alattari);
        adress = BitmapFactory.decodeResource(getResources(),R.drawable.location);
        email = BitmapFactory.decodeResource(getResources(),R.drawable.email);
        phone = BitmapFactory.decodeResource(getResources(),R.drawable.telephone);
        scaledbmp = Bitmap.createScaledBitmap(bmp,1200,518,false);
        scaledaddress = Bitmap.createScaledBitmap(adress,50,50,false);
        scaledemail = Bitmap.createScaledBitmap(email,45,45,false);
        scaledphone = Bitmap.createScaledBitmap(phone,45,45,false);
    }

    public  void setOnItemSelectedListener(){
        createPdfBtn.setOnClickListener(this);
        viewPdfBtn.setOnClickListener(this);
        sharePdfBtn.setOnClickListener(this);
        sendSmsBtn.setOnClickListener(this);
    }
    private void setTextToViews() {
        tvID.setText(ID);
        tvName.setText(Name);
        tvNumber.setText(Number);
        tvKameez.setText(kameez);
        tvKameezFrac.setText(kameezFrac);
        tvTera.setText(tera);
        tvTeraFrac.setText(teraFrac);
        tvBazo.setText(bazo);
        tvBazoFrac.setText(bazoFrac);
        tvModa.setText(moda);
        tvModaFrac.setText(modaFrac);
        tvChati.setText(chati);
        tvChatiFrac.setText(chatiFrac);
        tvFitting.setText(fitting);
        tvFittingFrac.setText(fittingFrac);
        tvGera.setText(gera);
        tvGeraFrac.setText(geraFrac);
        tvGala.setText(gala);
        tvGalaFrac.setText(galaFrac);
        tvShalwar.setText(shalwar);
        tvShalwarFrac.setText(shalwarFrac);
        tvPoncha.setText(poncha);
        tvPonchaFrac.setText(ponchaFrac);
        tvFair.setText(fair);
        tvFairFrac.setText(fairFrac);
        tvAsan.setText(asan);
        tvAsanFrac.setText(asanFrac);
        tvTrouser.setText(trouser);
        tvTrouserFrac.setText(trouserFrac);
        tvTrouserPoncha.setText(trouserPoncha);
        tvTrouserPonchaFrac.setText(trouserPonchaFrac);
        tvTrouserFair.setText(trouserFair);
        tvTrouserFairFrac.setText(trouserFairFrac);
        tvTrouserAsan.setText(trouserAsan);
        tvTrouserAsanFrac.setText(trouserAsanFrac);
        collarTv.setText(collar);
        kaffTv.setText(kaff);
        salaeiTv.setText(salaei);
        gheeraTv.setText(gheera);
        dhagaTv.setText(dhaga);
        frontPocketTv.setText(frontPocket);
        sidePocketTv.setText(sidePocket);
        shalwarPocketTv.setText(shalwarPocket);
    }

    @Override
    public void onClick(View v) {

        if(v == createPdfBtn){
            createPDF();
        } else if (v == viewPdfBtn) {
            viewPDF(mPath);
        } else if (v == sharePdfBtn) {
            sharePDF(mPath);
        } else if (v == sendSmsBtn) {
            sensSmsDialog();
        }

    }

    public void createPDF(){

        try{

            PdfDocument pdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            Paint titlePaint = new Paint();
            PdfDocument.PageInfo pageInfo1 =  new PdfDocument.PageInfo.Builder(pageWidth,pageHeight,1).create();
            PdfDocument.Page page1 = pdfDocument.startPage(pageInfo1);
            Canvas canvas = page1.getCanvas();
            canvas.drawBitmap(scaledbmp,0,0,myPaint);

//            titlePaint.setTextAlign(Paint.Align.LEFT);
//            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
//            titlePaint.setTextSize(70);
//            titlePaint.setColor(Color.rgb(red,green,blue));
//            canvas.drawText("Al Attari Fabrics & Stichers",20,650,titlePaint);


            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTextSize(35f);
            myPaint.setColor(Color.BLACK);
            canvas.drawText("Customer ID: " + ID,20,565+height,myPaint);
            canvas.drawText("Contact No: " + Number,20,615+height,myPaint);
            canvas.drawText("Customer Name: " + Name,20,665+height,myPaint);



            myPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Date: " + curDate,850,665+height,myPaint);
            //canvas.drawText("Status: " + "Pending",850,665+height,myPaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC));
            titlePaint.setTextSize(70);
            titlePaint.setColor(Color.rgb(red,green,blue));
            canvas.drawText("MEASUREMENTS",600,780+height,titlePaint);

            x = 265;
            x1 = 850;
            y = height + 850;  //150+850 = 1000
            //String over here for measurements
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTextSize(35f);
            myPaint.setColor(Color.BLACK);

            if(!tvKameez.getText().toString().equals("")){
                if(tvKameezFrac.getText().toString().equals("")){
                    canvas.drawText("Kameez",x,y,myPaint);canvas.drawText(kameez,x1,y,myPaint);
                }else {
                    canvas.drawText("Kameez",x,y,myPaint);canvas.drawText(kameez+" , "+kameezFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvTera.getText().toString().equals("")){
                if(tvTeraFrac.getText().toString().equals("")){
                    canvas.drawText("Tera",x,y,myPaint);canvas.drawText(tera,x1,y,myPaint);
                }else {
                    canvas.drawText("Tera",x,y,myPaint);canvas.drawText(tera+" , "+teraFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvBazo.getText().toString().equals("")){
                if(tvBazoFrac.getText().toString().equals("")){
                    canvas.drawText("Bazo",x,y,myPaint);canvas.drawText(bazo,x1,y,myPaint);
                }else {
                    canvas.drawText("Bazo",x,y,myPaint);canvas.drawText(bazo+" , "+bazoFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvModa.getText().toString().equals("")){
                if(tvModaFrac.getText().toString().equals("")){
                    canvas.drawText("Kandha",x,y,myPaint);canvas.drawText(moda,x1,y,myPaint);
                }else {
                    canvas.drawText("Kandha",x,y,myPaint);canvas.drawText(moda+" , "+modaFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvChati.getText().toString().equals("")){
                if(tvChatiFrac.getText().toString().equals("")){
                    canvas.drawText("Chhati",x,y,myPaint);canvas.drawText(chati,x1,y,myPaint);
                }else {
                    canvas.drawText("Chhati",x,y,myPaint);canvas.drawText(chati+" , "+chatiFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvFitting.getText().toString().equals("")){
                if(tvFairFrac.getText().toString().equals("")){
                    canvas.drawText("Fitting",x,y,myPaint);canvas.drawText(fitting,x1,y,myPaint);
                }else {
                    canvas.drawText("Fitting",x,y,myPaint);canvas.drawText(fitting+" , "+fittingFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvGera.getText().toString().equals("")){
                if(tvGeraFrac.getText().toString().equals("")){
                    canvas.drawText("Ghera",x,y,myPaint);canvas.drawText(gera,x1,y,myPaint);
                }else {
                    canvas.drawText("Ghera",x,y,myPaint);canvas.drawText(geraFrac+" , "+geraFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvGala.getText().toString().equals("")){
                if(tvGalaFrac.getText().toString().equals("")){
                    canvas.drawText("Gala",x,y,myPaint);canvas.drawText(gala,x1,y,myPaint);
                }else {
                    canvas.drawText("Gala",x,y,myPaint);canvas.drawText(gala+" , "+galaFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvShalwar.getText().toString().equals("")){
                if(tvShalwarFrac.getText().toString().equals("")){
                    canvas.drawText("Shalwar",x,y,myPaint);canvas.drawText(shalwar,x1,y,myPaint);
                }else {
                    canvas.drawText("Shalwar",x,y,myPaint);canvas.drawText(shalwar+" , "+shalwarFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvPoncha.getText().toString().equals("")){
                if(tvPonchaFrac.getText().toString().equals("")){
                    canvas.drawText("Poncha",x,y,myPaint);canvas.drawText(poncha,x1,y,myPaint);
                }else {
                    canvas.drawText("Poncha",x,y,myPaint);canvas.drawText(poncha+" , "+ponchaFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvFair.getText().toString().equals("")){
                if(tvFairFrac.getText().toString().equals("")){
                    canvas.drawText("Fair",x,y,myPaint);canvas.drawText(fair,x1,y,myPaint);
                }else {
                    canvas.drawText("Fair",x,y,myPaint);canvas.drawText(fair+" , "+fairFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvAsan.getText().toString().equals("")){
                if(tvAsanFrac.getText().toString().equals("")){
                    canvas.drawText("Asan",x,y,myPaint);canvas.drawText(asan,x1,y,myPaint);
                }else {
                    canvas.drawText("Asan",x,y,myPaint);canvas.drawText(asan+" , "+asanFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvTrouser.getText().toString().equals("")){
                if(tvTrouserFrac.getText().toString().equals("")){
                    canvas.drawText("Trouser",x,y,myPaint);canvas.drawText(trouser,x1,y,myPaint);
                }else {
                    canvas.drawText("Trouser",x,y,myPaint);canvas.drawText(trouser+" , "+trouserFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvTrouserPoncha.getText().toString().equals("")){
                if(tvTrouserPonchaFrac.getText().toString().equals("")){
                    canvas.drawText("Trouser Poncha",x,y,myPaint);canvas.drawText(trouserPoncha,x1,y,myPaint);
                }else {
                    canvas.drawText("Trouser Poncha",x,y,myPaint);canvas.drawText(trouserPoncha+" , "+trouserPonchaFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvTrouserFair.getText().toString().equals("")){
                if(tvTrouserFairFrac.getText().toString().equals("")){
                    canvas.drawText("Trouser Fair",x,y,myPaint);canvas.drawText(trouserFair,x1,y,myPaint);
                }else {
                    canvas.drawText("Trouser Fair",x,y,myPaint);canvas.drawText(trouserFair+" , "+trouserFairFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!tvTrouserAsan.getText().toString().equals("")){
                if(tvTrouserAsanFrac.getText().toString().equals("")){
                    canvas.drawText("Trouser Asan",x,y,myPaint);canvas.drawText(trouserAsan,x1,y,myPaint);
                }else {
                    canvas.drawText("Trouser Asan",x,y,myPaint);canvas.drawText(trouserAsan+" , "+trouserAsanFrac,x1,y,myPaint);
                }
                y = y + 50;
            }
            if(!collarTv.getText().toString().equals("")){
                canvas.drawText("Collar Type",x,y,myPaint);canvas.drawText(collar,x1,y,myPaint);
                y = y + 50;
            }
            if(!kaffTv.getText().toString().equals("")){
                canvas.drawText("Kaff Type",x,y,myPaint);canvas.drawText(kaff,x1,y,myPaint);
                y = y + 50;
            }
            if(!salaeiTv.getText().toString().equals("")){
                canvas.drawText("Salaei",x,y,myPaint);canvas.drawText(salaei,x1,y,myPaint);
                y = y + 50;
            }
            if(!gheeraTv.getText().toString().equals("")){
                canvas.drawText("Gheera",x,y,myPaint);canvas.drawText(gheera,x1,y,myPaint);
                y = y + 50;
            }
            if(!dhagaTv.getText().toString().equals("")){
                canvas.drawText("Dhaga",x,y,myPaint);canvas.drawText(dhaga,x1,y,myPaint);
                y = y + 50;
            }
            if(!frontPocketTv.getText().toString().equals("none")){
                canvas.drawText("Front Pocket",x,y,myPaint);canvas.drawText(frontPocket,x1,y,myPaint);
                y = y + 50;
            }
            if(!sidePocketTv.getText().toString().equals("none")){
                canvas.drawText("Side Pocket",x,y,myPaint);canvas.drawText(sidePocket,x1,y,myPaint);
                y = y + 50;
            }
            if(!shalwarPocketTv.getText().toString().equals("none")){
                canvas.drawText("Shalwar Pocket",x,y,myPaint);canvas.drawText(shalwarPocket,x1,y,myPaint);
                //y = y + 50;
            }

            //drwaing left marginal line
            myPaint.setColor(Color.rgb(red,green,blue));
            canvas.drawRect(100,830+height,115,y,myPaint);

            //2320
            y = 2120;

            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
            Paint notePaint = new Paint();
            notePaint.setStyle(Paint.Style.STROKE);
            notePaint.setStrokeWidth(2);
            canvas.drawRect(20,y,pageWidth-20,y+140,notePaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setColor(Color.BLACK);
            myPaint.setTextSize(28f);
            //90+40
            y = y + 40;
            canvas.drawText("We take the cloth according to your measure, but nevertheless, some cloth is saved which",40,y,myPaint);
            y = y + 40;
            canvas.drawText("is not avoided for which we want to be  honored and forgiveness on the day of ",40,y,myPaint);
            y = y + 40;
            canvas.drawText("resurrection ask for Thank you",40,y,myPaint);


            y = y + 50;
            canvas.drawLine(20,y,pageWidth-20,y,myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTextSize(32f);
            y = y + 50;
            canvas.drawBitmap(scaledphone,20,y-30,myPaint);
            canvas.drawText("0311 4400424",100,y,myPaint);
            y = y + 70;
            canvas.drawBitmap(scaledemail,20,y-30,myPaint);
            canvas.drawText("ahm4400424@gmail.com",100,y,myPaint);
            y = y + 70;
            canvas.drawBitmap(scaledaddress,20,y-30,myPaint);
            canvas.drawText("Main Bazar Rana Town, Madina Chowk, Multan Road Lahore.",100,y,myPaint);

            pdfDocument.finishPage(page1);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mPath= this.getExternalFilesDir(null).toString() + "/" + ID + "_" + Name +"_MeasurementInvoice.pdf";
            }
            else
            {
                mPath= Environment.getExternalStorageDirectory().toString() + "/" + ID + "_" + Name +"_MeasurementInvoice.pdf";
            }

            File file = new File(mPath);
            try {
                pdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(this, "PDF created", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("mTAG", "Exception " + mPath);
            }
            pdfDocument.close();

        }catch (Exception e){
            Log.d("ex", "createPDF: " + e);
        }
    }

    public void viewPDF(String path){

        try {
            File file = new File(path);
            if(file.exists()){
                Uri fileUri= FileProvider.getUriForFile(MeasurementsActivity.this,
                        this.getApplicationContext()
                                .getPackageName()+ ".provider", file);
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(fileUri,"application/pdf");
                target.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent intent = Intent.createChooser(target, "Open File");
                this.startActivity(intent);
            }
            else {
                Toast.makeText(this, "File does not exist!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("mTAG1", "Exception in viewPDF: " + e);
        }
    }

    public void sharePDF(String path){
        try{
            File file = new File(path);
            if(file.exists()){
                Uri fileUri= FileProvider.getUriForFile(MeasurementsActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider", file);
                Intent shareFile = new Intent();
                shareFile.setAction(Intent.ACTION_SEND);
                shareFile.setType("application/pdf");
                shareFile.putExtra(Intent.EXTRA_STREAM,fileUri);
                startActivity(shareFile);
            }else {
                Toast.makeText(this, "File does not exist!!!", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e) {
            Log.d("mTAG2", "Exception " + e);
        }
    }

    public void sensSmsDialog(){
        try{

            final Dialog dialog = new Dialog(this, android.R.style.Theme_Light);
            dialog.setContentView(R.layout.sms_send_dialog);
            dialog.setTitle("SMS Details");

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;

            sendSmsTv = dialog.findViewById(R.id.smsTvDialogMeasure);
            Button sendSms = dialog.findViewById(R.id.sendSmsDialogMeaure);
            Button cancelSms = dialog.findViewById(R.id.cancelSmsDialogMeaure);


            String sms = "\t\tMeasurements" +
                    "\nKameez -:- " + tvKameez.getText().toString() + " , " + tvKameezFrac.getText().toString() +
                    "\nTera -:- " + tvTera.getText().toString() + " , " + tvTeraFrac.getText().toString() +
                    "\nBazo -:- " + tvBazo.getText().toString() + " , " + tvBazoFrac.getText().toString() +
                    "\nKandha -:- " + tvModa.getText().toString() + " , " + tvModaFrac.getText().toString() +
                    "\nChhati -:- " + tvChati.getText().toString() + " , "+ tvChatiFrac.getText().toString() +
                    "\nFitting -:- " + tvFitting.getText().toString() + " , " +tvFittingFrac.getText().toString() +
                    "\nGheera -:- " + tvGera.getText().toString() + " , " + tvGeraFrac.getText().toString() +
                    "\nGala -:- " + tvGala.getText().toString() + " , " + tvGalaFrac.getText().toString() +
                    "\n\nShalwar -:- " + tvShalwar.getText().toString() + " , " + tvShalwarFrac.getText().toString() +
                    "\nPoncha -:- " + tvPoncha.getText().toString() + " , " + tvPonchaFrac.getText().toString() +
                    "\nFair -:- " + tvFair.getText().toString() + " , " + tvFairFrac.getText().toString() +
                    "\nAsan -:- " + tvAsan.getText().toString() + " , " + tvAsanFrac.getText().toString() +
                    "\n\nTrouser -:- "+ tvTrouser.getText().toString() + " , " + tvTrouserFrac.getText().toString() +
                    "\nPoncha -:- " + tvTrouserPoncha.getText().toString() + " , " + tvTrouserPonchaFrac.getText().toString() +
                    "\nFair -:- " + tvTrouserFair.getText().toString() + " , " + tvTrouserFairFrac.getText().toString() +
                    "\nAsan -:- " + tvTrouserAsan.getText().toString() + " , " + tvTrouserAsanFrac.getText().toString() +
                    "\n\nCollar -:- " + collar +
                    "\nKaff -:- " + kaff +
                    "\nSalaei -:- " + salaei +
                    "\nGheera -:- " + gheera +
                    "\nDhaga -:- " + dhaga +
                    "\n\nFront Pocket -:- " + frontPocketTv.getText().toString() +
                    "\nSide Pocket -:- " + sidePocketTv.getText().toString() +
                    "\nShalwar Pocket -:- "+ shalwarPocketTv.getText().toString() +
                    "\n\n\n Al Attari Fabrics & Stichers";


            sendSmsTv.setText(sms);

            sendSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(), "Message sending...", Toast.LENGTH_SHORT).show();
                    sendSms(sms,Number);

                }
            });

            cancelSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            dialog.show();
            dialog.getWindow().setAttributes(lp);

        }catch (Exception e){
            Log.d("excp", "sensSmsDialog: " + e);
        }
    }

    public void sendSms(String sms,String number){
        try {

            SmsManager smsManager = SmsManager.getDefault();

            ArrayList<String> parts = smsManager.divideMessage(sendSmsTv.getText().toString());
            ArrayList<PendingIntent> sentIntents = new ArrayList<>();
            ArrayList<PendingIntent> deliveryIntents = new ArrayList<>();

            for (int i = 0; i < parts.size(); i++) {
                sentIntents.add(null);
                deliveryIntents.add(null);
            }

            smsManager.sendMultipartTextMessage(Number.trim(), null, parts, sentIntents, deliveryIntents);


        }catch (Exception e){
            Toast.makeText(this, "Exception " + e, Toast.LENGTH_SHORT).show();
        }
    }
}