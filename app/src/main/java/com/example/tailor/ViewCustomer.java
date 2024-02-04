package com.example.tailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tailor.Utils.PendingOrderUtils;
import com.example.tailor.Utils.CustomerUtils;
import com.example.tailor.Models.OrderDetails;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//this class is used to show all the details of a selected customer from the list.

public class ViewCustomer extends AppCompatActivity implements View.OnClickListener {
    TextView tvID,tvName,tvNumber,tvKameez,tvKameezFrac,tvTera,tvTeraFrac,tvBazo,tvBazoFrac,tvModa,tvModaFrac,tvChati,tvChatiFrac,tvFitting,tvFittingFrac,tvGera,tvGeraFrac,tvGala,tvGalaFrac,tvShalwar,tvShalwarFrac,tvPoncha,tvPonchaFrac,tvFair,tvFairFrac,tvAsan,tvAsanFrac,tvTrouser,tvTrouserFrac,tvTrouserPoncha,tvTrouserPonchaFrac,tvTrouserFair,tvTrouserFairFrac,tvTrouserAsan,tvTrouserAsanFrac,collarTv,collarWidthTv,fracCollarWidthTv,collarLengthTv,fracCollarLengthTv,kaffTv,kaffWidthTv,fracKaffWidthTv,kaffLengthTv,fracKaffLengthTv,salaeiTv,gheeraTv,dhagaTv,frontPocketTv,sidePocketTv,shalwarPocketTv,noteTv;
    String oid = "",Cid="",ID="",Name="",Number="",kameez="",kameezFrac="",tera="",teraFrac="",bazo="",bazoFrac="",moda="",modaFrac="",chati="",chatiFrac="",fitting="",fittingFrac="",gera="",geraFrac="",gala="",galaFrac="",shalwar="",shalwarFrac="",poncha="",ponchaFrac="",fair="",fairFrac="",asan="",asanFrac="",trouser="",trouserFrac="",trouserPoncha="",trouserPonchaFrac="",trouserFair="",trouserFairFrac="",trouserAsan="",trouserAsanFrac="",collar="",collarWidth="",fracCollarWidth="",collarLength="",fracCollarLength="",kaff="",kaffWidth="",fracKaffWidth="",kaffLength="",fracKaffLength="",salaei="",gheera="",dhaga="",frontPocket = "",sidePocket  = "",shalwarPocket = "",note = "",Location="";
    ImageView editBtn,deleteBtn,measurementBtn,clockBtn;
    Button startOrderBtn;
    public  EditText totalPayment,paidPayment,suitQuantity;
    public TextView selectedDate;
    private String total_Payment = "",paid_Payment = "0.0",suit_Quantity = "",orderStatus = "Started",selected_Date = "",days,months,years;
    int index;
    LinearLayout mainLayout;
    CustomerUtils customerUtils;
    PendingOrderUtils pendingOrderUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        getSupportActionBar().setTitle("Customer Information");

