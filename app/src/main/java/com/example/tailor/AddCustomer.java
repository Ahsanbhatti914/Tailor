package com.example.tailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tailor.Utils.CustomerUtils;
import com.example.tailor.Models.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddCustomer extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText custID,custName,custNumber,kameezLength,tera,bazo,moda,chati,fitting,gera,gala,shalwarLength,poncha,fair,asan,trouserLength,trouserPoncha,trouserFair,trouserAsan,collarWidth,collarLength,kaffWidth,kaffLength,frontPocketedt,sidePocketedt,shalwarPocketedt,note;
    Spinner kameeezSpinner,teraSpinner,bazoSpinner,modaSpinner,chatiSpinner,fittingSpinner,geraSpinner,galaSpinner,shalwarSpinner,ponchaSpinner,fairSpinner,asanSpinner,trouserSpinner,trouserPonchaSpinner,trouserFairSpinner,trouserAsanSpinner,collarSpinner,collarWidthSpinner,collarLengthSpinner,kaffSpinner,kaffWidthSpinner,kaffLengthSpinner,salaeiSpinner,gheeraSpinner,dhagaSpinner;
    Button submit;
    String cid = "",fracKameez = "",fracTera = "",fracBazo = "",fracModa = "",fracChati = "",fracFitting ="",fracGera = "",fracGala = "",fracShalwar = "",fracPoncha = "",fracFair = "",fracAsan = "",fracTrouser = "",fracTrouserPoncha = "",fracTrouserFair = "",fracTrouserAsan = "",collar = "none",fracCollarWidth="",fracCollarLength="",kaff = "none",fracKaffWidth="",fracKaffLength="",salaei = "none",gheera="none",dhaga = "none";
    boolean updateCustomer = false;
    TextView lable,collarTv,kaffTv,salaeiTv,gheeraTv,dhagaTv;
    public static ArrayList<Customer> customersList = new ArrayList<>();
    String [] fraction = {"none","1/4","1/2","3/4"};
    String [] collarArray = {"none","Collar","Bain","Magzi"};
    String [] kaffArray = {"none","Cut Kaff","Gol Kaff","Choras Kaff","Stud Kaff","Bazo Gol"};
    String [] salaeiArray = {"Single","Double","Tripple"};
    String [] gheeraArray = {"none","Choras","Gol","M.Kurta"};
    String [] dhagaArray = {"Sadha","Reshmi"};
    String Location,frontPocket = "",sidePocket  = "",shalwarPocket = "",preName="",preContact = "";
    ArrayAdapter <String> adapter,collarAdapter,kaffAdapter,salaeiAdapter,gheeraAdapter,dhagaAdapter;
    int visited = 0,index;
    boolean unLocked = false;
    LinearLayout mainLayout;
    CustomerUtils customerUtils;
    private Customer customer = null;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        getSupportActionBar().setTitle("MEASUREMENT");

        Initialization();
        setOnItemSelectedListener();
        setAdapters();
    }

    public void Initialization(){
        customerUtils = new CustomerUtils(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".com",""));
        lable = findViewById(R.id.toolbarTitleAddCustomer);
        custID = findViewById(R.id.custID);
        custName = findViewById(R.id.custName);
        custNumber = findViewById(R.id.custNumber);

        kameezLength = findViewById(R.id.kameezLegth);
        tera = findViewById(R.id.teraLength);
        bazo = findViewById(R.id.bazoLength);
        moda = findViewById(R.id.modaLength);
        chati = findViewById(R.id.chatiLength);
        fitting = findViewById(R.id.fittingLength);
        gera = findViewById(R.id.geraLength);
        gala = findViewById(R.id.galaLength);

        shalwarLength = findViewById(R.id.shalwarLength);
        poncha = findViewById(R.id.panchaLength);
        fair = findViewById(R.id.fairLength);
        asan = findViewById(R.id.asanLength);

        trouserLength = findViewById(R.id.trouserLength);
        trouserPoncha = findViewById(R.id.trouserPanchaLength);
        trouserFair = findViewById(R.id.trouserFairLength);
        trouserAsan = findViewById(R.id.trouserAsanLength);

        kameeezSpinner = findViewById(R.id.kameezSpinner);
        teraSpinner = findViewById(R.id.terSpinner);
        bazoSpinner = findViewById(R.id.bazoSpinner);
        modaSpinner = findViewById(R.id.modaSpinner);
        chatiSpinner = findViewById(R.id.chatiSpinner);
        fittingSpinner = findViewById(R.id.fittingSpinner);
        geraSpinner = findViewById(R.id.geraSpinner);
        galaSpinner = findViewById(R.id.galaSpinner);

        shalwarSpinner = findViewById(R.id.shalwarSpinner);
        ponchaSpinner = findViewById(R.id.ponchaSpinner);
        fairSpinner = findViewById(R.id.fairSpinner);
        asanSpinner = findViewById(R.id.asanSpinner);

        trouserSpinner = findViewById(R.id.trouserSpinner);
        trouserPonchaSpinner = findViewById(R.id.trouserPonchaSpinner);
        trouserFairSpinner = findViewById(R.id.trouserFairSpinner);
        trouserAsanSpinner = findViewById(R.id.trouseraAsanSpinner);

        collarTv = findViewById(R.id.ACCollarTv);
        collarWidth = findViewById(R.id.ACCollarWidthEdt);
        collarLength = findViewById(R.id.ACCollarLengthEdt);
        kaffTv = findViewById(R.id.ACKaffTv);
        kaffWidth = findViewById(R.id.ACKaffWidthEdt);
        kaffLength = findViewById(R.id.ACKaffLengthEdt);
        salaeiTv = findViewById(R.id.ACSalaeiTv);
        gheeraTv = findViewById(R.id.ACGheeraTv);
        dhagaTv = findViewById(R.id.ACDhagaTv);

        collarSpinner = findViewById(R.id.ACCollarSpinner);
        collarWidthSpinner = findViewById(R.id.ACCollarWidthSpinner);
        collarLengthSpinner = findViewById(R.id.ACCollarLengthSpinner);
        kaffSpinner = findViewById(R.id.ACKaffSpinner);
        kaffWidthSpinner = findViewById(R.id.ACKaffWidthSpinner);
        kaffLengthSpinner = findViewById(R.id.ACKaffLengthSpinner);
        salaeiSpinner = findViewById(R.id.ACSalaeiSpinner);
        gheeraSpinner = findViewById(R.id.ACGheeraSpinner);
        dhagaSpinner = findViewById(R.id.ACDhagaSpinner);

        frontPocketedt = findViewById(R.id.setFrontPocketsEdt);
        sidePocketedt = findViewById(R.id.setSidePocketsEdt);
        shalwarPocketedt = findViewById(R.id.setShalwarPocketsEdt);
        note = findViewById(R.id.noteEdtAC);
        submit = findViewById(R.id.submitBtn);

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,fraction);
        collarAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,collarArray);
        kaffAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,kaffArray);
        salaeiAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,salaeiArray);
        gheeraAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,gheeraArray);
        dhagaAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dhagaArray);

        mainLayout = findViewById(R.id.mainLayoutAddCustomer);
        mainLayout.getBackground().setAlpha(25);
    }

    public void setOnItemSelectedListener(){
        kameeezSpinner.setOnItemSelectedListener(this);
        teraSpinner.setOnItemSelectedListener(this);
        bazoSpinner.setOnItemSelectedListener(this);
        modaSpinner.setOnItemSelectedListener(this);
        chatiSpinner.setOnItemSelectedListener(this);
        fittingSpinner.setOnItemSelectedListener(this);
        geraSpinner.setOnItemSelectedListener(this);
        galaSpinner.setOnItemSelectedListener(this);
        shalwarSpinner.setOnItemSelectedListener(this);
        ponchaSpinner.setOnItemSelectedListener(this);
        fairSpinner.setOnItemSelectedListener(this);
        asanSpinner.setOnItemSelectedListener(this);
        trouserSpinner.setOnItemSelectedListener(this);
        trouserPonchaSpinner.setOnItemSelectedListener(this);
        trouserFairSpinner.setOnItemSelectedListener(this);
        trouserAsanSpinner.setOnItemSelectedListener(this);
        collarSpinner.setOnItemSelectedListener(this);
        collarWidthSpinner.setOnItemSelectedListener(this);
        collarLengthSpinner.setOnItemSelectedListener(this);
        kaffSpinner.setOnItemSelectedListener(this);
        kaffWidthSpinner.setOnItemSelectedListener(this);
        kaffLengthSpinner.setOnItemSelectedListener(this);
        salaeiSpinner.setOnItemSelectedListener(this);
        gheeraSpinner.setOnItemSelectedListener(this);
        dhagaSpinner.setOnItemSelectedListener(this);
        submit.setOnClickListener(this);
    }

    public void setAdapters(){
        kameeezSpinner.setAdapter(adapter);
        teraSpinner.setAdapter(adapter);
        bazoSpinner.setAdapter(adapter);
        modaSpinner.setAdapter(adapter);
        chatiSpinner.setAdapter(adapter);
        fittingSpinner.setAdapter(adapter);
        geraSpinner.setAdapter(adapter);
        galaSpinner.setAdapter(adapter);
        shalwarSpinner.setAdapter(adapter);
        ponchaSpinner.setAdapter(adapter);
        fairSpinner.setAdapter(adapter);
        asanSpinner.setAdapter(adapter);
        trouserSpinner.setAdapter(adapter);
        trouserPonchaSpinner.setAdapter(adapter);
        trouserFairSpinner.setAdapter(adapter);
        trouserAsanSpinner.setAdapter(adapter);
        collarSpinner.setAdapter(collarAdapter);
        collarWidthSpinner.setAdapter(adapter);
        collarLengthSpinner.setAdapter(adapter);
        kaffSpinner.setAdapter(kaffAdapter);
        kaffWidthSpinner.setAdapter(adapter);
        kaffWidthSpinner.setSelection(adapter.getPosition("1/2"));
        kaffLengthSpinner.setAdapter(adapter);
        salaeiSpinner.setAdapter(salaeiAdapter);
        gheeraSpinner.setAdapter(gheeraAdapter);
        dhagaSpinner.setAdapter(dhagaAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(unLocked)
        {
            if(parent == kameeezSpinner) {
                fracKameez = parent.getItemAtPosition(position).toString();
            }
            else if(parent == teraSpinner) {
                fracTera = parent.getItemAtPosition(position).toString();
            }
            else if(parent == bazoSpinner) {
                fracBazo = parent.getItemAtPosition(position).toString();
            }
            else if(parent == modaSpinner) {
                fracModa = parent.getItemAtPosition(position).toString();
            }
            else if(parent == chatiSpinner) {
                fracChati = parent.getItemAtPosition(position).toString();
            }
            else if(parent == fittingSpinner) {
                fracFitting = parent.getItemAtPosition(position).toString();
            }
            else if(parent == geraSpinner) {
                fracGera = parent.getItemAtPosition(position).toString();
            }
            else if(parent == galaSpinner) {
                fracGala = parent.getItemAtPosition(position).toString();
            }
            else if(parent == shalwarSpinner) {
                fracShalwar = parent.getItemAtPosition(position).toString();
            }
            else if(parent == ponchaSpinner) {
                fracPoncha = parent.getItemAtPosition(position).toString();
            }
            else if(parent == fairSpinner) {
                fracFair = parent.getItemAtPosition(position).toString();
            }
            else if(parent == asanSpinner) {
                fracAsan = parent.getItemAtPosition(position).toString();
            }
            else if(parent == trouserSpinner) {
                fracTrouser = parent.getItemAtPosition(position).toString();
            }
            else if(parent == trouserPonchaSpinner) {
                fracTrouserPoncha = parent.getItemAtPosition(position).toString();
            }
            else if(parent == trouserFairSpinner) {
                fracTrouserFair = parent.getItemAtPosition(position).toString();
            }
            else if(parent == trouserAsanSpinner) {
                fracTrouserAsan = parent.getItemAtPosition(position).toString();
            }
            else if(parent == collarSpinner) {
                collar = parent.getItemAtPosition(position).toString();
                collarTv.setText(collar);
            }
            else if(parent == collarWidthSpinner ) {
                fracCollarWidth = parent.getItemAtPosition(position).toString();
            }
            else if(parent == collarLengthSpinner ) {
                fracCollarLength = parent.getItemAtPosition(position).toString();
            }
            else if(parent == kaffSpinner) {
                kaff = parent.getItemAtPosition(position).toString();
                kaffTv.setText(kaff);
            }
            else if(parent == kaffWidthSpinner ) {
                fracKaffWidth = parent.getItemAtPosition(position).toString();
            }
            else if(parent == kaffLengthSpinner ) {
                fracKaffLength = parent.getItemAtPosition(position).toString();
            }
            else if(parent == salaeiSpinner) {
                salaei = parent.getItemAtPosition(position).toString();
                salaeiTv.setText(salaei);
            }
            else if(parent == gheeraSpinner) {
                gheera = parent.getItemAtPosition(position).toString();
                gheeraTv.setText(gheera);
            }
            else if(parent == dhagaSpinner) {
                dhaga = parent.getItemAtPosition(position).toString();
                dhagaTv.setText(dhaga);
            }
        }
        else {
            visited++;
            if(visited == 25)
            {
                if(fracKameez == null){
                    fracKameez = "none";
                }
                if(fracTera == null){
                    fracTera = "none";
                }
                if(fracBazo == null){
                    fracBazo = "none";
                }
                if(fracModa == null){
                    fracModa = "none";
                }
                if(fracChati == null){
                    fracChati = "none";
                }
                if(fracFitting == null){
                    fracFitting = "none";
                }
                if(fracGera == null){
                    fracGera = "none";
                }
                if(fracGala == null){
                    fracGala = "none";
                }
                if(fracShalwar == null){
                    fracShalwar = "none";
                }
                if(fracPoncha == null){
                    fracPoncha = "none";
                }
                if(fracFair == null){
                    fracFair = "none";
                }
                if(fracAsan == null){
                    fracAsan = "none";
                }
                if(fracTrouser == null){
                    fracTrouser = "none";
                }
                if(fracTrouserPoncha == null){
                    fracTrouserPoncha = "none";
                }
                if(fracTrouserFair == null){
                    fracTrouserFair = "none";
                }
                if(fracTrouserAsan == null){
                    fracTrouserAsan = "none";
                }
                if(fracCollarWidth == null){
                    fracCollarWidth = "none";
                }
                if(fracCollarLength == null){
                    fracCollarLength = "none";
                }
                if(fracKaffWidth == null || fracKaffWidth.equals("")){
                    fracKaffWidth = "1/2";
                }
                if(fracKaffLength == null){
                    fracKaffLength = "none";
                }
                unLocked = true;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v==submit){
            if(!checkFroAllRequiredData())
                return;
            Pockets();

            if(!updateCustomer){
                customerUtils.getReference().orderByChild("id").equalTo(custID.getText().toString())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(getApplicationContext(), "Customer with ID "+custID.getText().toString()+" already exist", Toast.LENGTH_SHORT).show();
                        }else {
                            Customer customer = new Customer(cid,custID.getText().toString(),custName.getText().toString(),custNumber.getText().toString(),kameezLength.getText().toString(),fracKameez,
                                    tera.getText().toString(),fracTera,bazo.getText().toString(),fracBazo,moda.getText().toString(),fracModa,chati.getText().toString(),fracChati,fitting.getText().toString(),fracFitting,
                                    gera.getText().toString(),fracGera,gala.getText().toString(),fracGala, shalwarLength.getText().toString(),fracShalwar,poncha.getText().toString(),fracPoncha,fair.getText().toString(),fracFair,
                                    asan.getText().toString(),fracAsan,trouserLength.getText().toString(),fracTrouser,trouserPoncha.getText().toString(),fracTrouserPoncha,trouserFair.getText().toString(),fracTrouserFair,
                                    trouserPoncha.getText().toString(),fracTrouserAsan,collar,collarWidth.getText().toString(),fracCollarWidth,collarLength.getText().toString(),fracCollarLength,kaff,kaffWidth.getText().toString(),
                                    fracKaffWidth,kaffLength.getText().toString(),fracKaffLength,salaei,gheera,dhaga,frontPocket,sidePocket,shalwarPocket,note.getText().toString());

                            customerUtils.addCustomer(customer);
                            Toast.makeText(getApplicationContext(), "Customer added successfully", Toast.LENGTH_SHORT).show();
                            saveContactsInPhone(custName.getText().toString(),custNumber.getText().toString());
                            Intent intent = new Intent(AddCustomer.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else {

                Customer customer =  new Customer(cid,custID.getText().toString(),custName.getText().toString(),custNumber.getText().toString(),kameezLength.getText().toString(),fracKameez,
                        tera.getText().toString(),fracTera,bazo.getText().toString(),fracBazo,moda.getText().toString(),fracModa,chati.getText().toString(),fracChati,fitting.getText().toString(),fracFitting,
                        gera.getText().toString(),fracGera,gala.getText().toString(),fracGala, shalwarLength.getText().toString(),fracShalwar,poncha.getText().toString(),fracPoncha,fair.getText().toString(),fracFair,
                        asan.getText().toString(),fracAsan,trouserLength.getText().toString(),fracTrouser,trouserPoncha.getText().toString(),fracTrouserPoncha,trouserFair.getText().toString(),fracTrouserFair,
                        trouserPoncha.getText().toString(),fracTrouserAsan,collar,collarWidth.getText().toString(),fracCollarWidth,collarLength.getText().toString(),fracCollarLength,kaff,kaffWidth.getText().toString(),
                        fracKaffWidth,kaffLength.getText().toString(),fracKaffLength,salaei,gheera,dhaga,frontPocket,sidePocket,shalwarPocket,note.getText().toString());

                customerUtils.updateCustomer(cid,customer);
                updateContact(getApplicationContext(),preName,custName.getText().toString(),preContact,custNumber.getText().toString());
                Toast.makeText(getApplicationContext(), "Customer updated successfully ", Toast.LENGTH_SHORT).show();
                updateCustomer = false;
                Intent intent = new Intent(AddCustomer.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }
    }

    public void saveContactsInPhone(String name, String phone){
        ArrayList<ContentProviderOperation> contentProviderOperations
                = new ArrayList<ContentProviderOperation>();

        contentProviderOperations.add(ContentProviderOperation.newInsert(
                        ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // Adding Name
        contentProviderOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        name)
                .build());

        // Adding Number
        contentProviderOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build());

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    private void updateContact(Context ctx, String preName, String newName, String prePhone, String newPhone) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(prePhone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            if (cur.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(preName)) {
                        String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                    }

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        saveContactsInPhone(newName,newPhone);
    }

    public boolean checkFroAllRequiredData() {
        if(custID.getText().toString().equals("")){
            Toast.makeText(this, "Customer ID is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(custName.getText().toString().equals("")){
            Toast.makeText(this, "Customer name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(custNumber.getText().toString().equals("")){
            Toast.makeText(this, "Customer number is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void Pockets() {
        if(frontPocketedt.getText().toString().equals("")){
            frontPocket = "0";
        }else {
            frontPocket = frontPocketedt.getText().toString();
        }
        if(sidePocketedt.getText().toString().equals("")){
            sidePocket = "0";
        }else {
            sidePocket = sidePocketedt.getText().toString();
        }
        if(shalwarPocketedt.getText().toString().equals("")){
            shalwarPocket = "0";
        }else {
            shalwarPocket = shalwarPocketedt.getText().toString();
        }
    }

    public void setKameezData(String val2){
        if(!val2.equals(""))
            kameeezSpinner.setSelection(stringToIndex(val2));
    }
    public void setTeraData(String val2){
        if(!val2.equals(""))
            teraSpinner.setSelection(stringToIndex(val2));
    }
    public void setBazoData(String val2){
        if(!val2.equals(""))
            bazoSpinner.setSelection(stringToIndex(val2));
    }
    public void setModaData(String val2){
        if(!val2.equals(""))
            modaSpinner.setSelection(stringToIndex(val2));
    }
    public void setChatiData(String val2){
        if(!val2.equals(""))
            chatiSpinner.setSelection(stringToIndex(val2));
    }
    public void setFittingData(String val2){
        if(!val2.equals(""))
            fittingSpinner.setSelection(stringToIndex(val2));
    }
    public void setGeraData(String val2){
        if(!val2.equals(""))
            geraSpinner.setSelection(stringToIndex(val2));
    }
    public void setGalaData(String val2){
        if(!val2.equals(""))
            galaSpinner.setSelection(stringToIndex(val2));
    }
    public void setShalwarData(String val2){
        if(!val2.equals(""))
            shalwarSpinner.setSelection(stringToIndex(val2));
    }
    public void setPonchaData(String val2){
        if(!val2.equals(""))
            ponchaSpinner.setSelection(stringToIndex(val2));
    }
    public void setFairData(String val2){
        if(!val2.equals(""))
            fairSpinner.setSelection(stringToIndex(val2));
    }
    public void setAsanData(String val2){
        if(!val2.equals(""))
            asanSpinner.setSelection(stringToIndex(val2));
    }
    public void setTrouserData(String val2){
        if(!val2.equals(""))
            trouserSpinner.setSelection(stringToIndex(val2));
    }
    public void setTrouserPonchaData(String val2){
        if(!val2.equals(""))
            trouserPonchaSpinner.setSelection(stringToIndex(val2));
    }
    public void setTrouserFairData(String val2){
        if(!val2.equals(""))
            trouserFairSpinner.setSelection(stringToIndex(val2));
    }
    public void setTrouserAsanData(String val2){
        if(!val2.equals(""))
            trouserAsanSpinner.setSelection(stringToIndex(val2));
    }
    public void setCollarWidthData(String val2){
        if(!val2.equals(""))
            collarWidthSpinner.setSelection(stringToIndex(val2));
    }
    public void setCollarLengthData(String val2){
        if(!val2.equals(""))
            collarLengthSpinner.setSelection(stringToIndex(val2));
    }
    public void setKaffWidthData(String val2){
        if(!val2.equals(""))
            kaffWidthSpinner.setSelection(stringToIndex(val2));
    }
    public void setKaffLengthData(String val2){
        if(!val2.equals(""))
            kaffLengthSpinner.setSelection(stringToIndex(val2));
    }

    public int stringToIndex(String data){
        if(data.equals("none")){
            return 0;
        }else if(data.equals("1/4")){
            return 1;
        }
        else if(data.equals("1/2")){
            return 2;
        }
        else {
            return 3;
        }
    }

    public void fetchIntentdata() {

        try {
            intent = getIntent();
            if(intent.getStringExtra("CustCid") != null){
                Log.d("mTag", "fetchIntentdata: " + intent.getStringExtra("CustCid"));
                cid = intent.getStringExtra("CustCid");
                custID.setText(intent.getStringExtra("CustID"));
                custName.setText(intent.getStringExtra("CustName"));
                custNumber.setText(intent.getStringExtra("CustNumber"));
                kameezLength.setText(intent.getStringExtra("CustKameez"));
                tera.setText(intent.getStringExtra("CustTera"));
                bazo.setText(intent.getStringExtra("CustBazo"));
                moda.setText(intent.getStringExtra("CustModa"));
                chati.setText(intent.getStringExtra("CustChati"));
                fitting.setText(intent.getStringExtra("CustFitting"));
                gera.setText(intent.getStringExtra("CustGera"));
                gala.setText(intent.getStringExtra("CustGala"));
                shalwarLength.setText(intent.getStringExtra("CustShalwar"));
                poncha.setText(intent.getStringExtra("CustPoncha"));
                fair.setText(intent.getStringExtra("CustFair"));
                asan.setText(intent.getStringExtra("CustAsan"));
                trouserLength.setText(intent.getStringExtra("CustTrouser"));
                trouserPoncha.setText(intent.getStringExtra("CustTrouserPoncha"));
                trouserFair.setText(intent.getStringExtra("CustTrouserFair"));
                trouserAsan.setText(intent.getStringExtra("CustTrouserAsan"));
                fracKameez = intent.getStringExtra("CustKameezFrac");
                fracTera = intent.getStringExtra("CustTeraFrac");
                fracBazo = intent.getStringExtra("CustBazoFrac");
                fracModa = intent.getStringExtra("CustModaFrac");
                fracChati = intent.getStringExtra("CustChatiFrac");
                fracFitting = intent.getStringExtra("CustFittingFrac");
                fracGera = intent.getStringExtra("CustGeraFrac");
                fracGala = intent.getStringExtra("CustGalaFrac");
                fracShalwar = intent.getStringExtra("CustShalwarFrac");
                fracPoncha = intent.getStringExtra("CustPonchaFrac");
                fracFair = intent.getStringExtra("CustFairFrac");
                fracAsan = intent.getStringExtra("CustAsanFrac");
                fracTrouser = intent.getStringExtra("CustTrouserFrac");
                fracTrouserPoncha = intent.getStringExtra("CustTrouserPonchaFrac");
                fracTrouserFair = intent.getStringExtra("CustTrouserFairFrac");
                fracTrouserAsan = intent.getStringExtra("CustTrouserAsanFrac");
                collar = intent.getStringExtra("CustCollar");
                collarWidth.setText(intent.getStringExtra("CustCollarWidth"));
                fracCollarWidth = intent.getStringExtra("CustCollarWidthFrac");
                collarLength.setText(intent.getStringExtra("CustCollarLength"));
                fracCollarLength = intent.getStringExtra("CustCollarLengthFrac");
                kaff = intent.getStringExtra("CustKaff");
                kaffWidth.setText(intent.getStringExtra("CustKaffWidth"));
                fracKaffWidth = intent.getStringExtra("CustKaffWidthFrac");
                kaffLength.setText(intent.getStringExtra("CustKaffLength"));
                fracKaffLength = intent.getStringExtra("CustKaffLengthFrac");
                salaeiTv.setText(intent.getStringExtra("CustSalaei"));
                salaei = intent.getStringExtra("CustSalaei");
                gheeraTv.setText(intent.getStringExtra("CustGheera"));
                gheera = intent.getStringExtra("CustGheera");
                dhagaTv.setText(intent.getStringExtra("CustDhaga"));
                dhaga = intent.getStringExtra("CustDhaga");
                setKameezData(fracKameez);
                setTeraData(fracTera);
                setBazoData(fracBazo);
                setModaData(fracModa);
                setChatiData(fracChati);
                setFittingData(fracFitting);
                setGeraData(fracGera);
                setGalaData(fracGala);
                setShalwarData(fracShalwar);
                setPonchaData(fracPoncha);
                setFairData(fracFair);
                setAsanData(fracAsan);
                setTrouserData(fracTrouser);
                setTrouserPonchaData(fracTrouserPoncha);
                setTrouserFairData(fracTrouserFair);
                setTrouserAsanData(fracTrouserAsan);
                setCollarWidthData(fracCollarWidth);
                setCollarLengthData(fracCollarLength);
                setKaffWidthData(fracKaffWidth);
                setKaffLengthData(fracKaffLength);
                frontPocketedt.setText(intent.getStringExtra("frontPocket"));
                sidePocketedt.setText(intent.getStringExtra("sidePocket"));
                shalwarPocketedt.setText(intent.getStringExtra("shalwarPocket"));
                note.setText(intent.getStringExtra("note"));
                Bundle bundle = getIntent().getExtras();
                updateCustomer = bundle.getBoolean("updateCustomer");
                index = intent.getIntExtra("index",0);
                Location = intent.getStringExtra("LOCATION");
                preName = custName.getText().toString();
                preContact = custNumber.getText().toString();
                if(updateCustomer)
                {
                    lable.setText("Update Customer");
                    submit.setText("Update");
                }
            }
        }
        catch (Exception e){}

    }


    @Override
    protected void onResume() {
        super.onResume();
        getSaveCustomer();
        fetchIntentdata();
    }

    @Override
    protected void onPause() {//saving instance state
        super.onPause();
        customer = new Customer(cid,custID.getText().toString(),custName.getText().toString(),custNumber.getText().toString(),kameezLength.getText().toString(),fracKameez,
                tera.getText().toString(),fracTera,bazo.getText().toString(),fracBazo,moda.getText().toString(),fracModa,chati.getText().toString(),fracChati,fitting.getText().toString(),fracFitting,
                gera.getText().toString(),fracGera,gala.getText().toString(),fracGala, shalwarLength.getText().toString(),fracShalwar,poncha.getText().toString(),fracPoncha,fair.getText().toString(),fracFair,
                asan.getText().toString(),fracAsan,trouserLength.getText().toString(),fracTrouser,trouserPoncha.getText().toString(),fracTrouserPoncha,trouserFair.getText().toString(),fracTrouserFair,
                trouserPoncha.getText().toString(),fracTrouserAsan,collar,collarWidth.getText().toString(),fracCollarWidth,collarLength.getText().toString(),fracCollarLength,kaff,kaffWidth.getText().toString(),
                fracKaffWidth,kaffLength.getText().toString(),fracKaffLength,salaei,gheera,dhaga,frontPocketedt.getText().toString(),sidePocketedt.getText().toString(),shalwarPocketedt.getText().toString(),note.getText().toString());
        Log.d("mTag", "onPause: " + custName.getText().toString());
        //fetchIntentdata();
    }

    void getSaveCustomer(){//retrieving instance state
        if(customer != null){
            Log.d("mTag", "getSaveCustomer: " + customer.getName());
            cid = customer.getCid();
            custID.setText(customer.getId());
            custName.setText(customer.getName());
            custNumber.setText(customer.getNumber());
            kameezLength.setText(customer.getKameez());
            tera.setText(customer.getTera());
            bazo.setText(customer.getBazo());
            moda.setText(customer.getModa());
            chati.setText(customer.getChati());
            fitting.setText(customer.getFitting());
            gera.setText(customer.getGera());
            gala.setText(customer.getGala());
            shalwarLength.setText(customer.getShalwar());
            poncha.setText(customer.getPoncha());
            fair.setText(customer.getFair());
            asan.setText(customer.getAsan());
            trouserLength.setText(customer.getTrouser());
            trouserPoncha.setText(customer.getTrouserPoncha());
            trouserFair.setText(customer.getTrouserFair());
            trouserAsan.setText(customer.getTrouserAsan());
            fracKameez = customer.getKameezFrac();
            fracTera = customer.getTeraFrac();
            fracBazo = customer.getBazoFrac();
            fracModa = customer.getModaFrac();
            fracChati = customer.getChatiFrac();
            fracFitting = customer.getFittingFrac();
            fracGera = customer.getGeraFrac();
            fracGala = customer.getGalaFrac();
            fracShalwar = customer.getShalwarFrac();
            fracPoncha = customer.getPonchaFrac();
            fracFair = customer.getFairFrac();
            fracAsan = customer.getAsanFrac();
            fracTrouser = customer.getTrouserFrac();
            fracTrouserPoncha = customer.getTrouserPonchaFrac();
            fracTrouserFair = customer.getTrouserFairFrac();
            fracTrouserAsan = customer.getTrouserAsanFrac();
            collar = customer.getCollar();
            collarWidth.setText(customer.getCollarWidth());
            fracCollarWidth = customer.getFracCollarWidth();
            collarLength.setText(customer.getCollarLength());
            fracCollarLength = customer.getFracCollarLength();
            kaff = customer.getKaff();
            kaffWidth.setText(customer.getKaffWidth());
            fracKaffWidth = customer.getFracKaffWidth();
            kaffLength.setText(customer.getKaffLength());
            fracKaffLength = customer.getFracKaffLength();
            salaeiTv.setText(customer.getSalaei());
            salaei = customer.getSalaei();
            gheeraTv.setText(customer.getGheera());
            gheera = customer.getGheera();
            dhagaTv.setText(customer.getDhaga());
            dhaga = customer.getDhaga();
            setKameezData(fracKameez);
            setTeraData(fracTera);
            setBazoData(fracBazo);
            setModaData(fracModa);
            setChatiData(fracChati);
            setFittingData(fracFitting);
            setGeraData(fracGera);
            setGalaData(fracGala);
            setShalwarData(fracShalwar);
            setPonchaData(fracPoncha);
            setFairData(fracFair);
            setAsanData(fracAsan);
            setTrouserData(fracTrouser);
            setTrouserPonchaData(fracTrouserPoncha);
            setTrouserFairData(fracTrouserFair);
            setTrouserAsanData(fracTrouserAsan);
            setCollarWidthData(fracCollarWidth);
            setCollarLengthData(fracCollarLength);
            setKaffWidthData(fracKaffWidth);
            setKaffLengthData(fracKaffLength);
            frontPocketedt.setText(customer.getFrontPocket());
            sidePocketedt.setText(customer.getSidePocket());
            shalwarPocketedt.setText(customer.getShalwarPocket());
            note.setText(customer.getNote());
        }
    }

}