package org.fhi360.lamis.mobile.lite.pbs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

import java.io.Serializable;
@Data
public class PatientBiometricContract implements Serializable {

    @SerializedName("manufacturer")
    @Expose
    private String Manufacturer;

    @SerializedName("model")
    @Expose
    private String Model;

    @SerializedName("serialNumber")
    @Expose
    private String SerialNumber;

    @SerializedName("imageWidth")
    @Expose
    private int ImageWidth;

    @SerializedName("imageHeight")
    @Expose
    private int ImageHeight;

    @SerializedName("imageDPI")
    @Expose
    private int ImageDPI;

    @SerializedName("imageQuality")
    @Expose
    private int ImageQuality;

    @SerializedName("image")
    @Expose
    private String Image;

    @SerializedName("imageByte")
    @Expose
    private byte[] ImageByte;

    @SerializedName("template")
    @Expose
    private String Template;

    @SerializedName("fingerPositions")
    @Expose
    private FingerPositions FingerPositions;

    @SerializedName("patienId")
    @Expose
    private int PatienId;

    @SerializedName("biometricInfo_Id")
    @Expose
    public String BiometricInfo_Id;

    @SerializedName("creator")
    @Expose
    private Integer Creator;

    @SerializedName("errorMessage")
    @Expose
    private String ErrorMessage;

    @SerializedName("syncStatus")
    @Expose
    private int SyncStatus;


}



