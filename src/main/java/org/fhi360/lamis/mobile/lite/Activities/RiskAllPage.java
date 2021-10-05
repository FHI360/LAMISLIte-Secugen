package org.fhi360.lamis.mobile.lite.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.mlsdev.animatedrv.AnimatedRecyclerView;
import org.fhi360.lamis.mobile.lite.Adapter.AssessmentRecyclerAdapter;
import org.fhi360.lamis.mobile.lite.DAO.AssementDAO;
import org.fhi360.lamis.mobile.lite.Domains.AssessmentReturn;
import org.fhi360.lamis.mobile.lite.R;

import java.util.ArrayList;
import java.util.List;

public class RiskAllPage extends AppCompatActivity {
    private RecyclerView recyclerViewHts;
    private List<AssessmentReturn> listPatients;
    private AssessmentRecyclerAdapter assessmentRecyclerAdapter;
    private AutoCompleteTextView mSearchField;


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_patient_recycler);
        mSearchField = findViewById(R.id.search_field);
        recyclerViewHts = findViewById(R.id.patient_recycler);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });

        listPatients = new ArrayList<>();
        listPatients = new AssementDAO(RiskAllPage.this).getAssessment();
        assessmentRecyclerAdapter = new AssessmentRecyclerAdapter(listPatients, RiskAllPage.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RiskAllPage.this);
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(assessmentRecyclerAdapter);
        assessmentRecyclerAdapter.notifyDataSetChanged();
        recyclerViewHts.scheduleLayoutAnimation();


        ArrayList names = new ArrayList();
        for (AssessmentReturn patient : listPatients) {
            names.add(patient.getClientCode());
        }

        final ArrayAdapter districtAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_items, names);
        mSearchField.setThreshold(2);
        mSearchField.setAdapter(districtAdapter);

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    search(mSearchField.getText().toString());
                } else {
                    search(mSearchField.getText().toString());
                }

            }
        });

    }

    private void search(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            resetSearch();
            return;
        }

        List<AssessmentReturn> filteredValues = new ArrayList<>(listPatients);
        for (AssessmentReturn value : listPatients) {
            if (!value.getClientCode().toLowerCase().contains(searchText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        List<AssessmentReturn> filteredValues1 = new ArrayList<>(listPatients);
        for (AssessmentReturn value : listPatients) {
            if (!value.getClientCode().toLowerCase().contains(searchText.toLowerCase())) {
                filteredValues1.remove(value);
            }
        }
        assessmentRecyclerAdapter = new AssessmentRecyclerAdapter(filteredValues, RiskAllPage.this);
        recyclerViewHts.setAdapter(assessmentRecyclerAdapter);
    }

    public void resetSearch() {
        assessmentRecyclerAdapter = new AssessmentRecyclerAdapter(listPatients, RiskAllPage.this);
        recyclerViewHts.setAdapter(assessmentRecyclerAdapter);

    }


}

