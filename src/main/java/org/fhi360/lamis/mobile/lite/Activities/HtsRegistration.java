package org.fhi360.lamis.mobile.lite.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.library.NavigationBar;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.lamis.mobile.lite.DAO.GeoLocationDAO;
import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.LgaDAO;
import org.fhi360.lamis.mobile.lite.DAO.StateDAO;
import org.fhi360.lamis.mobile.lite.Domains.*;
import org.fhi360.lamis.mobile.lite.Fragments.RiskAssessmentFragment;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Service.NotificationService;
import org.fhi360.lamis.mobile.lite.Utils.GetCurrentLocation;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


public class HtsRegistration extends AppCompatActivity {
    private EditText indexClientCode, hivTestResult, surname, otherName, dateBirth, age,
            dateVisit, phone, address, clientCode, clientCode1, comments, initialhivTestResult;
    private AppCompatSpinner gender, firstTimeVisit, state, lgas, ward, maritalStatus, numChildren, numWives,
            typeCounseling, indexClient, typeIndex, referredFrom,
            knowledgeAssessment1, knowledgeAssessment2, knowledgeAssessment3, knowledgeAssessment4,
            knowledgeAssessment5, knowledgeAssessment6, knowledgeAssessment7,
            riskAssessment1, riskAssessment2, riskAssessment3, riskAssessment4, riskAssessment5, riskAssessment6,
            tbScreening1, tbScreening2, tbScreening3, tbScreening4, stiScreening1, stiScreening2, stiScreening3, stiScreening4, stiScreening5, testedHiv2, postTest1, postTest2, postTest3, postTest4, postTest5,
            postTest6, postTest7, postTest8, postTest9, postTest10, postTest11, postTest12,
            postTest13, postTest14, syphilisTestResult, hepatitiscTestResult, hepatitisbTestResult,
            testResult, confirmatoryTestResult, tieBreaker, ageUnit,
            initialtestResult, initilaconfirmatoryTestResult, initialtieBreaker;
    private CheckBox stiReferred, tbReferred, artReferred;

    private Calendar myCalendar = Calendar.getInstance();
    private Button finishButton;

    private String auoIncrementClientCode;
    private ScrollView activity_step_one;
    private ScrollView activity_step_two;
    private ScrollView activity_step_three;
    private ScrollView activity_step_four;
    private String facilityName;
    private String lgaId;
    private String steteId;
    private String facilityId;
    private PrefManager session;
    private NavigationBar bar;
    private int position = 0;
    private String status = "";
    private long id = 0;
    private SettingConfig settingConfig = new SettingConfig();
    private String name;
    private TextView clientDetails, stiScreening1F, stiScreening1F1, testingSetting, communitytesting1;
    private HashMap<String, String> coordinate;
    private TextView clientPregnant;
    private String pin;
    private String deviceconfigId, camCode1;
    private String assessmentId = "";
    GetCurrentLocation currentLoc;
    private LocationManager locationManager;
    private EditText nextKinName;
    private EditText nextKinPone;
    private EditText nextKinAddress;
    private EditText nextKinDescriptiveAddress;
    private EditText nextKinStreet;
    private EditText nextKinCity;
    private EditText nextKinLandmark, supperTesterName;
    private EditText longitude, latitude;
    private Button btn_prev, btn_next;
    int counter = 1;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    try {
        setContentView(R.layout.fragment_home);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        currentLoc = new GetCurrentLocation(this);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));

        bar = findViewById(R.id.navBar);
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_prev);


        longitude = findViewById(R.id.tvLongitudes);
        latitude = findViewById(R.id.tvLatitudes);
        final Button captureLocation = findViewById(R.id.captureLocation);
        activity_step_one = findViewById(R.id.activity_step_one);
        activity_step_two = findViewById(R.id.activity_step_two);
        activity_step_three = findViewById(R.id.activity_step_three);
        activity_step_four = findViewById(R.id.activity_step_four);
        session = new PrefManager(this);
        surname = findViewById(R.id.surname);
        otherName = findViewById(R.id.otherName);
        dateBirth = findViewById(R.id.dateBirth);
        stiScreening1F = findViewById(R.id.stiScreening1F);
        stiScreening1F1 = findViewById(R.id.stiScreening1F1);
        communitytesting1 = findViewById(R.id.communitytesting1);
        dateVisit = findViewById(R.id.dateVisit);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        firstTimeVisit = findViewById(R.id.firstTimeVisit);
        state = findViewById(R.id.state);
        lgas = findViewById(R.id.lgas);
        maritalStatus = findViewById(R.id.maritalStatus);
        phone = findViewById(R.id.phone);
        numChildren = findViewById(R.id.numChildren);
        numWives = findViewById(R.id.numWives2);
        numWives.setClickable(false);
        numWives.setEnabled(false);
        typeCounseling = findViewById(R.id.typeCounseling);
        indexClient = findViewById(R.id.indexClient);
        typeIndex = findViewById(R.id.typeIndex);
        indexClientCode = findViewById(R.id.indexClientCode);
        referredFrom = findViewById(R.id.referredFrom);
        clientCode = findViewById(R.id.clientCode);
        supperTesterName = findViewById(R.id.supperTesterName);


        initialtestResult = findViewById(R.id.initialtestResult);
        initilaconfirmatoryTestResult = findViewById(R.id.initilaconfirmatoryTestResult);
        initialtieBreaker = findViewById(R.id.initialtieBreaker);
        initialhivTestResult = findViewById(R.id.initialhivTestResult);

        initilaconfirmatoryTestResult.setEnabled(false);
        initialtieBreaker.setEnabled(false);
        initialhivTestResult.setEnabled(false);


        nextKinName = findViewById(R.id.name);
        nextKinPone = findViewById(R.id.next_of_phone);
        nextKinAddress = findViewById(R.id.next_of_address);
        nextKinDescriptiveAddress = findViewById(R.id.descriptive_address);
        nextKinStreet = findViewById(R.id.streat_name_city_landmark);
        nextKinCity = findViewById(R.id.city);
        nextKinLandmark = findViewById(R.id.landmark);
        testingSetting = findViewById(R.id.testingSetting);
        //   ward = findViewById(R.id.ward);
        clientCode1 = findViewById(R.id.clientCode1);
        clientCode1.setEnabled(false);
        HashMap<String, String> user = session.getHtsDetails();
        HashMap<String, String> assesment = session.getAssessmentId();

        HashMap<String, String> camCode = session.getCam();
        camCode1 = camCode.get("camCode");

        assessmentId = assesment.get("assessmentId");
        System.out.println("ASSESSMENT ID " + assessmentId);
        facilityName = user.get("faciltyName");
        lgaId = user.get("lgaId");
        steteId = user.get("stateId");
        facilityId = user.get("facilityId");
        status = user.get("status");
        pin = user.get("deviceconfig_id");
        deviceconfigId = user.get("deviceconfig_id");
        clientDetails = findViewById(R.id.clientDetails);
        clientDetails.setText("Client Details   (" + facilityName + ")");
        HashMap<String, String> code = session.getClientCode();
        auoIncrementClientCode = code.get("clientCode");
        String dateVisit1 = user.get("date_visit");


        HashMap<String, String> testingSetting1 = getGetTestingModality();
        String testingValue = testingSetting1.get("testModality");
        String ModalityValue = testingSetting1.get("testModality1");
        System.out.println("TESTINGVALUE " + testingValue);
        System.out.println("ModalityValue " + ModalityValue);

        testingSetting.setText(testingValue);
        communitytesting1.setText(ModalityValue);

        clientCode1.setText(auoIncrementClientCode);
        knowledgeAssessment1 = findViewById(R.id.knowledgeAssessment1);
        knowledgeAssessment2 = findViewById(R.id.knowledgeAssessment2);
        clientPregnant = findViewById(R.id.clientPregnant);
        clientPregnant.setVisibility(View.INVISIBLE);
        knowledgeAssessment2.setVisibility(View.INVISIBLE);

        knowledgeAssessment3 = findViewById(R.id.knowledgeAssessment3);
        knowledgeAssessment4 = findViewById(R.id.knowledgeAssessment4);
        knowledgeAssessment5 = findViewById(R.id.knowledgeAssessment5);
        knowledgeAssessment6 = findViewById(R.id.knowledgeAssessment6);
        knowledgeAssessment7 = findViewById(R.id.knowledgeAssessment7);
        riskAssessment1 = findViewById(R.id.riskAssessment1);
        riskAssessment2 = findViewById(R.id.riskAssessment2);
        riskAssessment3 = findViewById(R.id.riskAssessment3);
        riskAssessment4 = findViewById(R.id.riskAssessment4);
        riskAssessment5 = findViewById(R.id.riskAssessment5);
        riskAssessment6 = findViewById(R.id.riskAssessment6);
        tbScreening1 = findViewById(R.id.tbScreening1);
        tbScreening2 = findViewById(R.id.tbScreening2);
        tbScreening3 = findViewById(R.id.tbScreening3);
        tbScreening4 = findViewById(R.id.tbScreening4);
        stiScreening1 = findViewById(R.id.stiScreening1);
        stiScreening2 = findViewById(R.id.stiScreening2);
        stiScreening1.setVisibility(View.INVISIBLE);
        stiScreening2.setVisibility(View.INVISIBLE);
        stiScreening1F.setVisibility(View.INVISIBLE);
        stiScreening1F1.setVisibility(View.INVISIBLE);

        stiScreening3 = findViewById(R.id.tbScreening3);
        stiScreening4 = findViewById(R.id.tbScreening4);
        stiScreening5 = findViewById(R.id.stiScreening5);
