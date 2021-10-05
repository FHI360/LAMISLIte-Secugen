package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.ANC;
import org.fhi360.lamis.mobile.lite.Domains.Ward;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ANCDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public ANCDAO(Context context) {
        this.context = context;
    }


    public void save(ANC anc) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.id, anc.getId());
            values.put(Constant.htsId, anc.getHtsId());
            values.put(Constant.facilityId, anc.getFacilityId());
            values.put(Constant.facilityName, anc.getFacilityName());
            values.put(Constant.ancNum, anc.getAncNum());
            values.put(Constant.lmp, anc.getLmp());
            values.put(Constant.gestationalAge, anc.getGestationalAge());
            values.put(Constant.gravida, anc.getGravida());
            values.put(Constant.parity, anc.getParity());
            values.put(Constant.syphilisTested, anc.getSyphilisTested());
            values.put(Constant.syphilisTreated, anc.getSyphilisTreated());
            values.put(Constant.sourceReferral, anc.getSourceReferral());
            db.insert(Constant.TABLE_ANC, null, values);
            db.close();
        } catch (Exception ignored) {

        }

    }


    public void update(ANC anc) {
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.id, anc.getId());
        values.put(Constant.htsId, anc.getHtsId());
        values.put(Constant.facilityId, anc.getFacilityId());
        values.put(Constant.facilityName, anc.getFacilityName());
        values.put(Constant.ancNum, anc.getAncNum());
        values.put(Constant.lmp, anc.getLmp());
        values.put(Constant.gestationalAge, anc.getGestationalAge());
        values.put(Constant.gravida, anc.getGravida());
        values.put(Constant.parity, anc.getParity());
        values.put(Constant.syphilisTested, anc.getSyphilisTested());
        values.put(Constant.syphilisTreated, anc.getSyphilisTreated());
        values.put(Constant.sourceReferral, anc.getSourceReferral());
        db.update(Constant.TABLE_ANC, values, "id = ?", new String[]{String.valueOf(anc.getId())});
        db.close();

    }
}



