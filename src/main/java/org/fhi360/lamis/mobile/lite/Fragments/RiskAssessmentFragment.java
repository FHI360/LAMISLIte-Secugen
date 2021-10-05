package org.fhi360.lamis.mobile.lite.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import org.fhi360.lamis.mobile.lite.Activities.ANCActivity;
import org.fhi360.lamis.mobile.lite.Activities.HtsRegistration;
import org.fhi360.lamis.mobile.lite.Activities.PMCTHTS;
import org.fhi360.lamis.mobile.lite.Activities.RiskPage;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.fhi360.lamis.mobile.lite.Utils.Constant.PREFERENCES_ENCOUNTER;


public class RiskAssessmentFragment extends Fragment {
    private AppCompatSpinner question1, pict;
    private RadioButton cli1, cli2,
            cli3, cli4;

    private String facilityName;
    private String lgaId;
    private String steteId;
    private String facilityId;
    private PrefManager session;
    private String deviceconfigId;
    private String auoIncrementClientCode;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_rik_assessment, container, false);
        try {
            clearPreferences();
            session = new PrefManager(getContext());
            question1 = rootView.findViewById(R.id.question1);
            pict = rootView.findViewById(R.id.pict);
            cli1 = rootView.findViewById(R.id.cli1);
            cli2 = rootView.findViewById(R.id.cli2);
            cli3 = rootView.findViewById(R.id.cli3);
            cli4 = rootView.findViewById(R.id.cli4);
            pict.setVisibility(View.INVISIBLE);
            cli1.setVisibility(View.INVISIBLE);
            cli2.setVisibility(View.INVISIBLE);
            cli3.setVisibility(View.INVISIBLE);
            cli4.setVisibility(View.INVISIBLE);
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
            session.setClientCode(auoIncrementClientCode);

            question1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (question1.getSelectedItem().toString().equals("Clinical Platforms")) {
                        cli1.setVisibility(View.VISIBLE);
                        cli2.setVisibility(View.VISIBLE);
                        cli3.setVisibility(View.VISIBLE);
                        cli4.setVisibility(View.VISIBLE);
                        pict.setVisibility(View.INVISIBLE);
                        cli1.setText("PMVs/Chemists/dispensary/Community Pharmacy");
                        cli2.setText("PHCs/Clinics");
                        cli3.setText("Labs");
                        cli4.setText("Others");
                        cli1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                cli1.setChecked(true);
                                cli2.setChecked(false);
                                cli3.setChecked(false);
                                cli4.setChecked(false);

                                saveTestingModality(question1.getSelectedItem().toString(), cli1.getText().toString());
                                showAlert();
                            }
                        });

                        cli2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cli1.setChecked(false);
                                cli2.setChecked(true);
                                cli3.setChecked(false);
                                cli4.setChecked(false);
                                saveTestingModality(question1.getSelectedItem().toString(), cli2.getText().toString());
                                showAlert();
                            }
                        });
                        cli3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cli1.setChecked(false);
                                cli2.setChecked(false);
                                cli3.setChecked(true);
                                cli4.setChecked(false);
                                saveTestingModality(question1.getSelectedItem().toString(), cli3.getText().toString());
                                showAlert();
                            }
                        });
                        cli4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cli1.setChecked(false);
                                cli2.setChecked(false);
                                cli3.setChecked(false);
                                cli4.setChecked(true);
                                saveTestingModality(question1.getSelectedItem().toString(), cli4.getText().toString());
                                showAlert();
                            }
                        });

                    }
                    if (question1.getSelectedItem().toString().equals("PMTCT")) {
                        pict.setVisibility(View.INVISIBLE);
                        cli1.setVisibility(View.VISIBLE);
                        cli2.setVisibility(View.VISIBLE);
                        cli3.setVisibility(View.VISIBLE);
                        cli1.setText("ANC");
                        cli2.setText("Labour/Delivery");
                        cli3.setText("Post partum");

                        cli1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pict.setVisibility(View.VISIBLE);
                                cli1.setChecked(true);
                                cli2.setChecked(false);
                                cli3.setChecked(false);
                                session.clearANC();
                                ArrayList arrayListWarName = new ArrayList();
                                arrayListWarName.add(" ");
                                arrayListWarName.add("1st Visit");
                                arrayListWarName.add("Follow up Visit");
                                final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                        R.layout.spinner_items, arrayListWarName);
                                districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                                districtAdapter.notifyDataSetChanged();
                                pict.setAdapter(districtAdapter);
                            }
                        });
                        cli2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cli1.setChecked(false);
                                cli2.setChecked(true);
                                cli3.setChecked(false);
                                session.clearANC();
                                Intent intent = new Intent(getContext(), PMCTHTS.class);
                                startActivity(intent);
                            }
                        });
                        cli3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cli1.setChecked(false);
                                cli2.setChecked(false);
                                cli3.setChecked(true);

                                Intent intent = new Intent(getContext(), PMCTHTS.class);
                                startActivity(intent);
                            }
                        });


                        pict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (pict.getSelectedItem().equals("1st Visit")) {
                                    Intent intent = new Intent(getContext(), ANCActivity.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("Follow up Visit")) {
                                    Intent intent = new Intent(getContext(), PMCTHTS.class);
                                    startActivity(intent);
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        // FancyToast.makeText(getContext(), "Redirect to  PMTCT_HTS page", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                    }

                    if (question1.getSelectedItem().toString().equals("PITC")) {
                        cli1.setVisibility(View.INVISIBLE);
                        cli2.setVisibility(View.INVISIBLE);
                        cli3.setVisibility(View.INVISIBLE);
                        cli4.setVisibility(View.INVISIBLE);
                        pict.setVisibility(View.VISIBLE);
                        ArrayList arrayListWarName = new ArrayList();
                        arrayListWarName.add(" ");
                        arrayListWarName.add("TB Site");
                        arrayListWarName.add("STI Clinic");
                        arrayListWarName.add("In patient");
                        arrayListWarName.add("Blood bank");
                        arrayListWarName.add("Family planning");
                        arrayListWarName.add("Eye clinic");
                        arrayListWarName.add("Dental Clinic");
                        arrayListWarName.add("Dermatology");
                        arrayListWarName.add("Malnutrition");
                        arrayListWarName.add("Pediatrics ward");
                        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                R.layout.spinner_items, arrayListWarName);
                        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                        districtAdapter.notifyDataSetChanged();
                        pict.setAdapter(districtAdapter);

                        pict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (pict.getSelectedItem().equals("TB Site")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("STI Clinic")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("In patient")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("Family planning")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("Eye clinic")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("Dental Clinic")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("Dermatology")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("Malnutrition")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }

                                if (pict.getSelectedItem().equals("Pediatrics ward")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("Blood bank")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                    if (question1.getSelectedItem().toString().equals("VCT")) {
                        cli1.setVisibility(View.INVISIBLE);
                        cli2.setVisibility(View.INVISIBLE);
                        cli3.setVisibility(View.INVISIBLE);
                        cli4.setVisibility(View.INVISIBLE);
                        pict.setVisibility(View.INVISIBLE);

                        cli1.setChecked(false);
                        cli2.setChecked(false);
                        cli3.setChecked(false);
                        cli4.setChecked(false);

                        saveTestingModality(question1.getSelectedItem().toString(), "");
                        showAlert();
                    }
                    if (question1.getSelectedItem().toString().equals("INDEX")) {
                        cli1.setVisibility(View.INVISIBLE);
                        cli2.setVisibility(View.INVISIBLE);
                        cli3.setVisibility(View.INVISIBLE);
                        cli4.setVisibility(View.INVISIBLE);
                        pict.setVisibility(View.VISIBLE);
                        ArrayList arrayListWarName = new ArrayList();
                        arrayListWarName.add(" ");
                        arrayListWarName.add("Community");
                        arrayListWarName.add("Facility");
                        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                R.layout.spinner_items, arrayListWarName);
                        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                        districtAdapter.notifyDataSetChanged();
                        pict.setAdapter(districtAdapter);

                        pict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (pict.getSelectedItem().equals("Community")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }
                                if (pict.getSelectedItem().equals("Facility")) {
                                    saveTestingModality(question1.getSelectedItem().toString(), pict.getSelectedItem().toString());
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    startActivity(intent);
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    if (question1.getSelectedItem().toString().equals("Outreach/Mobile")) {
//                        Intent intent = new Intent(getContext(), RiskPage.class);
//                        startActivity(intent);
                        saveTestingModality(question1.getSelectedItem().toString(), "");
                        showAlert();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        } catch (
                Exception ignored) {

        }


        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        cli1.setChecked(false);
        cli2.setChecked(false);
        cli3.setChecked(false);
        cli4.setChecked(false);
    }


    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.pop_up_age, null);
        final AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
        dialog.setView(promptsView);
        final AppCompatSpinner ageBound = promptsView.findViewById(R.id.ageBound);
        ageBound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (ageBound.getSelectedItem().toString().equals("=>15")) {
                    Intent intent = new Intent(getContext(), RiskPage.class);
                    startActivity(intent);
                } else if (ageBound.getSelectedItem().toString().equals("<15")) {
                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dialog.show();
    }

    public void saveTestingModality(String testModality, String testModality1) {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("testModalityDB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("testModality", testModality);
        editor.putString("testModality1", testModality1);
        editor.apply();
    }






    private void clearPreferences() {
        this.preferences = Objects.requireNonNull(getContext()).getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
    }



}
