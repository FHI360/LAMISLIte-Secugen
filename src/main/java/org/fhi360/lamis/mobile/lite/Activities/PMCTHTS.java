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
import org.fhi360.lamis.mobile.lite.Domains.Geolocation;
import org.fhi360.lamis.mobile.lite.Domains.GetNameId;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.Hts2;
import org.fhi360.lamis.mobile.lite.Fragments.RiskAssessmentFragment;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.GetCurrentLocation;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.text.SimpleDateFormat;
import java.util.*;

public class PMCTHTS extends AppCompatActivity {
    private EditText hospitalNumber,
            ancNumber, age, dateVisit,
            date_tested_positive;
    private AppCompatSpinner accepted_hiv_test_result, previously_known_hiv_positive, testingSetting, gender,
            syphilisTestResult, hepatitiscTestResult, hepatitisbTestResult,
            HIV_HBV_co_infected, HepatitisC_Test_result, HepatitisB_test_result, HIV_re_testing,
            ageUnit, received_hiv_test_result, tested_for_HepatitisB, Tested_for_HepatitisC, hiv_test_result,
            HIVHBV_Co_infected1, agree_to_partner_notification;

    private LinearLayout communitytesting2;
    private Calendar myCalendar = Calendar.getInstance();
    private Button finishButton;
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
    private TextView communitytesting, clientDetails, stiScreening1F, stiScreening1F1;
    private HashMap<String, String> coordinate;
    private TextView clientPregnant;
    private String pin;
    private String deviceconfigId, camCode1;
    private String assessmentId = "";
    GetCurrentLocation currentLoc;
    private LocationManager locationManager;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pmtct_hts_anc);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        currentLoc = new GetCurrentLocation(this);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        session = new PrefManager(this);
        dateVisit = findViewById(R.id.dateVisit);
        gender = findViewById(R.id.gender);
        hospitalNumber = findViewById(R.id.hospitalNum);
        ancNumber = findViewById(R.id.ancNumber);
        previously_known_hiv_positive = findViewById(R.id.previously_known_hiv_positive);
        date_tested_positive = findViewById(R.id.date_tested_positive);
        hiv_test_result = findViewById(R.id.hiv_test_result);
        accepted_hiv_test_result = findViewById(R.id.accepted_hiv_test_result);
        received_hiv_test_result = findViewById(R.id.received_hiv_test_result);
        HIV_re_testing = findViewById(R.id.hivretestin_testing);
        tested_for_HepatitisB = findViewById(R.id.tested_for_HepatitisB);
        HepatitisB_test_result = findViewById(R.id.HepatitisB_test_result);
        Tested_for_HepatitisC = findViewById(R.id.Tested_for_HepatitisC);
        HepatitisC_Test_result = findViewById(R.id.HepatitisC_Test_result);
        HIV_HBV_co_infected = findViewById(R.id.hiv_co_infected);
        HIVHBV_Co_infected1 = findViewById(R.id.HIVHBV_Co_infected1);
        agree_to_partner_notification = findViewById(R.id.agree_to_partner_notification);

        HashMap<String, String> user = session.getANC();
        dateVisit.setText(user.get("dateVisit"));
        hospitalNumber.setText(user.get("hospitalNum"));
        String anc = user.get("ancNo");
        if (anc != null) {
            ancNumber.setText(anc);
        } else {
            ancNumber.setVisibility(View.GONE);
        }
        age = findViewById(R.id.age);
        age.setText(user.get("age"));
        syphilisTestResult = findViewById(R.id.syphilisTestResult);
        hepatitisbTestResult = findViewById(R.id.hepatitisbTestResult);
        hepatitiscTestResult = findViewById(R.id.hepatitiscTestResult);
        finishButton = findViewById(R.id.save);

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
                final DatePickerDialog mDatePicker = new DatePickerDialog(PMCTHTS.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }

        });


        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };

        date_tested_positive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(PMCTHTS.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }

        });
        tested_for_HepatitisB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tested_for_HepatitisB1 = tested_for_HepatitisB.getSelectedItem().toString();
                if(tested_for_HepatitisB1.equalsIgnoreCase("NO")){
                    HepatitisB_test_result.setEnabled(false);
                }else if(tested_for_HepatitisB1.equalsIgnoreCase("YES")){
                    HepatitisB_test_result.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Tested_for_HepatitisC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Tested_for_HepatitisC1 = Tested_for_HepatitisC.getSelectedItem().toString();
                if(Tested_for_HepatitisC1.equalsIgnoreCase("NO")){
                    HepatitisC_Test_result.setEnabled(false);
                }else if(Tested_for_HepatitisC1.equalsIgnoreCase("YES")){
                    HepatitisC_Test_result.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.get("surname");
                String otherName = user.get("otherName");
                String referredFrom = user.get("referredFrom");
                String phoneNumber = user.get("phoneNumber");
                String address = user.get("address");
                HashMap<String, String> user = session.getHtsDetails();
                String facilityName = user.get("faciltyName");
                String lgaId = user.get("lgaId");
                String steteId = user.get("stateId");
                String facilityId = user.get("facilityId");
                Hts hts = new Hts();
                hts.setFacilityName(facilityName);
                hts.setFacilityId(Long.valueOf(facilityId));
                hts.setDateVisit(dateVisit.getText().toString());
                hts.setHospitalNum(hospitalNumber.getText().toString());
                hts.setSurname(username);
                hts.setOtherNames(otherName);
                hts.setAge(Integer.valueOf(age.getText().toString()));
                hts.setReferredFrom(referredFrom);
                hts.setPhone(phoneNumber);
                hts.setAddress(address);
                hts.setLgaId(Long.valueOf(lgaId));
                hts.setStateId(Long.valueOf(steteId));
                if (previously_known_hiv_positive.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment1(1);
                } else if (previously_known_hiv_positive.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment1(0);
                }
                if (accepted_hiv_test_result.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment2(1);
                } else if (accepted_hiv_test_result.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment2(0);
                }
                if (received_hiv_test_result.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment3(1);
                } else if (received_hiv_test_result.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment3(0);
                }
                if (HIV_re_testing.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment4(1);
                } else if (HIV_re_testing.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment4(0);
                }
                if (tested_for_HepatitisB.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment5(1);
                } else if (tested_for_HepatitisB.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment5(0);
                }
                if (HIV_HBV_co_infected.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment6(1);
                } else if (HIV_HBV_co_infected.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment6(0);
                }
                if (HIVHBV_Co_infected1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment7(1);
                } else if (HIVHBV_Co_infected1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment7(0);
                }

                hts.setHepatitisbTestResult(HepatitisB_test_result.getSelectedItem().toString());
                hts.setHepatitiscTestResult(HepatitisC_Test_result.getSelectedItem().toString());
                System.out.println("HIV TEST result " + hiv_test_result.getSelectedItem().toString());
                hts.setHivTestResult(hiv_test_result.getSelectedItem().toString());
                hts.setPartnerNotification(agree_to_partner_notification.getSelectedItem().toString());
                hts.setTestedHiv(HIV_re_testing.getSelectedItem().toString());
                hts.setDateStarted(date_tested_positive.getText().toString());
                new HtsDAO(getApplicationContext()).addTestResult(hts);
                FancyToast.makeText(PMCTHTS.this, "Test Result saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                session.clearANC();
                Intent intent = new Intent(PMCTHTS.this, Home.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
//        coordinate = session.getCoordnitae();
//        if (coordinate.get("longitude") != null && coordinate.get("latitude") != "") {
//            NotificationService notificationService = new NotificationService(getApplicationContext());
//            notificationService.createNotification("LAMISLite", "Your device location is captured");
//        } else {
//            NotificationService notificationService = new NotificationService(getApplicationContext());
//            notificationService.createNotification("LAMISLite", "Enable your GPS Location");
//
//        }
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateVisit.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_tested_positive.setText(sdf.format(myCalendar.getTime()));

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
//                    if (phone.length() == 11) {
//                        floatingUsernameLabel.setErrorEnabled(false);
//                    }
                } else {
                    floatingUsernameLabel.setError("Invalid Phone");
                    floatingUsernameLabel.setErrorEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private boolean validateInput(String surname1, String dateOfBirth, String dateVisits) {
        if (surname1.isEmpty()) {
            // surname.setError("Enter surname");
            FancyToast.makeText(PMCTHTS.this, "Enter surname", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        } else if (dateOfBirth.isEmpty()) {
            //   dateBirth.setError("Enter date of birth");
            FancyToast.makeText(PMCTHTS.this, "Enter date of birth", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        } else if (dateVisits.isEmpty()) {
            dateVisit.setError("Enter date of visit");
            FancyToast.makeText(PMCTHTS.this, "Enter date of visit", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
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
            FancyToast.makeText(PMCTHTS.this, "SettingConfig can not be empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
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

    private void showAlert(final long htsIds) {
        LayoutInflater li = LayoutInflater.from(PMCTHTS.this);
        View promptsView = li.inflate(R.layout.recency_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(PMCTHTS.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PMCTHTS.this.getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("htsId", htsIds + "");
                editor.putString("name", name + "");
                // editor.putString("clientcode", clientCode + "");
                Hts2 hts2 = new Hts2();
                hts2.setHtsId(htsIds);
                new HtsDAO(getApplicationContext()).updateContact1(hts2, "0");
                editor.commit();
                Intent intent = new Intent(PMCTHTS.this, Home.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PMCTHTS.this, RecencyTesting.class);
                SharedPreferences sharedPreferences = PMCTHTS.this.getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("htsId", htsIds + "");
                editor.putString("name", name + "");
                // editor.putString("clientcode", clientCode + "");
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
        LayoutInflater li = LayoutInflater.from(PMCTHTS.this);
        View promptsView = li.inflate(R.layout.activity_location_popup, null);
        final AlertDialog dialog = new AlertDialog.Builder(PMCTHTS.this).create();
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
                //  longitude.setText(longitude1);
                // latitude.setText(latitude1);
                //  phone.setText(name2);
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
                    Intent intent = new Intent(PMCTHTS.this, Home.class);
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
