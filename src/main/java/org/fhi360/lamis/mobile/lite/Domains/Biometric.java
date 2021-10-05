package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;
import org.fhi360.lamis.mobile.lite.pbs.FingerPositions;

@Data
public class Biometric {
    private int id;
    private String biometricType;
    private byte[] template;
    private FingerPositions templateType;
    private String enrollmentDate;
    private Patient3 patient;
    private Facility3 facility;
    private String uuid;
    private String buuid;
}
