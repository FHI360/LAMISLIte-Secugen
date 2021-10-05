package org.fhi360.lamis.mobile.lite.pbs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.fhi360.lamis.mobile.lite.Domains.Biometric;

import java.util.ArrayList;
import java.util.List;
@Data
public class PatientBiometricDTO {

    @SerializedName("patientUUID")
    @Expose
    private String PatientUUID;

    @SerializedName("fingerPrintList")
    @Expose
    private List<Biometric> FingerPrintList;

}

