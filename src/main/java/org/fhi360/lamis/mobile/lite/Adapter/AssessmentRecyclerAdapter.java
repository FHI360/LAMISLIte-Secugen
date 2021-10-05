package org.fhi360.lamis.mobile.lite.Adapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import org.fhi360.lamis.mobile.lite.Activities.RiskAllPage;
import org.fhi360.lamis.mobile.lite.Activities.RiskPage;
import org.fhi360.lamis.mobile.lite.Activities.RistPage2;
import org.fhi360.lamis.mobile.lite.DAO.FacilityDAO;
import org.fhi360.lamis.mobile.lite.Domains.AssessmentReturn;
import org.fhi360.lamis.mobile.lite.Domains.Facility;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.fhi360.lamis.mobile.lite.Utils.Constant.PREFERENCES_ENCOUNTER;

/**
 * Created by idris on 10/10/2016.
 */
public class AssessmentRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AssessmentReturn> assessmentList;
    private Context context;
    private PrefManager prefManager;
    private String clientName;
    private SharedPreferences preferences;

    public AssessmentRecyclerAdapter(List<AssessmentReturn> assessmentList, Context context) {
        this.assessmentList = assessmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items1, parent, false);
        prefManager = new PrefManager(context);
        return new AssessmentRecyclerAdapter.AssessmentViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AssessmentRecyclerAdapter.AssessmentViewHolder) {
            ((AssessmentRecyclerAdapter.AssessmentViewHolder) holder).populateAssesment(assessmentList.get(position));
        }

    }


    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return assessmentList.size();
    }


    public class AssessmentViewHolder extends RecyclerView.ViewHolder {

        TextView facilitiesName;
        TextView textViewClientName, dateText;
        LinearLayout relativeLayout;
        TextView circleImage;

        public AssessmentViewHolder(View view) {
            super(view);
            textViewClientName = view.findViewById(R.id.hts_profile);
            facilitiesName = view.findViewById(R.id.facility_name);
            relativeLayout = view.findViewById(R.id.relarive_container);
            circleImage = itemView.findViewById(R.id.circleImage);
            dateText = view.findViewById(R.id.dateText);

        }

        public void populateAssesment(final AssessmentReturn assessment) {
            try {
                String clientCode = assessment.getClientCode();

                textViewClientName.setText(Html.fromHtml(clientCode), TextView.BufferType.SPANNABLE);
                Facility facility = new FacilityDAO(context).getFacilityById(assessment.getFacility().getId());
                facilitiesName.setText(facility.getName());
                dateText.setText(assessment.getDateVisit());
                String firstLetter = String.valueOf(clientCode.charAt(0));
                Random mRandom = new Random();
                int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
                ((GradientDrawable) circleImage.getBackground()).setColor(color);
                circleImage.setText(firstLetter);

                relativeLayout.setOnClickListener(view -> {
                    Intent intent = new Intent(context, RistPage2.class);
                    savePreferences(assessment);
                    context.startActivity(intent);
                });

            } catch (Exception ignored) {

            }
        }
    }

    private void savePreferences(AssessmentReturn assessment) {
        preferences = Objects.requireNonNull(context).getSharedPreferences(PREFERENCES_ENCOUNTER, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("assessmentReturn", new Gson().toJson(assessment));
        editor.putBoolean("edit_mode", false);
        editor.apply();
    }

}
