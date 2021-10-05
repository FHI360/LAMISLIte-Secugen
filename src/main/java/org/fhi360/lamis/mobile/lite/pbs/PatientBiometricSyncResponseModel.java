package org.fhi360.lamis.mobile.lite.pbs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class PatientBiometricSyncResponseModel {
    @SerializedName("IsSuccessful")
    @Expose
    private boolean IsSuccessful;

    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;

}
