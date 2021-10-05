package org.fhi360.lamis.mobile.lite.Activities;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.*;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.lamis.mobile.lite.DAO.ANCDAO;
import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.Domains.ANC;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Fragments.RiskAssessmentFragment;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.nio.BufferUnderflowException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class ANCActivity extends AppCompatActivity {

    private EditText sourcesOfReferer, dateVisit, surname, gestationalAge, otherName, hospitalNumber, name, age, address, phoneNumber;

    private AppCompatSpinner syphilisTested;
    private AppCompatSpinner syphilisTestResult;
    private AppCompatSpinner syphilisTreated;
    private CheckBox referredFrom;
    private PrefManager session;
    //ance
    private EditText ancNo, lmp;
    private EditText gravida, parity;
    private Button save;
    private Calendar myCalendar = Calendar.getInstance();

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anc_activity);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        dateVisit = findViewById(R.id.dateVisit);
        hospitalNumber = findViewById(R.id.hospitalNum);
        ancNo = findViewById(R.id.ancNumber);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phonenumber);
        lmp = findViewById(R.id.LMP);
        age = findViewById(R.id.age);
        gestationalAge = findViewById(R.id.gestational);
        gravida = findViewById(R.id.gravida);
        parity = findViewById(R.id.parity);
        sourcesOfReferer = findViewById(R.id.source_of_referral);
        syphilisTestResult = findViewById(R.id.syphilis_result);
        syphilisTested = findViewById(R.id.tested_syhpilis);
        syphilisTreated = findViewById(R.id.treated_syphilis);
        referredFrom = findViewById(R.id.referredFrom);
        surname = findViewById(R.id.surname);
        otherName = findViewById(R.id.otherName);
        save = findViewById(R.id.save);
        session = new PrefManager(this);
        HashMap<String, String> user = session.getHtsDetails();
        String facilityName = user.get("faciltyName");
        String lgaId = user.get("lgaId");
        String steteId = user.get("stateId");
        String facilityId = user.get("facilityId");


        syphilisTested.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String testValue = syphilisTested.getSelectedItem().toString();
                System.out.println("testValue "+testValue);
                if (testValue.equalsIgnoreCase("NO")) {

                    syphilisTestResult.setEnabled(false);
                    syphilisTreated.setEnabled(false);
                    referredFrom.setEnabled(false);
                } else if (testValue.equalsIgnoreCase("YES")) {

                    syphilisTestResult.setEnabled(true);
                    syphilisTreated.setEnabled(true);
                    referredFrom.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateVisit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(ANCActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }

        });
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };

        lmp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(ANCActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }

        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ANC anc = new ANC();
                anc.setFacilityName(facilityName);
                anc.setFacilityId(Long.valueOf(Objects.requireNonNull(facilityId)));
                anc.setAncNum(ancNo.getText().toString());
                anc.setLmp(lmp.getText().toString());
                anc.setSourceReferral(sourcesOfReferer.getText().toString());
                anc.setGestationalAge(Integer.valueOf(gestationalAge.getText().toString()));
                anc.setGravida(Integer.valueOf(gravida.getText().toString()));
                anc.setParity(Integer.valueOf(parity.getText().toString()));
                if (syphilisTested.getSelectedItem().toString().equals("YES")) {
                    anc.setSyphilisTested(1);
                } else {
                    anc.setSyphilisTested(0);
                }
                if (syphilisTreated.getSelectedItem().toString().equals("YES")) {
                    anc.setSyphilisTreated(1);
                } else {
                    anc.setSyphilisTreated(0);
                }
                anc.setSyphilisTestResult(syphilisTestResult.getSelectedItem().toString());
                if (referredFrom.isChecked()) {
                    anc.setReferredFrom(1);
                } else {
                    anc.setReferredFrom(0);
                }
                new ANCDAO(getApplicationContext()).save(anc);
                session.createANC(dateVisit.getText().toString(), hospitalNumber.getText().toString(), ancNo.getText().toString(), age.getText().toString(), surname.getText().toString(), otherName.getText().toString(),
                        referredFrom.getText().toString(), phoneNumber.getText().toString(), address.getText().toString());
                FancyToast.makeText(getApplicationContext(), "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                Intent intent = new Intent(getApplicationContext(), PMCTHTS.class);
                startActivity(intent);

            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateVisit.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        lmp.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("DO YOU WISH TO EXIST MODULE OR APP?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HashMap<String, Integer> stringIntegerHashMap = session.getFragmentState();
                int count = stringIntegerHashMap.get("state");
                if (count == 0) {
                    Fragment fragment = new RiskAssessmentFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit();
                    ScrollView view = findViewById(R.id.ristassessment);
                    view.setVisibility(View.INVISIBLE);
                    session.saveFragment(1);
                } else {
                    session.clear();
                    Intent intent = new Intent(ANCActivity.this, Home.class);
                    startActivity(intent);
                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