        fetchIntentData();
        Initialization();
        setTextToViews();
        setBtnListeners();
    }

    private void setTextChangeListener() {
        totalPayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                total_Payment = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        suitQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                suit_Quantity = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void fetchIntentData(){
        Intent intent = getIntent();
        if (intent != null){
            oid = intent.getStringExtra("Oid");
            Cid = intent.getStringExtra("CustCid");
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
            note = intent.getStringExtra("note");
            index = intent.getIntExtra("index",0);
            Location = intent.getStringExtra("LOCATION");
        }
    }

    public void Initialization(){
        customerUtils = new CustomerUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        pendingOrderUtils = new PendingOrderUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        tvID = findViewById(R.id.customerIDMeasurements);
        tvName = findViewById(R.id.CustomerName);
        tvNumber = findViewById(R.id.customerNumberMeasurements);
        editBtn = findViewById(R.id.editCustomerBtn);
        deleteBtn = findViewById(R.id.deleteCustomerBtn);
        measurementBtn = findViewById(R.id.measurementsCustomerBtn);
        startOrderBtn = findViewById(R.id.startOrderBtn);
        tvKameez = findViewById(R.id.kameeSizeMeasurements);
        tvKameezFrac = findViewById(R.id.kameeFracMeasurements);
        tvTera = findViewById(R.id.teraSizeMeasurements);
        tvTeraFrac = findViewById(R.id.teraFracMeasurements);
        tvBazo = findViewById(R.id.bazoSizeMeasurements);
        tvBazoFrac = findViewById(R.id.bazoFracMeasurements);
        tvModa = findViewById(R.id.modaSizeMeasurements);
        tvModaFrac = findViewById(R.id.modaFracMeasurements);
        tvChati = findViewById(R.id.chatiSizeMeasurements);
        tvChatiFrac = findViewById(R.id.chatiFracMeasurements);
        tvFitting = findViewById(R.id.fittingSizeMeasurements);
        tvFittingFrac = findViewById(R.id.fittingFracMeasurements);
        tvGera = findViewById(R.id.geraSizeMeasurements);
        tvGeraFrac = findViewById(R.id.geraFracMeasurements);
        tvGala = findViewById(R.id.galaSizeMeasurements);
        tvGalaFrac = findViewById(R.id.galaFracMeasurements);
        tvShalwar = findViewById(R.id.shalwarSizeMeasurements);
        tvShalwarFrac = findViewById(R.id.shalwarFracMeasurements);
        tvPoncha = findViewById(R.id.ponchaSizeMeasurements);
        tvPonchaFrac = findViewById(R.id.ponchaFracMeasurements);
        tvFair = findViewById(R.id.fairSizeMeasurements);
        tvFairFrac = findViewById(R.id.fairFracMeasurements);
        tvAsan = findViewById(R.id.asanSizeMeasurements);
        tvAsanFrac = findViewById(R.id.asanFracMeasurements);
        tvTrouser = findViewById(R.id.trouserSizeMeasurements);
        tvTrouserFrac = findViewById(R.id.trouserFracMeasurements);
        tvTrouserPoncha  =findViewById(R.id.trouserPonchaSizeMeasurements);
        tvTrouserPonchaFrac = findViewById(R.id.trouserPonchaFracMeasurements);
        tvTrouserFair = findViewById(R.id.trouserFairSizeMeasurements);
        tvTrouserFairFrac = findViewById(R.id.trouserFairFracMeasurements);
        tvTrouserAsan = findViewById(R.id.trouserAsanSizeMeasurements);
        tvTrouserAsanFrac = findViewById(R.id.trouserAsanFracMeasurements);
        collarTv = findViewById(R.id.collarTvMeasurements);
        collarWidthTv = findViewById(R.id.collarWidthTvMeasurements);
        fracCollarWidthTv = findViewById(R.id.collarWidthFracTvMeasurements);
        collarLengthTv = findViewById(R.id.collarLengthTvMeasurements);
        fracCollarLengthTv = findViewById(R.id.collarLengthFracTvMeasurements);
        kaffTv = findViewById(R.id.kaffTvMeasurements);
        kaffWidthTv = findViewById(R.id.kaffWidthTvMeasurements);
        fracKaffWidthTv = findViewById(R.id.kaffWidthFracTvMeasurements);
        kaffLengthTv = findViewById(R.id.kaffLengthTvMeasurements);
        fracKaffLengthTv = findViewById(R.id.kaffLengthFracTvMeasurements);
        salaeiTv = findViewById(R.id.salaeiTvMeasurements);
        gheeraTv = findViewById(R.id.gheeraTvMeasurements);
        dhagaTv = findViewById(R.id.dhagaTvMeasurements);
        frontPocketTv = findViewById(R.id.frontPocketTvMeasurements);
        sidePocketTv = findViewById(R.id.sidePocketTvMeasurements);
        shalwarPocketTv = findViewById(R.id.shalwarPocketTvMeasurements);
        noteTv = findViewById(R.id.tvNoteVC);
        mainLayout = findViewById(R.id.mainLayoutVC);
        mainLayout.getBackground().setAlpha(25);
    }

    public void setBtnListeners() {
        editBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        measurementBtn.setOnClickListener(this);
        startOrderBtn.setOnClickListener(this);
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
        collarWidthTv.setText(collarWidth);
        fracCollarWidthTv.setText(fracCollarWidth);
        collarLengthTv.setText(collarLength);
        fracCollarLengthTv.setText(fracCollarLength);
        kaffTv.setText(kaff);
        kaffWidthTv.setText(kaffWidth);
        fracKaffWidthTv.setText(fracKaffWidth);
        kaffLengthTv.setText(kaffLength);
        fracKaffLengthTv.setText(fracKaffLength);
        salaeiTv.setText(salaei);
        gheeraTv.setText(gheera);
        dhagaTv.setText(dhaga);
        frontPocketTv.setText(frontPocket);
        sidePocketTv.setText(sidePocket);
        shalwarPocketTv.setText(shalwarPocket);
        noteTv.setText(note);
    }

    @Override
    public void onClick(View v) {
        if(v == editBtn){
            Intent intent = new Intent(ViewCustomer.this,AddCustomer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("CustCid",Cid);
            intent.putExtra("CustID",tvID.getText().toString());
            intent.putExtra("CustName",tvName.getText().toString());
            intent.putExtra("CustNumber",tvNumber.getText().toString());
            intent.putExtra("CustKameez",kameez);
            intent.putExtra("CustKameezFrac",kameezFrac);
            intent.putExtra("CustTera",tera);
            intent.putExtra("CustTeraFrac",teraFrac);
            intent.putExtra("CustBazo",bazo);
            intent.putExtra("CustBazoFrac",bazoFrac);
            intent.putExtra("CustModa",moda);
            intent.putExtra("CustModaFrac",modaFrac);
            intent.putExtra("CustChati",chati);
            intent.putExtra("CustChatiFrac",chatiFrac);
            intent.putExtra("CustFitting",fitting);
            intent.putExtra("CustFittingFrac",fittingFrac);
            intent.putExtra("CustGera",gera);
            intent.putExtra("CustGeraFrac",geraFrac);
            intent.putExtra("CustGala",gala);
            intent.putExtra("CustGalaFrac",galaFrac);
            intent.putExtra("CustShalwar",shalwar);
            intent.putExtra("CustShalwarFrac",shalwarFrac);
            intent.putExtra("CustPoncha",poncha);
            intent.putExtra("CustPonchaFrac",ponchaFrac);
            intent.putExtra("CustFair",fair);
            intent.putExtra("CustFairFrac",fairFrac);
            intent.putExtra("CustAsan",asan);
            intent.putExtra("CustAsanFrac",asanFrac);
            intent.putExtra("CustTrouser",trouser);
            intent.putExtra("CustTrouserFrac",trouserFrac);
            intent.putExtra("CustTrouserPoncha",trouserPoncha);
            intent.putExtra("CustTrouserPonchaFrac",trouserPonchaFrac);
            intent.putExtra("CustTrouserFair",trouserFair);
            intent.putExtra("CustTrouserFairFrac",trouserFairFrac);
            intent.putExtra("CustTrouserAsan",trouserAsan);
            intent.putExtra("CustTrouserAsanFrac",trouserAsanFrac);
            intent.putExtra("CustCollar",collar);
            intent.putExtra("CustCollarWidth",collarWidth);
            intent.putExtra("CustCollarWidthFrac",fracCollarWidth);
            intent.putExtra("CustCollarLength",collarLength);
            intent.putExtra("CustCollarLengthFrac",fracCollarLength);
            intent.putExtra("CustKaff",kaff);
            intent.putExtra("CustKaffWidth",kaffWidth);
            intent.putExtra("CustKaffWidthFrac",fracKaffWidth);
            intent.putExtra("CustKaffLength",kaffLength);
            intent.putExtra("CustKaffLengthFrac",fracKaffLength);
            intent.putExtra("CustSalaei",salaei);
            intent.putExtra("CustGheera",gheera);
            intent.putExtra("CustDhaga",dhaga);
            intent.putExtra("frontPocket",frontPocket);
            intent.putExtra("sidePocket",sidePocket);
            intent.putExtra("shalwarPocket",shalwarPocket);
            intent.putExtra("note",note);
            intent.putExtra("updateCustomer",true);
            intent.putExtra("index",index);
            intent.putExtra("LOCATION","ViewCustomer");
            startActivity(intent);

        }
        else if(v == deleteBtn){
            if(Location.equals("PendingOrderFragment")){
                deleteContact(this,Name,Number);
                pendingOrderUtils.deleteOrder(oid);
                Toast.makeText(this, "Pending order deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewCustomer.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else {
                deleteContact(this,Name,Number);
                customerUtils.deleteCustommer(Cid);
                Toast.makeText(this, "Customer deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewCustomer.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        else if (v == startOrderBtn){
            showCustomAlertDialog();
        }
        else if(v == measurementBtn){
            Intent intent = new Intent(ViewCustomer.this,MeasurementsActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("CustID",tvID.getText().toString());
            intent.putExtra("CustName",tvName.getText().toString());
            intent.putExtra("CustNumber",tvNumber.getText().toString());
            intent.putExtra("CustKameez",kameez);
            intent.putExtra("CustKameezFrac",kameezFrac);
            intent.putExtra("CustTera",tera);
            intent.putExtra("CustTeraFrac",teraFrac);
            intent.putExtra("CustBazo",bazo);
            intent.putExtra("CustBazoFrac",bazoFrac);
            intent.putExtra("CustModa",moda);
            intent.putExtra("CustModaFrac",modaFrac);
            intent.putExtra("CustChati",chati);
            intent.putExtra("CustChatiFrac",chatiFrac);
            intent.putExtra("CustFitting",fitting);
            intent.putExtra("CustFittingFrac",fittingFrac);
            intent.putExtra("CustGera",gera);
            intent.putExtra("CustGeraFrac",geraFrac);
            intent.putExtra("CustGala",gala);
            intent.putExtra("CustGalaFrac",galaFrac);
            intent.putExtra("CustShalwar",shalwar);
            intent.putExtra("CustShalwarFrac",shalwarFrac);
            intent.putExtra("CustPoncha",poncha);
            intent.putExtra("CustPonchaFrac",ponchaFrac);
            intent.putExtra("CustFair",fair);
            intent.putExtra("CustFairFrac",fairFrac);
            intent.putExtra("CustAsan",asan);
            intent.putExtra("CustAsanFrac",asanFrac);
            intent.putExtra("CustTrouser",trouser);
            intent.putExtra("CustTrouserFrac",trouserFrac);
            intent.putExtra("CustTrouserPoncha",trouserPoncha);
            intent.putExtra("CustTrouserPonchaFrac",trouserPonchaFrac);
            intent.putExtra("CustTrouserFair",trouserFair);
            intent.putExtra("CustTrouserFairFrac",trouserFairFrac);
            intent.putExtra("CustTrouserAsan",trouserAsan);
            intent.putExtra("CustTrouserAsanFrac",trouserAsanFrac);
            intent.putExtra("CustCollar",collar);
            intent.putExtra("CustCollarWidth",collarWidth);
            intent.putExtra("CustCollarWidthFrac",fracCollarWidth);
            intent.putExtra("CustCollarLength",collarLength);
            intent.putExtra("CustCollarLengthFrac",fracCollarLength);
            intent.putExtra("CustKaff",kaff);
            intent.putExtra("CustKaffWidth",kaffWidth);
            intent.putExtra("CustKaffWidthFrac",fracKaffWidth);
            intent.putExtra("CustKaffLength",kaffLength);
            intent.putExtra("CustKaffLengthFrac",fracKaffLength);
            intent.putExtra("CustSalaei",salaei);
            intent.putExtra("CustGheera",gheera);
            intent.putExtra("CustDhaga",dhaga);
            intent.putExtra("frontPocket",frontPocket);
            intent.putExtra("sidePocket",sidePocket);
            intent.putExtra("shalwarPocket",shalwarPocket);
            intent.putExtra("updateCustomer",true);
            intent.putExtra("index",index);
            intent.putExtra("LOCATION","ViewCustomer");
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchIntentData();
    }

    @SuppressLint("Range")
    private void deleteContact(Context ctx, String name, String phone) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            if (cur.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(name)) {
                        String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                    }

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
    public void showCustomAlertDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.order_dialog);
        dialog.setTitle("Requirements");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        totalPayment = dialog.findViewById(R.id.edtTotalPayment);
        paidPayment = dialog.findViewById(R.id.edtPaidPayment);
        suitQuantity = dialog.findViewById(R.id.edtQuantity);
        clockBtn = dialog.findViewById(R.id.dateImg);
        selectedDate = dialog.findViewById(R.id.tvSelectedDate);

        setTextChangeListener();

        clockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        TextView cancelBtn = dialog.findViewById(R.id.tvCancelBtn);
        TextView startBtn = dialog.findViewById(R.id.tvStartBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getRequiredData()){
                    if(selectedDate.getText().toString().equals("")) {
                        Toast.makeText(ViewCustomer.this, "Date required", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        String order_Status = "Pending";
                        String completed_date = "";
                        String delivered_date = "";
                        SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy");
                        String start_date = formDate.format(new Date());

                        OrderDetails order = new OrderDetails(oid,ID,Name,Number,order_Status,start_date
                                ,completed_date,delivered_date,total_Payment,paid_Payment,suit_Quantity,selected_Date,false);

                        if(oid == null || oid.equals("")){
                            pendingOrderUtils.addOrder(order);
                            Toast.makeText(ViewCustomer.this, "Order Started", Toast.LENGTH_SHORT).show();
                        }else {
                            pendingOrderUtils.updateOrder(oid,order);
                            Toast.makeText(ViewCustomer.this, "Order Updated", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    public boolean getRequiredData(){

        if(total_Payment.equals("")){
            Toast.makeText(ViewCustomer.this, "Total Payment required ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(suit_Quantity.equals("")){
            Toast.makeText(ViewCustomer.this, "Suit Quantity required ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!paidPayment.getText().toString().equals("")){
            paid_Payment = paidPayment.getText().toString();
        }
        return true;
    }

    public void pickDate(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ViewCustomer.this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                days = String.valueOf(dayOfMonth);
                months = String.valueOf(year);
                years = String.valueOf(year);
                selectedDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                selected_Date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
            }
         }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.manualInvoice:
                Intent intent = new Intent(ViewCustomer.this,ManualInvoiceActivity.class);
                intent.putExtra("CustID",tvID.getText().toString());
                intent.putExtra("CustName",tvName.getText().toString());
                intent.putExtra("CustNumber",tvNumber.getText().toString());
                startActivity(intent);
        }
            return super.onOptionsItemSelected(item);
    }
}