package org.fhi360.lamis.mobile.lite.Domains;

import java.util.List;
import java.util.Set;
@lombok.Data
public class Data {
    private Set<Facility> facilities;
    private Set<State> states;
    private Set<Lga> lgas;
    private Set<Regimens> regimens;
    private Set<CamTeam> camTeams;
   private Set<Ward> wards;

}
