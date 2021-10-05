package org.fhi360.lamis.mobile.lite.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.lamis.mobile.lite.DAO.AssementDAO;
import org.fhi360.lamis.mobile.lite.Domains.Assessment;
import org.fhi360.lamis.mobile.lite.Domains.AssessmentReturn;
import org.fhi360.lamis.mobile.lite.Fragments.RiskAssessmentFragment;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.fhi360.lamis.mobile.lite.Utils.Constant.PREFERENCES_ENCOUNTER;

public class RiskPage extends AppCompatActivity {
    private EditText clientCode, dateVisit;
    private AppCompatSpinner question1,
            question2, question3,
            question4, question5,
            question6, question7,
            question8, question9, question10, question11,question13,question14;
    private EditText date_last_teted1;
    private Spinner date_last_teted;
    private Button saved;
    private AssessmentReturn assessmentReturn;
    private boolean INITIAL = true;
    private SharedPreferences preferences;
    private String facilityName;
    private String lgaId;
    private String steteId;
    private String facilityId;
    private PrefManager session;
    private String deviceconfigId;
    private String auoIncrementClientCode;
    private TextView havebeen;
    private Calendar myCalendar = Calendar.getInstance();
    int sum = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        this.preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);

        dateVisit = findViewById(R.id.dateVisit);
        clientCode = findViewById(R.id.clientCode);
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        question5 = findViewById(R.id.question5);
        question6 = findViewById(R.id.question6);
        question7 = findViewById(R.id.question7);
        question8 = findViewById(R.id.question8);
        question9 = findViewById(R.id.question9);
        question10 = findViewById(R.id.question10);
        question11 = findViewById(R.id.question11);
        havebeen = findViewById(R.id.havebeen);
        date_last_teted1 = findViewById(R.id.dateTestDoneValue);
        date_last_teted = findViewById(R.id.date_last_teted);
        question13 = findViewById(R.id.question13);
        question14 = findViewById(R.id.question14);
        saved = findViewById(R.id.save);
        session = new PrefManager(this);
        clientCode.setEnabled(false);

        question14.setEnabled(false);

        HashMap<String, String> user = session.getHtsDetails();
        facilityName = user.get("faciltyName");
        lgaId = user.get("lgaId");
        steteId = user.get("stateId");
        facilityId = user.get("facilityId");
        deviceconfigId = user.get("deviceconfig_id");
        System.out.println("deviceconfigId " + deviceconfigId);
        System.out.println("facilityId " + facilityId);
        HashMap<String, String> lastSerialNumberOfHts = session.getLastHtsSerialDigit();
        String digit = lastSerialNumberOfHts.get("serialNumber");
        sum = Integer.valueOf(digit);
        sum++;
        System.out.println("SUM " + sum);
        auoIncrementClientCode = steteId + "/" + facilityId + "/" + deviceconfigId + "/" + sum;
        System.out.println("CLEINT CODE " + auoIncrementClientCode);
        clientCode.setText(auoIncrementClientCode);


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
                final DatePickerDialog mDatePicker = new DatePickerDialog(RiskPage.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }


        });
        question13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String questioon13 = question13.getSelectedItem().toString();
                if(questioon13.equals("YES")){
                    question14.setEnabled(true);
                }else {
                    question14.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//        date_last_teted1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String viralLoad = date_last_teted1.getText().toString();
//                if (viralLoad.equals("")) {
//                    date_last_teted1.setText("");
//                } else {
//                    if (viralLoad.length() == 2) {
//                        date_last_teted1.setError("Maximum");
//                    }else{
//                        date_last_teted1.setError("Does Not match");
////
//                    }
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });


        saved.setOnClickListener(view -> {
            if (validateInput(dateVisit.getText().toString())) {

                int total = 0;
                int total1 = 0;
                Assessment assessment = new Assessment();
                assessment.setFacilityId(Long.parseLong(facilityId));
                assessment.setDateVisit(dateVisit.getText().toString());
                assessment.setClientCode(auoIncrementClientCode);
                System.out.println("DATETESTDONE " + date_last_teted1.getText().toString() + "/" + date_last_teted.getSelectedItem().toString());
                assessment.setDateLastTestDone(date_last_teted1.getText().toString() + "/" + date_last_teted.getSelectedItem().toString());
                if (question1.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion1(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion1(0);
                    total = 0;
                }
                assessment.setQuestion2(question2.getSelectedItem().toString());

                if (question3.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion3(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion3(0);
                    total = 0;
                }
                if (question4.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion4(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion4(0);
                    total = 0;
                }
                if (question5.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion5(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion5(0);
                    total = 0;
                }
                if (question6.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion6(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion6(0);
                    total = 0;
                }
                if (question7.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion7(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion7(0);
                    total = 0;
                }
                if (question8.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion8(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion8(0);
                    total = 0;

                }
                if (question9.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion9(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion9(0);
                    total = 0;
                }
                if (question10.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion10(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion10(0);
                    total = 0;
                }
                if (question11.getSelectedItem().toString().equals("YES")) {
                    assessment.setQuestion11(1);
                    total1 = 1;
                } else {
                    assessment.setQuestion11(0);
                    total = 0;
                }
                if (question13.getSelectedItem().toString().equals("YES")) {
                    assessment.setGbv_1(1);
                    total1 = 1;
                } else {
                    assessment.setGbv_1(0);
                    total = 0;
                }

                if (question14.getSelectedItem().toString().equals("YES")) {
                    assessment.setGbv_2(1);
                    total1 = 1;
                } else {
                    assessment.setGbv_2(0);
                    total = 0;
                }

                assessment.setDeviceconfigId(Long.parseLong(deviceconfigId));
               // System.out.println("TIMESTAMP"+ new Timestamp(System.currentTimeMillis()));
                String pattern = "dd-MM-yyyy";
                String dateInString =new SimpleDateFormat(pattern).format(new Date());
                assessment.setTimeStamp(dateInString);
                // assessment.setAssessmentId(assessmentReturn.getId());
                long assessment_id = new AssementDAO(this).saveRiskAssessment(assessment);
                session.setLastHtsSerialDigit(sum);
                if (question2.getSelectedItem().toString().equals("Positive")) {
                    showAlert();
                } else {
                    if (total1 > 0 && total == 0) {
                        FancyToast.makeText(this, "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(this, HtsRegistration.class);
                        session.saveAssessmentId(String.valueOf(assessment_id), dateVisit.getText().toString());
                        session.setClientCode(auoIncrementClientCode);
                        startActivity(intent);
                    } else {
                        FancyToast.makeText(this, "Record saved successfully, Client is not eligible for HTS", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    }

                }
            }

        });


    }


    private boolean validateInput(String dateVisits) {
        if (dateVisits.isEmpty()) {
            dateVisit.setError("Enter date of visit");
            FancyToast.makeText(this, "Enter date of visit", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        return true;


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateVisit.setText(sdf.format(myCalendar.getTime()));

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
                    Intent intent = new Intent(RiskPage.this, Home.class);
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


    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(RiskPage.this);
        View promptsView = li.inflate(R.layout.popup2, null);
        final AlertDialog dialog = new AlertDialog.Builder(RiskPage.this).create();
    }


}
