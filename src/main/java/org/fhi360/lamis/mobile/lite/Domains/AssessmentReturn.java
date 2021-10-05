package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class AssessmentReturn {
    private Long id;
    private String dateVisit;
    private String clientCode;
    private Facility facility;
    private Integer question1;
    private String dateLastTestDone;
    private String question2;
    private Integer question3;
    private Integer question4;
    private Integer question5;
    private Integer question6;
    private Integer question7;
    private Integer question8;
    private Integer question9;
    private Integer question10;
    private Integer question11;
    private Long deviceconfigId;
    private String timeStamp;
    private int gbv_1;
    private int gbv_2;

}
