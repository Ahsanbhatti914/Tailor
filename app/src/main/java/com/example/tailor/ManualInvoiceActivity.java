package com.example.tailor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManualInvoiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView custID,custname,custNumber,dateTv,dueDateTv;
    EditText paidPayment,remainingBalance,quantity1,quantity2,quantity3,quantity4,price1,price2,price3,price4,noteEdt;
    Button generateInvoice,viewInvoice,shareInvoice;
    private String ID, Name, Number,total_Payment,paid_Payment ,suit_Quantity,selected_Date="__-__-____";

    ArrayAdapter<String> adapter;
    String [] itemsDescription = {"none","Kameez Shalwar","Kameez Pajama","Kurta Pajama","Jali","Embroidery","Fancy Button","Designing"};
    Spinner items1Spinner,items2Spinner,items3Spinner,items4Spinner;
    int visited = 0;
    boolean unLocked = false;

    int pageWidth = 1200,pageHeight = 2010;
    int height = 100;
    //int height1 = 50;
    Bitmap bmp,adress,email,phone,scaledbmp,scaledaddress,scaledemail,scaledphone;
    String mPath = "", itemDescription1 = "",itemDescription2 = "",itemDescription3 = "",itemDescription4 = "";
    SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy");
    String curDate = formDate.format(new Date());
    double total1 = 0,total2 = 0,total3 = 0,total4 = 0,remaining = 0,subTotal = 0,Total = 0;
    LinearLayout dueDateLayout;

    int y = 0 ;
    int red = 91,green = 188,blue = 73;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_invoice);


        getIntentData();
        intialization();
        setAdapters();
        setOnItemSelectedListener();
        setCustomerDatails();

        generateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!itemDescription1.equals("none")&& (!price1.getText().toString().equals("") && (!quantity1.getText().toString().equals("")) ||
                        (!itemDescription2.equals("none")&& (!price2.getText().toString().equals("")&&(!quantity2.getText().toString().equals("") ||
                                (!itemDescription3.equals("none")&& (!price3.getText().toString().equals("")&& (!quantity3.getText().toString().equals("")))))))))){
                    createPDF();
                }
                else {
                    Toast.makeText(ManualInvoiceActivity.this, "Some Fields Missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPDF(mPath);
            }
        });
        shareInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePDF(mPath);
            }
        });
        dueDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
    }

    private void setCustomerDatails() {
        custID.setText(ID);
        custname.setText(Name);
        custNumber.setText(Number);
        dateTv.setText(curDate);
    }
    private void intialization() {
        custID = findViewById(R.id.manualInvoiceCustID);
        custname = findViewById(R.id.manualInvoiceCustName);
        custNumber = findViewById(R.id.manualInvoiceCustNumber);
        dateTv = findViewById(R.id.manualInvoiceDate);
        dueDateTv = findViewById(R.id.manualInvoiceDueDate);
        dueDateLayout = findViewById(R.id.manualInvoiceDueDateLayout);
        paidPayment = findViewById(R.id.manualInvoicePaidPayment);
        remainingBalance = findViewById(R.id.manualInvoiceRemainingBalance);
        items1Spinner = findViewById(R.id.manualInvoiceItem1Spinner);
        items2Spinner = findViewById(R.id.manualInvoiceItem2Spinner);
        items3Spinner = findViewById(R.id.manualInvoiceItem3Spinner);
        items4Spinner = findViewById(R.id.manualInvoiceItem4Spinner);
        quantity1 = findViewById(R.id.manualInvoiceEdtQuantity1);
        quantity2 = findViewById(R.id.manualInvoiceEdtQuantity2);
        quantity3 = findViewById(R.id.manualInvoiceEdtQuantity3);
        quantity4 = findViewById(R.id.manualInvoiceEdtQuantity4);
        price1 = findViewById(R.id.manualInvoiceEdtPrice1);
        price2 = findViewById(R.id.manualInvoiceEdtPrice2);
        price3 = findViewById(R.id.manualInvoiceEdtPrice3);
        price4 = findViewById(R.id.manualInvoiceEdtPrice4);
        noteEdt = findViewById(R.id.manualInvoiceNoteEdtText);
        generateInvoice = findViewById(R.id.manualInvoiceGenerateBtn);
        viewInvoice = findViewById(R.id.manualInvoiceViewInvoiceBtn);
        shareInvoice = findViewById(R.id.manualInvoiceShareInvoiceBtn);

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,itemsDescription);

        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.alattari);
        adress = BitmapFactory.decodeResource(getResources(),R.drawable.location);
        email = BitmapFactory.decodeResource(getResources(),R.drawable.email);
        phone = BitmapFactory.decodeResource(getResources(),R.drawable.telephone);
        scaledbmp = Bitmap.createScaledBitmap(bmp,1200,518,false);
        scaledaddress = Bitmap.createScaledBitmap(adress,50,50,false);
        scaledemail = Bitmap.createScaledBitmap(email,45,45,false);
        scaledphone = Bitmap.createScaledBitmap(phone,45,45,false);
    }
    public void setAdapters(){
        items1Spinner.setAdapter(adapter);
        items2Spinner.setAdapter(adapter);
        items3Spinner.setAdapter(adapter);
        items4Spinner.setAdapter(adapter);
    }
    public void setOnItemSelectedListener() {
        items1Spinner.setOnItemSelectedListener(this);
        items2Spinner.setOnItemSelectedListener(this);
        items3Spinner.setOnItemSelectedListener(this);
        items4Spinner.setOnItemSelectedListener(this);
    }
    public void getIntentData(){
        Intent intent = getIntent();
        ID = intent.getStringExtra("CustID");
        Name = intent.getStringExtra("CustName");
        Number = intent.getStringExtra("CustNumber");
    }
    public void createPDF(){
        PdfDocument pdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint titlePaint = new Paint();
        PdfDocument.PageInfo pageInfo1 =  new PdfDocument.PageInfo.Builder(pageWidth,pageHeight,1).create();
        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo1);
        Canvas canvas = page1.getCanvas();
        canvas.drawBitmap(scaledbmp,0,0,myPaint);


        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(35f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Customer ID: " + ID,20,485+height,myPaint);
        canvas.drawText("Contact No: " + Number,20,535+height,myPaint);
        canvas.drawText("Customer Name: " + Name,20,585+height,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Date: " + curDate,840,535+height,myPaint);
        canvas.drawText("Due Date: " + selected_Date,840,585+height,myPaint);
        //canvas.drawText("Status: " + order_Status,850,715+height,myPaint);

        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(2);
        canvas.drawRect(20,690+height,pageWidth-20,770+height,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("Si No: ",40,740+height,myPaint);
        canvas.drawText("Item Description: ",200,740+height,myPaint);
        canvas.drawText("Price: ",700,740+height,myPaint);
        canvas.drawText("Qty: ",900,740+height,myPaint);
        canvas.drawText("Total: ",1050,740+height,myPaint);

        canvas.drawLine(180,700+height,180,750+height,myPaint);
        canvas.drawLine(680,700+height,680,750+height,myPaint);
        canvas.drawLine(880,700+height,880,750+height,myPaint);
        canvas.drawLine(1030,700+height,1030,750+height,myPaint);

        y=810+height;
        int siNo = 1;
        if((!itemDescription1.equals("none")&& (!price1.getText().toString().equals("") && (!quantity1.getText().toString().equals(""))))){
            y = y + 50;
            total1  = Double.parseDouble(price1.getText().toString()) * Double.parseDouble(quantity1.getText().toString());
            canvas.drawText(siNo+".",40,y,myPaint);
            canvas.drawText(itemDescription1,200,y,myPaint);
            canvas.drawText(price1.getText().toString(),700,y,myPaint);
            canvas.drawText(quantity1.getText().toString(),900,y,myPaint);
            canvas.drawText(""+total1,pageWidth-150,y,myPaint);
            siNo++;
        }
        if((!itemDescription2.equals("none")&& (!price2.getText().toString().equals("") && (!quantity2.getText().toString().equals(""))))){
            y = y + 50;
            total2  = Double.parseDouble(price2.getText().toString()) * Double.parseDouble(quantity2.getText().toString());
            canvas.drawText(siNo+".",40,y,myPaint);
            canvas.drawText(itemDescription2,200,y,myPaint);
            canvas.drawText(price2.getText().toString(),700,y,myPaint);
            canvas.drawText(quantity2.getText().toString(),900,y,myPaint);
            canvas.drawText(""+total2,pageWidth-150,y,myPaint);
            siNo++;
        }
        if((!itemDescription3.equals("none")&& (!price3.getText().toString().equals("") && (!quantity3.getText().toString().equals(""))))){
            y = y + 50;
            total3  = Double.parseDouble(price3.getText().toString()) * Double.parseDouble(quantity3.getText().toString());
            canvas.drawText(siNo + ".",40,y,myPaint);
            canvas.drawText(itemDescription3,200,y,myPaint);
            canvas.drawText(price3.getText().toString(),700,y,myPaint);
            canvas.drawText(quantity3.getText().toString(),900,y,myPaint);
            canvas.drawText(""+total3,pageWidth-150,y,myPaint);
            siNo++;
        }
        if((!itemDescription4.equals("none")&& (!price4.getText().toString().equals("") && (!quantity4.getText().toString().equals(""))))){
            y = y + 50;
            total4  = Double.parseDouble(price4.getText().toString()) * Double.parseDouble(quantity4.getText().toString());
            canvas.drawText(siNo + ".",40,y,myPaint);
            canvas.drawText(itemDescription4,200,y,myPaint);
            canvas.drawText(price4.getText().toString(),700,y,myPaint);
            canvas.drawText(quantity4.getText().toString(),900,y,myPaint);
            canvas.drawText(""+total4,pageWidth-150,y,myPaint);
            siNo++;
        }


        //note from tailor
        if(!(noteEdt.getText().toString().equals("") || noteEdt.getText() == null)){
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setColor(Color.BLACK);
            myPaint.setTextSize(32f);
            y = y + 100;
            String [] lines = noteEdt.getText().toString().split("(?<=\\G.{" + 70 + "})");
            for (String line: lines) {
                canvas.drawText("➤ " + line,40,y,myPaint);
                y=y+40;
            }
        }

        if(paidPayment.getText().toString().equals("") || paidPayment.getText() == null){
            paid_Payment = "0";
        }else {
            paid_Payment = paidPayment.getText().toString();
        }

        //Remaining Balance
        if(!remainingBalance.getText().toString().equals("")){
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setColor(Color.BLACK);
            myPaint.setTextSize(32f);
            canvas.drawText("Remaining" ,710,1250+height,myPaint);
            canvas.drawText(":",930,1250+height,myPaint);
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(remainingBalance.getText().toString(),pageWidth-40,1250+height,myPaint);
            remaining = Double.parseDouble(remainingBalance.getText().toString());
        }

        subTotal = total1+total2+total3+total4;

        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawLine(710,1280+height,pageWidth-20,1280+height,myPaint);

        canvas.drawText("Sub total",710,1330+height,myPaint);
        canvas.drawText(":",930,1330+height,myPaint);
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(""+subTotal,pageWidth-40,1330+height,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Paid",710,1380+height,myPaint);
        canvas.drawText(":",930,1380+height,myPaint);
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(paid_Payment,pageWidth-40,1380+height,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setColor(Color.rgb(red,green,blue));
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("Note:",20,1250+height,myPaint);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));

        Paint notePaint = new Paint();
        notePaint.setStyle(Paint.Style.STROKE);
        notePaint.setStrokeWidth(2);
        canvas.drawRect(20,1280+height,620,1500+height,notePaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(28f);
        canvas.drawText("We take the cloth according to your measure,",40,1320+height,myPaint);
        canvas.drawText("but nevertheless some cloth is saved which is",40,1360+height,myPaint);
        canvas.drawText("not avoided for which we want to be  honored",40,1400+height,myPaint);
        canvas.drawText("and forgiveness on the day of resurrection ask",40,1440+height,myPaint);
        canvas.drawText("for Thank you",40,1480+height,myPaint);

        myPaint.setColor(Color.rgb(red,green,blue));
        canvas.drawRect(710,1430+height,pageWidth-20,1500+height,myPaint);

        Total = (subTotal - Double.parseDouble(paid_Payment) + remaining);
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(50f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Total",730,1485+height,myPaint);
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(""+Total,pageWidth-40,1485+height,myPaint);

        canvas.drawLine(20,1600+height,pageWidth-20,1600+height,myPaint);
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(32f);
        canvas.drawText("0311 4400424",100,1650+height,myPaint);
        canvas.drawText("ahm4400424@gmail.com",100,1720+height,myPaint);
        canvas.drawText("Main Bazar Rana Town, Madina Chowk, Multan Road Lahore.",100,1790+height,myPaint);

        canvas.drawBitmap(scaledphone,20,1620+height,myPaint);
        canvas.drawBitmap(scaledemail,20,1690+height,myPaint);
        canvas.drawBitmap(scaledaddress,20,1760+height,myPaint);

        pdfDocument.finishPage(page1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            mPath= this.getExternalFilesDir(null).toString() + "/" + ID + "_" + Name +"_BillInvoice.pdf";
        }
        else
        {
            mPath= Environment.getExternalStorageDirectory().toString() +"/"+ID+"_"+Name+ "_BillInvoice.pdf";
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
    }
    public void viewPDF(String path){

        try {
            File file = new File(path);
            if(file.exists()){
                Uri fileUri= FileProvider.getUriForFile(ManualInvoiceActivity.this,
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
                Uri fileUri= FileProvider.getUriForFile(ManualInvoiceActivity.this,
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(unLocked)
        {
            if(parent == items1Spinner) {
                itemDescription1 = parent.getItemAtPosition(position).toString();
            }
            else if(parent == items2Spinner) {
                itemDescription2 = parent.getItemAtPosition(position).toString();
            }
            else if(parent == items3Spinner) {
                itemDescription3 = parent.getItemAtPosition(position).toString();
            }
            else if(parent == items4Spinner) {
                itemDescription4 = parent.getItemAtPosition(position).toString();
            }
        }
        else {
            visited++;

            if(visited == 4){
                if(itemDescription1 == null){
                    itemDescription1 = "none";
                }
                if(itemDescription2 == null){
                    itemDescription2 = "none";
                }
                if(itemDescription3 == null){
                    itemDescription3 = "none";
                }
                if(itemDescription4 == null){
                    itemDescription4 = "none";
                }
            }
            unLocked = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void pickDate(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ManualInvoiceActivity.this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
//                days = String.valueOf(dayOfMonth);
//                months = String.valueOf(year);
//                years = String.valueOf(year);
                dueDateTv.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                selected_Date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
            }
        }, year, month, day);

        datePickerDialog.show();
    }


}