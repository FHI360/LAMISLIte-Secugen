package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;
import org.fhi360.lamis.mobile.lite.Utils.Constant;
@Data
public class ANC {
    private Long id;
    private Long htsId;
    private Long facilityId;
    private String facilityName;
    private String dateVisit;
    private String hospitalNum;
    private String ancNum;
    private String name;
    private Integer age;
    private String address;
    private String phone;
    private String lmp;
    private Integer gestationalAge;
    private Integer gravida;
    private Integer parity;
    private String sourceReferral;
    private Integer syphilisTested;
    private String syphilisTestResult;
    private Integer syphilisTreated;
    private Integer referredFrom;

}