//            ageUnit = findViewById(R.id.ageUnit);


        testResult = findViewById(R.id.testResult);
        confirmatoryTestResult = findViewById(R.id.confirmatoryTestResult);
        tieBreaker = findViewById(R.id.tieBreaker);
        hivTestResult = findViewById(R.id.hivTestResult);

        testResult.setEnabled(false);
        confirmatoryTestResult.setEnabled(false);
        tieBreaker.setEnabled(false);
        hivTestResult.setEnabled(false);

        testedHiv2 = findViewById(R.id.testedHiv2);
        postTest1 = findViewById(R.id.postTest1);
        postTest2 = findViewById(R.id.postTest2);
        postTest3 = findViewById(R.id.postTest3);
        postTest4 = findViewById(R.id.postTest4);
        postTest5 = findViewById(R.id.postTest5);
        postTest6 = findViewById(R.id.postTest6);
        postTest7 = findViewById(R.id.postTest7);
        postTest8 = findViewById(R.id.postTest8);
        postTest9 = findViewById(R.id.postTest9);
        postTest10 = findViewById(R.id.postTest10);
        postTest11 = findViewById(R.id.postTest11);
        postTest12 = findViewById(R.id.postTest12);
        postTest13 = findViewById(R.id.postTest13);
        postTest14 = findViewById(R.id.postTest14);
        syphilisTestResult = findViewById(R.id.syphilisTestResult);
        hepatitisbTestResult = findViewById(R.id.hepatitisbTestResult);
        hepatitiscTestResult = findViewById(R.id.hepatitiscTestResult);
        comments = findViewById(R.id.comments);
        stiReferred = findViewById(R.id.stiReferred);
        tbReferred = findViewById(R.id.tbReferred);
        artReferred = findViewById(R.id.artReferred);
        stiReferred.setVisibility(View.INVISIBLE);

        artReferred.setEnabled(false);
        tbReferred.setVisibility(View.INVISIBLE);
        finishButton = findViewById(R.id.finishButton);
        final SettingConfig settingConfig = new SettingConfig();

        dateVisit.setText(dateVisit1);
        ArrayList lgaIds = new ArrayList();
        final ArrayList arrayListStateId = new ArrayList();
        ArrayList arrayListStateName = new ArrayList();
        lgaIds = new ArrayList();
        ArrayList<State> states = new ArrayList<>();
        states = new StateDAO(this).getStates();
        for (State state1 : states) {
            arrayListStateId.add(state1.getId());
            arrayListStateName.add(state1.getName());
        }
        //setup(true, counter);

        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(HtsRegistration.this,
                R.layout.spinner_items, arrayListStateName);
        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        districtAdapter.notifyDataSetChanged();
        state.setAdapter(districtAdapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long stateId = (long) arrayListStateId.get(position);
                ArrayList lgaIds = new ArrayList();
                ArrayList arrayListLgaName = new ArrayList();
                ArrayList<Lga> lga1;
                lga1 = new LgaDAO(getApplicationContext()).getLgaByStateId(stateId);
                for (Lga lga2 : lga1) {
                    session.setLgaId(lga2.getId());
                    lgaIds.add(lga2.getId());
                    arrayListLgaName.add(lga2.getName());

                }
                final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(HtsRegistration.this,
                        R.layout.spinner_items, arrayListLgaName);
                districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                districtAdapter.notifyDataSetChanged();
                lgas.setAdapter(districtAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        indexClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String client = indexClient.getSelectedItem().toString();
                if (client.equalsIgnoreCase("NO")) {
                    typeIndex.setEnabled(false);
                    indexClientCode.setEnabled(false);
                } else if (client.equalsIgnoreCase("YES")) {
                    typeIndex.setEnabled(true);
                    indexClientCode.setEnabled(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//            lgas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    HashMap<String, String> wardLgaId = session.getLgaId();
//                    final String lgaId = wardLgaId.get("lgaId");
//                    List<Ward> wardByLgaId = new WardDAO(getApplicationContext()).getWardByLgaId(lgaId);
//                    ArrayList arrayListWarName = new ArrayList();
//                    for (Ward ward : wardByLgaId) {
//                        arrayListWarName.add(ward.getName());
//                    }
//                    final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(HtsRegistration.this,
//                            R.layout.spinner_items, arrayListWarName);
//                    districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
//                    districtAdapter.notifyDataSetChanged();
//                    ward.setAdapter(districtAdapter);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
        EditText edittext = findViewById(R.id.dateVisit);
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

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(HtsRegistration.this, date, myCalendar
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
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe2();
            }

        };

        dateBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(HtsRegistration.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender1 = gender.getSelectedItem().toString();
                if (gender1.equalsIgnoreCase("Female")) {
                    clientPregnant.setVisibility(View.VISIBLE);
                    knowledgeAssessment2.setVisibility(View.VISIBLE);
                    stiScreening1F.setVisibility(View.VISIBLE);
                    stiScreening1F1.setVisibility(View.VISIBLE);
                    stiScreening1.setVisibility(View.VISIBLE);
                    stiScreening1.setVisibility(View.VISIBLE);
                } else if (gender1.equalsIgnoreCase("Male")) {
                    clientPregnant.setVisibility(View.INVISIBLE);
                    knowledgeAssessment2.setVisibility(View.INVISIBLE);
                    stiScreening1F.setVisibility(View.INVISIBLE);
                    stiScreening1F1.setVisibility(View.INVISIBLE);
                    stiScreening1.setVisibility(View.INVISIBLE);
                    stiScreening1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//            testingSetting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    if (testingSetting.getSelectedItem().toString().equals("Community")) {
//                      ///  showAlertLocation();
//                        communitytesting2.setVisibility(View.VISIBLE);
//                        communitytesting.setVisibility(View.VISIBLE);
//                        communitytesting1.setVisibility(View.VISIBLE);
//                    }
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });


        tbScreening1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tbScreen = tbScreening1.getSelectedItem().toString();
                if (tbScreen.equals("YES")) {
                    tbReferred.setVisibility(View.VISIBLE);
                }
                if (tbScreen.equals("NO")) {
                    tbReferred.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tbScreening2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tbScreen = tbScreening2.getSelectedItem().toString();
                if (tbScreen.equals("YES")) {
                    tbReferred.setVisibility(View.VISIBLE);
                }
                if (tbScreen.equals("NO")) {
                    tbReferred.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tbScreening3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tbScreen = tbScreening3.getSelectedItem().toString();
                if (tbScreen.equals("YES")) {
                    tbReferred.setVisibility(View.VISIBLE);
                }
                if (tbScreen.equals("NO")) {
                    tbReferred.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tbScreening4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tbScreen = tbScreening4.getSelectedItem().toString();
                if (tbScreen.equals("YES")) {
                    tbReferred.setVisibility(View.VISIBLE);
                }
                if (tbScreen.equals("NO")) {
                    tbReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stiScreening1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String stIScreen = stiScreening1.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                }
                if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stiScreening2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String stIScreen = stiScreening2.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                }
                if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stiScreening3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String stIScreen = stiScreening3.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                }
                if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stiScreening4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String stIScreen = stiScreening4.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                }
                if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        stiScreening5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String stIScreen = stiScreening5.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                }
                if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        knowledgeAssessment7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String knowledgeAssessment71 = knowledgeAssessment7.getSelectedItem().toString();
                if (knowledgeAssessment71.equalsIgnoreCase("NO")) {
                    hivTestResult.setEnabled(false);
                } else {
                    hivTestResult.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                System.out.println("counter " + counter);
                if (counter <= 4) {
                    System.out.println("counter 2 " + counter);
                    setup(true, counter);
                    btn_prev.setEnabled(true);
                    if (counter == 4) {
                        btn_next.setEnabled(false);
                    }
                }

            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter--;
                if (!(counter < 0)) {
                    if (counter == 1) {
                        btn_prev.setEnabled(false);
                    }
                    setup(true, counter);
                    btn_next.setEnabled(true);
                }


            }
        });

        initialtestResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hivTest2 = initialtestResult.getSelectedItem().toString();
                if (hivTest2.equalsIgnoreCase("Reactive")) {
                    initilaconfirmatoryTestResult.setEnabled(true);
                    initialtieBreaker.setEnabled(false);
                    SettingConfig.setSpinText(tieBreaker, "");
                    initialhivTestResult.setEnabled(false);
                    hivTestResult.setText("");
                } else if (hivTest2.equalsIgnoreCase("Non-Reactive")) {
                    initilaconfirmatoryTestResult.setEnabled(false);
                    SettingConfig.setSpinText(initilaconfirmatoryTestResult, "");
                    SettingConfig.setSpinText(initialtieBreaker, "");
                    initialtieBreaker.setEnabled(false);
                    initialhivTestResult.setText("Negative");
                    hivTestResult.setText("");


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initilaconfirmatoryTestResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hivTest2 = initilaconfirmatoryTestResult.getSelectedItem().toString();
                if (hivTest2.equalsIgnoreCase("Reactive")) {
                    initilaconfirmatoryTestResult.setEnabled(true);
                    initialtieBreaker.setEnabled(false);
                    SettingConfig.setSpinText(initialtieBreaker, "");
                    hivTestResult.setText("");
                    initialhivTestResult.setText("Positive");
                    initialhivTestResult.setEnabled(false);
                    artReferred.setEnabled(false);
                    artReferred.setChecked(false);
                    testResult.setEnabled(true);
                    confirmatoryTestResult.setEnabled(false);

                } else if (hivTest2.equalsIgnoreCase("Non-Reactive")) {
                    initilaconfirmatoryTestResult.setEnabled(true);
                    // SettingConfig.setSpinText(initilaconfirmatoryTestResult, "");
                    SettingConfig.setSpinText(initialtieBreaker, "");
                    initialtieBreaker.setEnabled(true);
                    initialhivTestResult.setText("");
                    hivTestResult.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initialtieBreaker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hivTest2 = initialtieBreaker.getSelectedItem().toString();
                if (hivTest2.equalsIgnoreCase("Reactive")) {
                    hivTestResult.setText("");
                    initialhivTestResult.setText("Positive");
                    artReferred.setEnabled(false);
                    artReferred.setChecked(false);
                    testResult.setEnabled(true);
                    confirmatoryTestResult.setEnabled(false);
                    tieBreaker.setEnabled(false);

                } else if (hivTest2.equalsIgnoreCase("Non-Reactive")) {
                    initialtieBreaker.setEnabled(true);
                    hivTestResult.setText("");
                    initialhivTestResult.setText("Negative");
                    artReferred.setVisibility(View.INVISIBLE);
                    testResult.setEnabled(false);
                    confirmatoryTestResult.setEnabled(false);
                    tieBreaker.setEnabled(false);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        testResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hivTest2 = testResult.getSelectedItem().toString();
                if (hivTest2.equalsIgnoreCase("Reactive")) {
                    confirmatoryTestResult.setEnabled(true);
                    tieBreaker.setEnabled(false);
                    SettingConfig.setSpinText(tieBreaker, "");
                    hivTestResult.setEnabled(false);

                } else if (hivTest2.equalsIgnoreCase("Non-Reactive")) {
                    confirmatoryTestResult.setEnabled(false);
                    SettingConfig.setSpinText(confirmatoryTestResult, "");
                    SettingConfig.setSpinText(tieBreaker, "");
                    tieBreaker.setEnabled(false);
                    hivTestResult.setText("Negative");


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        confirmatoryTestResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hivTest2 = confirmatoryTestResult.getSelectedItem().toString();
                if (hivTest2.equalsIgnoreCase("Reactive")) {
                    hivTestResult.setText("Positive");
                    artReferred.setEnabled(true);
                    tieBreaker.setEnabled(false);
                    artReferred.setVisibility(View.VISIBLE);
                    SettingConfig.setSpinText(tieBreaker, "");
                } else if (hivTest2.equalsIgnoreCase("Non-Reactive")) {
                    tieBreaker.setEnabled(true);
                    artReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tieBreaker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hivTest2 = tieBreaker.getSelectedItem().toString();
                if (hivTest2.equalsIgnoreCase("Reactive")) {
                    hivTestResult.setText("Positive");
                    artReferred.setEnabled(true);
                    artReferred.setVisibility(View.VISIBLE);
                } else if (hivTest2.equalsIgnoreCase("Non-Reactive")) {
                    tieBreaker.setEnabled(true);
                    hivTestResult.setText("Negative");
                    artReferred.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initialhivTestResult.setText("");
        initialhivTestResult.setEnabled(false);
        hivTestResult.setText("");
        hivTestResult.setEnabled(false);
        setupFloatingLabelError();
        //setupFloatingLabelErrorAge();


        maritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String mar = maritalStatus.getSelectedItem().toString();
                if (!(mar.equals("Single") || mar.equals(""))) {
                    numWives.setEnabled(true);
                    numWives.setClickable(true);
                } else {
                    numWives.setEnabled(false);
                    numWives.setClickable(false);
                    SettingConfig.setSpinText(numWives, "");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        captureLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLoc.connectGoogleApi();
                latitude.setVisibility(View.VISIBLE);
                longitude.setVisibility(View.VISIBLE);
                if (new PrefManager(getApplicationContext()).getCoordnitae().get("longitude") != null && new PrefManager(getApplicationContext()).getCoordnitae().get("latitude") != "") {
                    String longitude1 = new PrefManager(getApplicationContext()).getCoordnitae().get("longitude");
                    String latitude1 = new PrefManager(getApplicationContext()).getCoordnitae().get("latitude");
                    longitude.setText(longitude1);
                    latitude.setText(latitude1);
                }
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput(surname.getText().toString(),
                        dateBirth.getText().toString(),
                        dateVisit.getText().toString(), clientCode.getText().toString()
                )) {

                    if (hivTestResult.getText().toString().equals("") && knowledgeAssessment7.getSelectedItem().toString().equals("N0")) {
                        FancyToast.makeText(HtsRegistration.this, "Client consent is NO. Please exit.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                    if (latitude.getText().toString().equals("")) {
                        longitude.setError("Longitude Can not be empty");
                        latitude.setError("Latitude Can not be empty");
                        latitude.setVisibility(View.VISIBLE);
                        longitude.setVisibility(View.VISIBLE);
                        FancyToast.makeText(HtsRegistration.this, "Please Capture Your Location", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else if (gender.getSelectedItem().toString().equals("")) {
                        FancyToast.makeText(HtsRegistration.this, "Select gender", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else if (!gender.getSelectedItem().toString().equals("")) {
                        HashMap<String, String> user = session.getHtsDetails();
                        String facilityName = user.get("faciltyName");
                        String lgaId = user.get("lgaId");
                        String steteId = user.get("stateId");
                        String facilityId = user.get("facilityId");
                        Hts hts = new Hts();
                        hts.setRiskSatisfactionCode(auoIncrementClientCode);
                        hts.setStateId(Long.parseLong(steteId));
                        hts.setLgaId(Long.parseLong(lgaId));
                        hts.setFacilityId(Long.parseLong(facilityId));
                        hts.setFacilityName(facilityName);
                        System.out.println("CAMCODE " + camCode1);
                        hts.setCamCode(camCode1);
                        hts.setDateVisit(dateVisit.getText().toString());
                        if (assessmentId != null) {
                            hts.setAssessmentId(Long.parseLong(assessmentId));
                        } else {
                            hts.setAssessmentId(0);
                        }
                        hts.setLongitude(Float.parseFloat(longitude.getText().toString()));
                        hts.setLatitude(Float.parseFloat(latitude.getText().toString()));

                        name = surname.getText().toString() + "  " + otherName.getText().toString();
                        hts.setClientCode(clientCode.getText().toString());
                        if (referredFrom.getSelectedItem().toString().equalsIgnoreCase("")) {
                            hts.setReferredFrom("");
                        } else {
                            hts.setReferredFrom(referredFrom.getSelectedItem().toString());
                        }
                        HashMap<String, String> testingSetting1 = getGetTestingModality();
                        String testingValue = testingSetting1.get("testModality");
                        String ModalityValue = testingSetting1.get("testModality1");
                        hts.setTestingSetting(testingValue + "/" + ModalityValue);
                        hts.setSurname(surname.getText().toString());
                        hts.setOtherNames(otherName.getText().toString());
                        hts.setDateBirth(dateBirth.getText().toString());
                        hts.setPhone(phone.getText().toString());
                        hts.setAddress(address.getText().toString());
                        hts.setGender(gender.getSelectedItem().toString());
                        hts.setFirstTimeVisit(firstTimeVisit.getSelectedItem().toString());
                        hts.setState(state.getSelectedItem().toString());
                        if (state.getSelectedItem().toString().equals("")) {
                            hts.setLga("");
                        } else {
                            hts.setLga(lgas.getSelectedItem().toString());
                        }

                        /// hts.setWard(ward.getSelectedItem().toString());
                        hts.setMaritalStatus(maritalStatus.getSelectedItem().toString());
                        if (!numChildren.getSelectedItem().toString().equals("")) {
                            hts.setNumChildren(Integer.parseInt(numChildren.getSelectedItem().toString()));
                        }

                        if (!numWives.getSelectedItem().toString().equals("")) {
                            hts.setNumWives(Integer.parseInt(numWives.getSelectedItem().toString()));
                        }
                        hts.setTypeCounseling(typeCounseling.getSelectedItem().toString());
                        hts.setIndexClient(indexClient.getSelectedItem().toString());
                        hts.setTypeIndex(typeIndex.getSelectedItem().toString());
                        hts.setIndexClientCode(indexClientCode.getText().toString());
                        if (knowledgeAssessment1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setKnowledgeAssessment1(1);
                        } else if (knowledgeAssessment1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setKnowledgeAssessment1(0);
                        }
                        if (knowledgeAssessment2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setKnowledgeAssessment2(1);
                        } else if (knowledgeAssessment2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setKnowledgeAssessment2(0);
                        }
                        if (knowledgeAssessment3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setKnowledgeAssessment3(1);
                        } else if (knowledgeAssessment3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setKnowledgeAssessment3(0);
                        }
                        if (knowledgeAssessment4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setKnowledgeAssessment4(1);
                        } else if (knowledgeAssessment4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setKnowledgeAssessment4(0);
                        }
                        if (knowledgeAssessment5.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setKnowledgeAssessment5(1);
                        } else if (knowledgeAssessment5.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setKnowledgeAssessment5(0);
                        }
                        if (knowledgeAssessment6.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setKnowledgeAssessment6(1);
                        } else if (knowledgeAssessment6.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setKnowledgeAssessment6(0);
                        }
                        if (knowledgeAssessment7.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setKnowledgeAssessment7(1);
                        } else if (knowledgeAssessment7.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setKnowledgeAssessment7(0);
                        }
                        if (riskAssessment1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setRiskAssessment1(1);
                        } else if (riskAssessment1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setRiskAssessment1(0);
                        }
                        if (riskAssessment2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setRiskAssessment2(1);
                        } else if (riskAssessment2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setRiskAssessment2(0);
                        }
                        if (riskAssessment3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setRiskAssessment3(1);
                        } else if (riskAssessment3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setRiskAssessment3(0);
                        }
                        if (riskAssessment4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setRiskAssessment4(1);
                        } else if (riskAssessment4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setRiskAssessment4(0);
                        }
                        if (riskAssessment5.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setRiskAssessment5(1);
                        } else if (riskAssessment5.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setRiskAssessment5(0);
                        }
                        if (riskAssessment6.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setRiskAssessment6(1);
                        } else if (riskAssessment6.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setRiskAssessment6(0);
                        }
                        if (tbScreening1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setTbScreening1(1);
                        } else if (tbScreening1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setTbScreening1(0);
                        }
                        if (tbScreening2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setTbScreening2(1);
                        } else if (tbScreening2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setTbScreening2(0);
                        }
                        if (tbScreening3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setTbScreening3(1);
                        } else if (tbScreening3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setTbScreening3(0);
                        }
                        if (tbScreening4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setTbScreening4(1);
                        } else if (tbScreening4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setTbScreening4(0);
                        }
                        if (stiScreening1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setStiScreening1(1);
                        } else if (stiScreening1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setStiScreening1(0);
                        }
                        if (stiScreening2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setStiScreening2(1);
                        } else if (stiScreening2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setStiScreening2(0);
                        }
                        if (stiScreening3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setStiScreening3(1);
                        } else if (stiScreening3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setStiScreening3(0);
                        }
                        if (stiScreening4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setStiScreening4(1);
                        } else if (stiScreening4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setStiScreening4(0);
                        }
                        if (stiScreening5.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setStiScreening5(1);
                        } else if (stiScreening5.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setStiScreening5(0);
                        }

                        hts.setTestedHiv(testedHiv2.getSelectedItem().toString());
                        if (postTest1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest1(1);
                        } else if (postTest1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest1(0);
                        }
                        if (postTest2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest2(1);
                        } else if (postTest2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest2(0);
                        }
                        if (postTest3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest3(1);
                        } else if (postTest3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest3(0);
                        }
                        if (postTest4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest4(1);
                        } else if (postTest4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest4(0);
                        }
                        if (postTest5.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest5(1);
                        } else if (postTest5.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest5(0);
                        }
                        if (postTest6.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest6(1);
                        } else if (postTest6.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest6(0);
                        }
                        if (postTest7.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest7(1);
                        } else if (postTest7.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest7(0);
                        }
                        if (postTest8.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest8(1);
                        } else if (postTest8.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest8(0);
                        }
                        if (postTest9.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest9(1);
                        } else if (postTest9.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest9(0);
                        }
                        if (postTest10.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest10(1);
                        } else if (postTest10.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest10(0);
                        }

                        if (postTest11.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest11(1);
                        } else if (postTest11.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest11(0);
                        }

                        if (postTest12.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest12(1);
                        } else if (postTest12.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest12(0);
                        }

                        if (postTest13.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest13(1);
                        } else if (postTest13.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest13(0);
                        }

                        if (postTest14.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                            hts.setPostTest14(1);
                        } else if (postTest14.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                            hts.setPostTest14(0);
                        }
                        hts.setSyphilisTestResult(syphilisTestResult.getSelectedItem().toString());

                        hts.setDeviceconfigId(Long.parseLong(deviceconfigId));
                        hts.setHepatitisbTestResult(hepatitisbTestResult.getSelectedItem().toString());

                        hts.setHepatitiscTestResult(hepatitiscTestResult.getSelectedItem().toString());

                        hts.setNote(comments.getText().toString());
                        if (stiReferred.isChecked()) {
                            hts.setStiReferred(stiReferred.getText().toString());
                        } else {
                            hts.setStiReferred("");
                        }
                        if (artReferred.isChecked()) {
                            hts.setArtReferred(artReferred.getText().toString());
                        } else {
                            hts.setArtReferred("");
                        }
                        hts.setNextKinName(nextKinName.getText().toString());
                        hts.setNextKinPhone(nextKinPone.getText().toString());
                        hts.setNextKinAddress(nextKinAddress.getText().toString());
                        hts.setNextKinDescriptiveAddress(nextKinDescriptiveAddress.getText().toString());
                        hts.setNextKinStreet(nextKinStreet.getText().toString());
                        hts.setNextKinCity(nextKinCity.getText().toString());
                        hts.setNextKinLandmark(nextKinLandmark.getText().toString());

                        hts.setTestResult(initialtestResult.getSelectedItem().toString());
                        hts.setConfirmatoryTestResult(initilaconfirmatoryTestResult.getSelectedItem().toString());
                        hts.setTieBreakerTestResult(initialtieBreaker.getSelectedItem().toString());
                        hts.setInitialHivTestResult(initialhivTestResult.getText().toString());

                        hts.setRetestResult(testResult.getSelectedItem().toString());
                        hts.setRetestConfirmatoryResult(confirmatoryTestResult.getSelectedItem().toString());
                        hts.setRetestTieBreakerResult(tieBreaker.getSelectedItem().toString());
                        if (initialhivTestResult.getText().toString().equals("Negative")) {
                            hts.setHivTestResult("Negative");
                        } else {
                            hts.setHivTestResult(hivTestResult.getText().toString());
                        }
                        hts.setSupperTesterName(supperTesterName.getText().toString());


                        hts.setUploaded(0);
                        String pattern = "dd-MM-yyyy";
                        String dateInString = new SimpleDateFormat(pattern).format(new Date());
                        hts.setTimeStamp(dateInString);
                        System.out.println("HTS SAVE " + hts);
                        id = new HtsDAO(getApplicationContext()).addTestResult(hts);
                        System.out.println("HTS ID " + id);
                        FancyToast.makeText(HtsRegistration.this, "Test Result saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        clientCode1.setText(auoIncrementClientCode);
                        dateVisit.setText("");
                        clientCode1.setText(clientCode1.getText().toString());
                        surname.setText("");
                        otherName.setText("");
                        dateBirth.setText("");
                        //age.setText("");
                        phone.setText("");
                        address.setText("");
                        indexClientCode.setText("");
                        comments.setText("");
                        settingConfig.setSpinText(maritalStatus, "");
                        settingConfig.setSpinText(state, "");
                        settingConfig.setSpinText(lgas, "");
                        //  settingConfig.setSpinText(ageUnit, "");
                        settingConfig.setSpinText(gender, "");
                        settingConfig.setSpinText(firstTimeVisit, "");
                        settingConfig.setSpinText(numChildren, "");
                        settingConfig.setSpinText(numWives, "");
                        settingConfig.setSpinText(typeCounseling, "");
                        settingConfig.setSpinText(typeIndex, "");
                        settingConfig.setSpinText(knowledgeAssessment1, "");
                        settingConfig.setSpinText(knowledgeAssessment2, "");
                        settingConfig.setSpinText(knowledgeAssessment3, "");
                        settingConfig.setSpinText(knowledgeAssessment4, "");
                        settingConfig.setSpinText(knowledgeAssessment5, "");
                        settingConfig.setSpinText(knowledgeAssessment6, "");
                        settingConfig.setSpinText(knowledgeAssessment7, "");
                        settingConfig.setSpinText(riskAssessment1, "");
                        settingConfig.setSpinText(riskAssessment2, "");
                        settingConfig.setSpinText(riskAssessment3, "");
                        settingConfig.setSpinText(riskAssessment4, "");
                        settingConfig.setSpinText(riskAssessment5, "");
                        settingConfig.setSpinText(riskAssessment6, "");
                        settingConfig.setSpinText(tbScreening1, "");
                        settingConfig.setSpinText(tbScreening2, "");
                        settingConfig.setSpinText(tbScreening3, "");
                        settingConfig.setSpinText(tbScreening4, "");
                        settingConfig.setSpinText(stiScreening1, "");
                        settingConfig.setSpinText(stiScreening2, "");
                        settingConfig.setSpinText(stiScreening3, "");
                        settingConfig.setSpinText(stiScreening4, "");
                        settingConfig.setSpinText(stiScreening5, "");
                        settingConfig.setSpinText(postTest1, "");
                        settingConfig.setSpinText(postTest2, "");
                        settingConfig.setSpinText(postTest3, "");
                        settingConfig.setSpinText(postTest4, "");
                        settingConfig.setSpinText(postTest5, "");
                        settingConfig.setSpinText(postTest6, "");
                        settingConfig.setSpinText(postTest7, "");
                        settingConfig.setSpinText(postTest8, "");
                        settingConfig.setSpinText(postTest9, "");
                        settingConfig.setSpinText(postTest10, "");
                        settingConfig.setSpinText(postTest11, "");
                        settingConfig.setSpinText(postTest12, "");
                        settingConfig.setSpinText(postTest13, "");
                        settingConfig.setSpinText(postTest14, "");
                        stiReferred.setVisibility(View.INVISIBLE);
                        artReferred.setVisibility(View.INVISIBLE);
                        if (hivTestResult.getText().toString().equalsIgnoreCase("Positive")) {
                            showAlertAgeBound(id);
                        } else {
                            Intent intent = new Intent(HtsRegistration.this, Home.class);
                            startActivity(intent);
                        }

                    }
                }
            }
        });
//        } catch (Exception e) {
//
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        coordinate = session.getCoordnitae();
        if (coordinate.get("longitude") != null && coordinate.get("latitude") != "") {
            NotificationService notificationService = new NotificationService(getApplicationContext());
            notificationService.createNotification("LAMISLite", "Your device location is captured");
        } else {
            NotificationService notificationService = new NotificationService(getApplicationContext());
            notificationService.createNotification("LAMISLite", "Enable your GPS Location");

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            String indexClientCode2, surname2, otherName2, dateBirth2, phone2, dateVist2, address1,
                    testingSetting2, gender2, firstTimeVisit2, state2, lga2, maritalStatus2, numChildren2, numWives2,
                    typeCounseling2, indexClient2, typeIndex2, referredFrom2,
                    knowledgeAssessment12, knowledgeAssessment22, knowledgeAssessment32, knowledgeAssessment42,
                    knowledgeAssessment52, knowledgeAssessment62, knowledgeAssessment72,
                    riskAssessment12, riskAssessment22, riskAssessment32, riskAssessment42, riskAssessment52, riskAssessment62,
                    tbScreening12, tbScreening22, tbScreening32, tbScreening42, stiScreening12, stiScreening22, stiScreening32,
                    stiScreening42, stiScreening52, hivTestResult2, postTest1111, postTest21, postTest31,
                    postTest41, postTest51,
                    postTest61, postTest71, postTest81, postTest91, postTest101, postTest111, postTest121,
                    postTest131, postTest141, syphilisTestResult1, hepatitiscTestResult1, hepatitisbTestResult1, ageUnit1,
                    stiReferred1, tbReferred1, artReferred1;
            coordinate = session.getCoordnitae();
            if (coordinate.get("longitude") != null && coordinate.get("latitude") != "") {

                NotificationService notificationService = new NotificationService(getApplicationContext());
                notificationService.createNotification("LAMISLite", "Your device location is captured");
            } else {
                NotificationService notificationService = new NotificationService(getApplicationContext());
                notificationService.createNotification("LAMISLite", "Enable your GPS Location");

            }

            HashMap<String, String> user1 = session.getHtsInstance();
            indexClientCode2 = user1.get("indexClientCode");
            surname2 = user1.get("surname");
            otherName2 = user1.get("otherName");
            dateBirth2 = user1.get("dateBirth");
            dateVist2 = user1.get("dateVisit");
            phone2 = user1.get("phone");
            String age1 = user1.get("age");
            address1 = user1.get("address");
            testingSetting2 = user1.get("testingSetting");
            gender2 = user1.get("gender");
            firstTimeVisit2 = user1.get("firstTimeVisit");
            state2 = user1.get("state");
            lga2 = user1.get("lga");
            maritalStatus2 = user1.get("maritalStatus");
            numChildren2 = user1.get("numChildren");
            numWives2 = user1.get("numWives");
            typeCounseling2 = user1.get("typeCounseling");
            indexClient2 = user1.get("indexClient");
            typeIndex2 = user1.get("typeIndex");
            referredFrom2 = user1.get("referredFrom");

            clientCode1.setText(auoIncrementClientCode);
            knowledgeAssessment12 = user1.get("knowledgeAssessment1");
            knowledgeAssessment22 = user1.get("knowledgeAssessment2");
            knowledgeAssessment32 = user1.get("knowledgeAssessment3");
            knowledgeAssessment42 = user1.get("knowledgeAssessment4");
            knowledgeAssessment52 = user1.get("knowledgeAssessment5");
            knowledgeAssessment62 = user1.get("knowledgeAssessment6");
            knowledgeAssessment72 = user1.get("knowledgeAssessment7");
            riskAssessment12 = user1.get("riskAssessment1");
            riskAssessment22 = user1.get("riskAssessment2");
            riskAssessment32 = user1.get("riskAssessment3");
            riskAssessment42 = user1.get("riskAssessment4");
            riskAssessment52 = user1.get("riskAssessment5");
            riskAssessment62 = user1.get("riskAssessment6");
            tbScreening12 = user1.get("tbScreening1");
            tbScreening22 = user1.get("tbScreening2");
            tbScreening32 = user1.get("tbScreening3");
            tbScreening42 = user1.get("tbScreening4");
            stiScreening12 = user1.get("stiScreening1");
            stiScreening22 = user1.get("stiScreening2");
            stiScreening32 = user1.get("stiScreening3");
            stiScreening42 = user1.get("stiScreening4");
            stiScreening52 = user1.get("stiScreening5");
            hivTestResult2 = user1.get("hivTestResult");
            postTest1111 = user1.get("postTest1");
            postTest21 = user1.get("postTest2");
            postTest31 = user1.get("postTest3");
            postTest41 = user1.get("postTest4");
            postTest51 = user1.get("postTest5");
            postTest61 = user1.get("postTest6");
            postTest71 = user1.get("postTest7");
            postTest81 = user1.get("postTest8");
            postTest91 = user1.get("postTest9");
            postTest101 = user1.get("postTest10");
            postTest111 = user1.get("postTest11");
            postTest121 = user1.get("postTest12");
            postTest131 = user1.get("postTest13");
            postTest141 = user1.get("postTest14");
            syphilisTestResult1 = user1.get("syphilisTestResult");
            hepatitiscTestResult1 = user1.get("hepatitiscTestResult");
            hepatitisbTestResult1 = user1.get("hepatitisbTestResult");
            ageUnit1 = user1.get("ageUnit");
            String commentss = user1.get("comments");
            indexClientCode.setText(indexClientCode2);
            surname.setText(surname2);
            otherName.setText(otherName2);
            dateBirth.setText(dateBirth2);
            phone.setText(phone2);
            dateVisit.setText(dateVist2);
            comments.setText(commentss);
            age.setText(age1);
            address.setText(address1);
            hivTestResult.setText(hivTestResult2);
            HashMap<String, String> testingSetting1 = getGetTestingModality();
            String testingValue = testingSetting1.get("testModality");
            String ModalityValue = testingSetting1.get("testModality1");
            testingSetting.setText(testingValue);
            communitytesting1.setText(ModalityValue);
            SettingConfig.setSpinText(gender, gender2);
            SettingConfig.setSpinText(firstTimeVisit, firstTimeVisit2);
            SettingConfig.setSpinText(state, state2);
            SettingConfig.setSpinText(lgas, lga2);
            SettingConfig.setSpinText(maritalStatus, maritalStatus2);
            SettingConfig.setSpinText(numChildren, numChildren2);
            SettingConfig.setSpinText(numWives, numWives2);
            SettingConfig.setSpinText(typeCounseling, typeCounseling2);
            SettingConfig.setSpinText(indexClient, indexClient2);
            SettingConfig.setSpinText(typeIndex, typeIndex2);
            SettingConfig.setSpinText(referredFrom, referredFrom2);
            SettingConfig.setSpinText(knowledgeAssessment1, knowledgeAssessment12);
            SettingConfig.setSpinText(knowledgeAssessment2, knowledgeAssessment22);
            SettingConfig.setSpinText(knowledgeAssessment3, knowledgeAssessment32);
            SettingConfig.setSpinText(knowledgeAssessment4, knowledgeAssessment42);
            SettingConfig.setSpinText(knowledgeAssessment5, knowledgeAssessment52);
            SettingConfig.setSpinText(knowledgeAssessment6, knowledgeAssessment62);
            SettingConfig.setSpinText(knowledgeAssessment7, knowledgeAssessment72);
            SettingConfig.setSpinText(riskAssessment1, riskAssessment12);
            SettingConfig.setSpinText(riskAssessment2, riskAssessment22);
            SettingConfig.setSpinText(riskAssessment3, riskAssessment32);
            SettingConfig.setSpinText(riskAssessment4, riskAssessment42);
            SettingConfig.setSpinText(riskAssessment5, riskAssessment52);
            SettingConfig.setSpinText(riskAssessment6, riskAssessment62);
            SettingConfig.setSpinText(tbScreening1, tbScreening12);
            SettingConfig.setSpinText(tbScreening2, tbScreening22);
            SettingConfig.setSpinText(tbScreening3, tbScreening32);
            SettingConfig.setSpinText(tbScreening4, tbScreening42);
            SettingConfig.setSpinText(stiScreening1, stiScreening12);
            SettingConfig.setSpinText(stiScreening2, stiScreening22);
            SettingConfig.setSpinText(stiScreening3, stiScreening32);
            SettingConfig.setSpinText(stiScreening4, stiScreening42);
            SettingConfig.setSpinText(stiScreening5, stiScreening52);

            SettingConfig.setSpinText(postTest1, postTest1111);
            SettingConfig.setSpinText(postTest12, postTest21);
            SettingConfig.setSpinText(postTest3, postTest31);
            SettingConfig.setSpinText(postTest4, postTest41);
            SettingConfig.setSpinText(postTest5, postTest51);
            SettingConfig.setSpinText(postTest6, postTest61);
            SettingConfig.setSpinText(postTest7, postTest71);
            SettingConfig.setSpinText(postTest8, postTest81);
            SettingConfig.setSpinText(postTest9, postTest91);
            SettingConfig.setSpinText(postTest10, postTest101);
            SettingConfig.setSpinText(postTest11, postTest111);
            SettingConfig.setSpinText(postTest12, postTest121);
            SettingConfig.setSpinText(postTest13, postTest131);
            SettingConfig.setSpinText(postTest14, postTest131);
            SettingConfig.setSpinText(syphilisTestResult, syphilisTestResult1);
            SettingConfig.setSpinText(hepatitiscTestResult, hepatitiscTestResult1);
            SettingConfig.setSpinText(hepatitisbTestResult, hepatitisbTestResult1);
            SettingConfig.setSpinText(ageUnit, ageUnit1);
            SettingConfig.setSpinText(syphilisTestResult, syphilisTestResult1);

        } catch (Exception ignored) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("holdState", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("indexClientCode", indexClientCode.getText().toString());
            editor.putString("surname", surname.getText().toString());
            editor.putString("otherName", otherName.getText().toString());
            editor.putString("dateBirth", dateBirth.getText().toString());
            editor.putString("dateVisit", dateVisit.getText().toString());
            editor.putString("phone", phone.getText().toString());
            editor.putString("testingSetting", testingSetting.getText().toString());

            editor.putString("gender", gender.getSelectedItem().toString());
            editor.putString("firstTimeVisit", firstTimeVisit.getSelectedItem().toString());
            editor.putString("state", state.getSelectedItem().toString());
            editor.putString("lga", lgas.getSelectedItem().toString());
            editor.putString("address", address.getText().toString());
            editor.putString("auoIncrementClientCode", auoIncrementClientCode);
            editor.putString("maritalStatus", maritalStatus.getSelectedItem().toString());
            editor.putString("numChildren", numChildren.getSelectedItem().toString());
            editor.putString("numWives", numWives.getSelectedItem().toString());
            editor.putString("typeCounseling", typeCounseling.getSelectedItem().toString());
            editor.putString("indexClient", indexClient.getSelectedItem().toString());
            editor.putString("typeIndex", typeIndex.getSelectedItem().toString());
            editor.putString("referredFrom", referredFrom.getSelectedItem().toString());
            editor.putString("knowledgeAssessment1", knowledgeAssessment1.getSelectedItem().toString());
            editor.putString("knowledgeAssessment2", knowledgeAssessment2.getSelectedItem().toString());
            editor.putString("knowledgeAssessment3", knowledgeAssessment3.getSelectedItem().toString());
            editor.putString("knowledgeAssessment4", knowledgeAssessment4.getSelectedItem().toString());
            editor.putString("knowledgeAssessment5", knowledgeAssessment5.getSelectedItem().toString());
            editor.putString("knowledgeAssessment6", knowledgeAssessment6.getSelectedItem().toString());
            editor.putString("knowledgeAssessment7", knowledgeAssessment7.getSelectedItem().toString());
            editor.putString("riskAssessment1", riskAssessment1.getSelectedItem().toString());
            editor.putString("riskAssessment2", riskAssessment2.getSelectedItem().toString());
            editor.putString("riskAssessment3", riskAssessment3.getSelectedItem().toString());
            editor.putString("riskAssessment4", riskAssessment4.getSelectedItem().toString());
            editor.putString("riskAssessment5", riskAssessment5.getSelectedItem().toString());
            editor.putString("riskAssessment6", riskAssessment6.getSelectedItem().toString());
            editor.putString("tbScreening1", tbScreening1.getSelectedItem().toString());
            editor.putString("tbScreening2", tbScreening2.getSelectedItem().toString());
            editor.putString("tbScreening3", tbScreening3.getSelectedItem().toString());
            editor.putString("tbScreening4", tbScreening4.getSelectedItem().toString());
            editor.putString("stiScreening1", stiScreening1.getSelectedItem().toString());
            editor.putString("stiScreening2", stiScreening2.getSelectedItem().toString());
            editor.putString("stiScreening3", stiScreening3.getSelectedItem().toString());
            editor.putString("stiScreening4", stiScreening4.getSelectedItem().toString());
            editor.putString("stiScreening5", stiScreening5.getSelectedItem().toString());
            editor.putString("hivTestResult", hivTestResult.getText().toString());
            editor.putString("testedHiv2", testedHiv2.getSelectedItem().toString());
            editor.putString("postTest1", postTest1.getSelectedItem().toString());
            editor.putString("postTest2", postTest2.getSelectedItem().toString());
            editor.putString("postTest3", postTest3.getSelectedItem().toString());
            editor.putString("postTest4", postTest4.getSelectedItem().toString());
            editor.putString("postTest5", postTest5.getSelectedItem().toString());
            editor.putString("postTest6", postTest6.getSelectedItem().toString());
            editor.putString("postTest7", postTest7.getSelectedItem().toString());
            editor.putString("postTest8", postTest8.getSelectedItem().toString());
            editor.putString("postTest9", postTest9.getSelectedItem().toString());
            editor.putString("postTest10", postTest10.getSelectedItem().toString());
            editor.putString("postTest11", postTest11.getSelectedItem().toString());
            editor.putString("postTest12", postTest12.getSelectedItem().toString());
            editor.putString("postTest13", postTest13.getSelectedItem().toString());
            editor.putString("postTest14", postTest14.getSelectedItem().toString());
            editor.putString("syphilisTestResult", syphilisTestResult.getSelectedItem().toString());
            editor.putString("hepatitiscTestResult", hepatitiscTestResult.getSelectedItem().toString());
            editor.putString("hepatitisbTestResult", hepatitisbTestResult.getSelectedItem().toString());
//        editor.putString("ageUnit", ageUnit.getSelectedItem().toString());
            editor.putString("comment", comments.getText().toString());
            editor.apply();
        } catch (Exception ignored) {

        }

    }


    private void updateLabe2() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateBirth.setText(sdf.format(myCalendar.getTime()));
        int age1 = 0;
        try {
            age1 = SettingConfig.getAge(dateBirth.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateVisit.setText(sdf.format(myCalendar.getTime()));

    }

    public static boolean isValidPhoneNumber(String phone) {
        if ((phone.length() >= 3)) {
            if (phone.length() == 11) {
                return Patterns.PHONE.matcher(phone).matches();
            }

        }

        return false;
    }

    private void setupFloatingLabelError() {
        final TextInputLayout floatingUsernameLabel = findViewById(R.id.invalidPhone);
        Objects.requireNonNull(floatingUsernameLabel.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (!text.equals("") || (text.length() >= 3)) {
                    if (phone.length() == 11) {
                        floatingUsernameLabel.setErrorEnabled(false);
                    }
                } else {
                    floatingUsernameLabel.setError("Invalid Phone");
                    floatingUsernameLabel.setErrorEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
//
//    private void setupFloatingLabelErrorAge() {
//        final TextInputLayout floatingUsernameLabel = findViewById(R.id.inputLayoutAge);
//        Objects.requireNonNull(floatingUsernameLabel.getEditText()).addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence text, int start, int count, int after) {
//                if (text.length() > 0 && text.length() <= 11) {
//                    floatingUsernameLabel.setErrorEnabled(false);
//                } else {
//                    floatingUsernameLabel.setError("Invalid Phone");
//                    floatingUsernameLabel.setErrorEnabled(true);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//
//    }

    private void setup(boolean reset, int count) {
        if (reset)
            bar.resetItems();
        bar.setTabCount(count);
        bar.animateView(3000);
        bar.setCurrentPosition(position <= 0 ? 0 : position);
        System.out.println("COUTNINSETUP " + count);
        switch (count) {
            case 1:
                activity_step_one.setVisibility(View.VISIBLE);
                activity_step_two.setVisibility(View.INVISIBLE);
                activity_step_three.setVisibility(View.INVISIBLE);
                activity_step_four.setVisibility(View.INVISIBLE);
                break;
            case 2:
                activity_step_one.setVisibility(View.INVISIBLE);
                activity_step_two.setVisibility(View.VISIBLE);
                activity_step_three.setVisibility(View.INVISIBLE);
                activity_step_four.setVisibility(View.INVISIBLE);
                break;
            case 3:
                activity_step_one.setVisibility(View.INVISIBLE);
                activity_step_two.setVisibility(View.INVISIBLE);
                activity_step_three.setVisibility(View.VISIBLE);
                activity_step_four.setVisibility(View.INVISIBLE);
                break;
            case 4:
                activity_step_one.setVisibility(View.INVISIBLE);
                activity_step_two.setVisibility(View.INVISIBLE);
                activity_step_three.setVisibility(View.INVISIBLE);
                activity_step_four.setVisibility(View.VISIBLE);
                break;

        }
    }

//    @Override
//    public void onTabClick(int touchPosition, NvTab prev, NvTab nvTab) {
//        switch (touchPosition) {
//            case 1:
//                activity_step_one.setVisibility(View.INVISIBLE);
//                activity_step_two.setVisibility(View.VISIBLE);
//                activity_step_three.setVisibility(View.INVISIBLE);
//                activity_step_four.setVisibility(View.INVISIBLE);
//                break;
//            case 2:
//                activity_step_one.setVisibility(View.INVISIBLE);
//                activity_step_two.setVisibility(View.INVISIBLE);
//                activity_step_three.setVisibility(View.VISIBLE);
//                activity_step_four.setVisibility(View.INVISIBLE);
//                break;
//            case 3:
//                activity_step_one.setVisibility(View.INVISIBLE);
//                activity_step_two.setVisibility(View.INVISIBLE);
//                activity_step_three.setVisibility(View.INVISIBLE);
//                activity_step_four.setVisibility(View.VISIBLE);
//                break;
//            default:
//                activity_step_one.setVisibility(View.VISIBLE);
//                activity_step_two.setVisibility(View.INVISIBLE);
//                activity_step_three.setVisibility(View.INVISIBLE);
//                activity_step_four.setVisibility(View.INVISIBLE);
//                break;
//        }
//
//
//    }


    private boolean validateInput(String surname1, String dateOfBirth, String dateVisits, String clientCode1) {
        if (surname1.isEmpty()) {
            surname.setError("Enter surname");
            FancyToast.makeText(HtsRegistration.this, "Enter surname", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        } else if (dateOfBirth.isEmpty()) {
            dateBirth.setError("Enter date of birth");
            FancyToast.makeText(HtsRegistration.this, "Enter date of birth", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        } else if (dateVisits.isEmpty()) {
            dateVisit.setError("Enter date of visit");
            FancyToast.makeText(HtsRegistration.this, "Enter date of visit", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        } else if (clientCode1.isEmpty()) {
            dateVisit.setError("Enter Client code");
            FancyToast.makeText(HtsRegistration.this, "Enter Client code", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        return true;


    }


    private void settingsSpinnerError(Spinner spinner) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error");
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText("SettingConfig can not be empty"); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.
            FancyToast.makeText(HtsRegistration.this, "SettingConfig can not be empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }

    private void ageUnitSpinnerError(Spinner spinner) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error1");
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText("Age can not be empty"); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void sexSpinnerError(Spinner spinner) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error2");
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText("Sex can not be empty"); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void showAlertAgeBound(long htsIds) {
        LayoutInflater li = LayoutInflater.from((HtsRegistration.this));
        View promptsView = li.inflate(R.layout.pop_up_age, null);
        final AlertDialog dialog = new AlertDialog.Builder(HtsRegistration.this).create();
        dialog.setView(promptsView);
        final AppCompatSpinner ageBound = promptsView.findViewById(R.id.ageBound);
        ageBound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (ageBound.getSelectedItem().toString().equals("=>15")) {
                    showAlert(htsIds);
                } else if (ageBound.getSelectedItem().toString().equals("<15")) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dialog.show();
    }


    private void showAlert(final long htsIds) {
        LayoutInflater li = LayoutInflater.from(HtsRegistration.this);
        View promptsView = li.inflate(R.layout.recency_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(HtsRegistration.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = HtsRegistration.this.getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("htsId", htsIds + "");
                editor.putString("name", name + "");
                editor.putString("clientcode", clientCode + "");
                Hts2 hts2 = new Hts2();
                hts2.setHtsId(htsIds);
                new HtsDAO(getApplicationContext()).updateContact1(hts2, "0");
                editor.commit();
                Intent intent = new Intent(HtsRegistration.this, Home.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HtsRegistration.this, RecencyTesting.class);
                SharedPreferences sharedPreferences = HtsRegistration.this.getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("htsId", htsIds + "");
                editor.putString("name", name + "");
                editor.putString("clientcode", clientCode + "");
                Hts2 hts2 = new Hts2();
                hts2.setHtsId(htsIds);
                new HtsDAO(getApplicationContext()).updateContact1(hts2, "1");
                editor.apply();
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


    private void showAlertLocation() {
        LayoutInflater li = LayoutInflater.from(HtsRegistration.this);
        View promptsView = li.inflate(R.layout.activity_location_popup, null);
        final AlertDialog dialog = new AlertDialog.Builder(HtsRegistration.this).create();
        dialog.setView(promptsView);
        final Button ok = promptsView.findViewById(R.id.ok);
        final AppCompatSpinner grid = promptsView.findViewById(R.id.load);
        List<GetNameId> listName = new GeoLocationDAO(getApplicationContext()).getGeolocationName1("Community");
        final ArrayList geoId = new ArrayList();
        final ArrayList name1 = new ArrayList();
        geoId.add(0L);
        name1.add(0, "");
        for (GetNameId listNam : listName) {
            geoId.add(listNam.getId());
            name1.add(listNam.getName());
        }

        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_items, name1);
        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        districtAdapter.notifyDataSetChanged();
        grid.setAdapter(districtAdapter);
        grid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                long id1 = (long) geoId.get(position);
                String name2 = "";
                String longitude1 = "";
                String latitude1 = "";
                List<Geolocation> geolocationss = new ArrayList<>();
                geolocationss = new GeoLocationDAO(getApplicationContext()).getLongitudeAndLatitude(id1);
                for (Geolocation lat : geolocationss) {
                    longitude1 = String.valueOf(lat.getLongitude());
                    latitude1 = String.valueOf(lat.getLatitude());
                    name2 = lat.getName();
                }
                longitude.setText(longitude1);
                latitude.setText(latitude1);
                phone.setText(name2);
            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
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
                    RelativeLayout view = findViewById(R.id.activity_main);
                    view.setVisibility(View.INVISIBLE);
                    session.saveFragment(1);
                } else {
                    session.clear();
                    Intent intent = new Intent(HtsRegistration.this, Home.class);
                    startActivity(intent);
                }

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public HashMap<String, String> getGetTestingModality() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = HtsRegistration.this.getSharedPreferences("testModalityDB", Context.MODE_PRIVATE);
        user.put("testModality", sharedPreferences.getString("testModality", null));
        user.put("testModality1", sharedPreferences.getString("testModality1", null));
        return user;
    }

}
