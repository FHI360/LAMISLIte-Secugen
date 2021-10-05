package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Biometric;
import org.fhi360.lamis.mobile.lite.Domains.Facility;
import org.fhi360.lamis.mobile.lite.Domains.Facility3;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Domains.Patient2;
import org.fhi360.lamis.mobile.lite.Domains.Patient3;
import org.fhi360.lamis.mobile.lite.Domains.ViralLoad;
import org.fhi360.lamis.mobile.lite.Utils.Constant;
import org.fhi360.lamis.mobile.lite.pbs.FingerPositions;

import java.util.ArrayList;
import java.util.List;

public class BiometricDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public BiometricDAO(Context context) {
        this.context = context;
    }
/*
  + Column.biometricInfo_Id + Column.Type.TEXT_TYPE_WITH_COMMA
                + Column.patient_id + Column.Type.INT_TYPE_WITH_COMMA
                + Column.template + Column.Type.TEXT_TYPE_WITH_COMMA
                + Column.imageWidth + Column.Type.INT_TYPE_WITH_COMMA
                + Column.imageHeight + Column.Type.INT_TYPE_WITH_COMMA
                + Column.imageDPI + Column.Type.INT_TYPE_WITH_COMMA
                + Column.imageQuality + Column.Type.INT_TYPE_WITH_COMMA
                + Column.fingerPosition + Column.Type.TEXT_TYPE_WITH_COMMA
                + Column.serialNumber + Column.Type.TEXT_TYPE_WITH_COMMA
                + Column.model + Column.Type.TEXT_TYPE_WITH_COMMA
                + Column.manufacturer + Column.Type.TEXT_TYPE_WITH_COMMA
                + Column.SyncStatus + Column.Type.INT_TYPE_WITH_COMMA
                + Column.creator + Column.Type.INT_TYPE
                + ");"
 */

    public long save(Biometric biometric) {
        long id = 0L;
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.facilityId, biometric.getFacility().getId());
            values.put(Constant.biometricType, biometric.getBiometricType());
            values.put(Constant.template, biometric.getTemplate());
            values.put(Constant.patientId, biometric.getPatient().getId());
            values.put(Constant.templateType, biometric.getTemplateType().toString());
            values.put(Constant.enrollmentDate, biometric.getEnrollmentDate());
            values.put(Constant.uuid, biometric.getUuid());
            values.put(Constant.buuid, biometric.getBuuid());
            id = db.insert(Constant.TABLE_BIOMETRIC, null, values);
            db.close();
        } catch (Exception ignored) {

        }
        return id;
    }

    public void update(Biometric biometric) {
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.facilityId, biometric.getFacility().getId());
        values.put(Constant.biometricType, biometric.getBiometricType());
        values.put(Constant.template, biometric.getTemplate());
        values.put(Constant.patientId, biometric.getPatient().getId());
        values.put(Constant.templateType, biometric.getTemplateType().toString());
        values.put(Constant.enrollmentDate, biometric.getEnrollmentDate());
        db.update(Constant.TABLE_BIOMETRIC, values, "id = ?", new String[]{String.valueOf(biometric.getId())});
        db.close();

    }

    public List<Biometric> getBiometric() {
        List<Biometric> loadArrayList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_BIOMETRIC;// + " where buuid = '34ae010f-53fd-4021-9715-4cbf43e88c06'";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Biometric biometric = new Biometric();
                    Facility3 facility = new Facility3();
                    facility.setId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    biometric.setFacility(facility);
                    biometric.setTemplate(cursor.getBlob(cursor.getColumnIndex(Constant.template)));
                    biometric.setUuid(cursor.getString(cursor.getColumnIndex(Constant.uuid)));
                    biometric.setBuuid(cursor.getString(cursor.getColumnIndex(Constant.buuid)));
                    Patient3 patient3 = new Patient3();
                    patient3.setId(cursor.getLong(cursor.getColumnIndex(Constant.patientId)));
                    biometric.setPatient(patient3);
                    System.out.println("TamplateType database" + cursor.getString(cursor.getColumnIndex(Constant.templateType)));
                    biometric.setTemplateType(Enum.valueOf(FingerPositions.class,cursor.getString(cursor.getColumnIndex(Constant.templateType))));
                    biometric.setBiometricType(cursor.getString(cursor.getColumnIndex(Constant.biometricType)));
                    biometric.setEnrollmentDate(cursor.getString(cursor.getColumnIndex(Constant.enrollmentDate)));

                    loadArrayList.add(biometric);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {
            // System.out.println("EREREE " + e.getMessage());

        }
        return loadArrayList;
    }


    public int count(Long patientId) {
        int count = 0;
        try {
            String query = "Select * FROM " + Constant.TABLE_BIOMETRIC + " WHERE " + Constant.patientId + " = " + patientId;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            count = cursor.getCount();
            cursor.close();
        } catch (Exception ignored) {

        }
        return count;
    }


    public List<Biometric> getBiometric(Long patientId) {
        List<Biometric> loadArrayList = new ArrayList<>();
        try {
            String query = "Select * FROM " + Constant.TABLE_BIOMETRIC + " WHERE " + Constant.patientId + " = " + patientId;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    org.fhi360.lamis.mobile.lite.Domains.Biometric biometric = new org.fhi360.lamis.mobile.lite.Domains.Biometric();
                    Facility3 facility = new Facility3();
                    facility.setId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    biometric.setFacility(facility);
                    biometric.setTemplate(cursor.getBlob(cursor.getColumnIndex(Constant.template)));
                    biometric.setUuid(cursor.getString(cursor.getColumnIndex(Constant.uuid)));
                    biometric.setBuuid(cursor.getString(cursor.getColumnIndex(Constant.buuid)));
                    Patient3 patient3 = new Patient3();
                    patient3.setId(cursor.getLong(cursor.getColumnIndex(Constant.patientId)));
                    biometric.setPatient(patient3);
                    System.out.println("TamplateType database" + cursor.getString(cursor.getColumnIndex(Constant.templateType)));
                    biometric.setTemplateType(Enum.valueOf(FingerPositions.class,cursor.getString(cursor.getColumnIndex(Constant.templateType))));
                    biometric.setBiometricType(cursor.getString(cursor.getColumnIndex(Constant.biometricType)));
                    biometric.setEnrollmentDate(cursor.getString(cursor.getColumnIndex(Constant.enrollmentDate)));

                    loadArrayList.add(biometric);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {
            // System.out.println("EREREE " + e.getMessage());

        }
        return loadArrayList;
    }
}

