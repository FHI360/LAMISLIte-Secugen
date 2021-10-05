package org.fhi360.lamis.mobile.lite.Activities;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
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

public class RistPage2 extends AppCompatActivity {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        this.preferences = getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        restorePreferences();
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString("assessmentReturn");
            assessmentReturn = new Gson().fromJson(json, AssessmentReturn.class);
        }

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
        final Integer sum = Integer.valueOf(digit) + 1;
        System.out.println("SUM " + sum);
        auoIncrementClientCode = steteId + "/" + facilityId + "/" + deviceconfigId + "/" + sum;
        System.out.println("CLEINT CODE " + auoIncrementClientCode);
        clientCode.setText(auoIncrementClientCode);
        System.out.println("ASSESSMENT" + assessmentReturn);
        clientCode.setText(assessmentReturn.getClientCode());
        dateVisit.setText(assessmentReturn.getDateVisit());
        String dateLastValue = assessmentReturn.getDateLastTestDone();
        System.out.println("dateLastValue "+dateLastValue);
        if (dateLastValue != null) {
        int idx = dateLastValue.lastIndexOf('/');
        if (idx == -1)
            throw new IllegalArgumentException("Only a single name: " + dateLastValue);
        String dateLastValue1 = dateLastValue.substring(0, idx);
        String dateLastValue2 = dateLastValue.substring(idx + 1);

        date_last_teted1.setText(dateLastValue1.trim());
        SettingConfig.setSpinText(date_last_teted, dateLastValue2);
        }
        if (assessmentReturn.getQuestion1() == 1) {
            SettingConfig.setSpinText(question1, "YES");
        } else if (assessmentReturn.getQuestion1() == 0) {
            SettingConfig.setSpinText(question1, "NO");
        }


        SettingConfig.setSpinText(question2, assessmentReturn.getQuestion2());

        if (assessmentReturn.getQuestion3() == 1) {
            SettingConfig.setSpinText(question3, "YES");
        } else if (assessmentReturn.getQuestion3() == 0) {
            SettingConfig.setSpinText(question3, "NO");
        }
        if (assessmentReturn.getQuestion4() == 1) {
            SettingConfig.setSpinText(question4, "YES");
        } else if (assessmentReturn.getQuestion4() == 0) {
            SettingConfig.setSpinText(question4, "NO");
        }
        if (assessmentReturn.getQuestion5() == 1) {
            SettingConfig.setSpinText(question5, "YES");
        } else if (assessmentReturn.getQuestion5() == 0) {
            SettingConfig.setSpinText(question5, "NO");
        }
        if (assessmentReturn.getQuestion6() == 1) {
            SettingConfig.setSpinText(question6, "YES");
        } else if (assessmentReturn.getQuestion6() == 0) {
            SettingConfig.setSpinText(question6, "NO");
        }
        if (assessmentReturn.getQuestion7() == 1) {
            SettingConfig.setSpinText(question7, "YES");
        } else if (assessmentReturn.getQuestion7() == 0) {
            SettingConfig.setSpinText(question7, "NO");
        }

        if (assessmentReturn.getQuestion8() == 1) {
            SettingConfig.setSpinText(question8, "YES");
        } else if (assessmentReturn.getQuestion8() == 0) {
            SettingConfig.setSpinText(question8, "NO");
        }
        if (assessmentReturn.getQuestion9() == 1) {
            SettingConfig.setSpinText(question9, "YES");
        } else if (assessmentReturn.getQuestion9() == 0) {
            SettingConfig.setSpinText(question9, "NO");
        }
        if (assessmentReturn.getQuestion10() == 1) {
            SettingConfig.setSpinText(question10, "YES");
        } else if (assessmentReturn.getQuestion10() == 0) {
            SettingConfig.setSpinText(question10, "NO");
        }
        if (assessmentReturn.getQuestion11() == 1) {
            SettingConfig.setSpinText(question11, "YES");
        } else if (assessmentReturn.getQuestion11() == 0) {
            SettingConfig.setSpinText(question11, "NO");
        }

        if (assessmentReturn.getGbv_1() == 1) {
            SettingConfig.setSpinText(question13, "YES");
        } else if (assessmentReturn.getGbv_2() == 0) {
            SettingConfig.setSpinText(question14, "NO");
        }

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
                final DatePickerDialog mDatePicker = new DatePickerDialog(RistPage2.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });


//        System.out.println("fourteen");
//        question2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (question7.getSelectedItem().toString().equals("YES")) {
//                    date_last_teted1.setVisibility(View.VISIBLE);
//                    date_last_teted.setVisibility(View.VISIBLE);
//                    question2.setVisibility(View.VISIBLE);
//
//                }
//                if (question7.getSelectedItem().toString().equals("NO")) {
//                    date_last_teted1.setVisibility(View.INVISIBLE);
//                    date_last_teted.setVisibility(View.VISIBLE);
//                    question2.setVisibility(View.INVISIBLE);
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
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
                assessment.setAssessmentId(assessmentReturn.getId());
                String pattern = "dd-MM-yyyy";
                String dateInString =new SimpleDateFormat(pattern).format(new Date());
                assessment.setTimeStamp(dateInString);
                new AssementDAO(this).updateRiskAssessment(assessment);
                session.setLastHtsSerialDigit(sum);
                if (question2.getSelectedItem().toString().equals("Positive")) {
                    showAlert();
                } else {
                    if (total1 > 0 && total == 0) {
                        FancyToast.makeText(this, "Record update successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(this, HtsRegistration.class);
                        session.saveAssessmentId(String.valueOf(assessmentReturn.getId()), dateVisit.getText().toString());
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


//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setMessage("Do you want to Exit?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                RiskAssessmentFragment fragment = new RiskAssessmentFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, fragment);
//                transaction.commit();
//
//
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //if user select "No", just cancel this dialog and continue with app
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//        android.os.Process.killProcess(android.os.Process.myPid());
//    }

    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(RistPage2.this);
        View promptsView = li.inflate(R.layout.popup2, null);
        final AlertDialog dialog = new AlertDialog.Builder(RistPage2.this).create();
        dialog.setView(promptsView);
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putString("assessmentReturn", new Gson().toJson(assessmentReturn));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String json = savedInstanceState.getString("assessmentReturn");
        assessmentReturn = new Gson().fromJson(json, AssessmentReturn.class);
    }

    private void restorePreferences() {
        String json = preferences.getString("assessmentReturn", "");
        assessmentReturn = new Gson().fromJson(json, AssessmentReturn.class);
    }


}
