package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Lga;
import org.fhi360.lamis.mobile.lite.Domains.ViralLoad;
import org.fhi360.lamis.mobile.lite.Domains.Ward;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class WardDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public WardDAO(Context context) {
        this.context = context;
    }


    public void save(Ward ward) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.id, ward.getId());
            values.put(Constant.wardName, ward.getName());
            values.put(Constant.lgaId, ward.getLgaId());
            db.insert(Constant.TABLE_WARD, null, values);
            db.close();
        } catch (Exception e) {

        }

    }

    public void update(Ward ward) {
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.id, ward.getId());
        values.put(Constant.wardName, ward.getName());
        values.put(Constant.lgaId, ward.getLgaId());
        db.update(Constant.TABLE_WARD, values, "id = ?", new String[]{String.valueOf(ward.getId())});
        db.close();

    }

    public List<Ward> getWardByLgaId(String lgaId) {
        List<Ward> ward1 = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_WARD + " WHERE  lga_id=" + lgaId + " ORDER BY name ASC";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Ward ward = new Ward();
                    ward.setId(cursor.getLong(cursor.getColumnIndex(Constant.id)));
                    ward.setName(cursor.getString(cursor.getColumnIndex(Constant.name)));
                    ward.setLgaId(cursor.getLong(cursor.getColumnIndex(Constant.name)));
                    ward1.add(ward);

                } while ((cursor.moveToNext()));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return ward1;
    }
}
