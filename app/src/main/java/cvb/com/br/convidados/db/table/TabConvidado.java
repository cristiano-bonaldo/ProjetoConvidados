package cvb.com.br.convidados.db.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cvb.com.br.convidados.dao.DAOUtil;
import cvb.com.br.convidados.model.Convidado;

public class TabConvidado {

    public static String C_TABLE_NAME = "CONVIDADOS";

    public static String C_FIELD_ID     = "ID";
    public static String C_FIELD_NAME   = "NAME";
    public static String C_FIELD_STATUS = "STATUS";

    public static String C_SCRIPT_V1 =
            "CREATE TABLE " + C_TABLE_NAME + "(" +
            C_FIELD_ID     + " LONG PRIMARY KEY AUTOINCREMENT, " +
            C_FIELD_NAME   + " TEXT(200), " +
            C_FIELD_STATUS + " INTEGER(1) " +
            ")";

    private SQLiteDatabase db;

    public TabConvidado(Context ctx) {
        db = DAOUtil.getInstance(ctx).getDB();
    }

    public long inserir(Convidado convidado) {
        return db.insert(C_TABLE_NAME, null, getContentValues(convidado));
    }

    private ContentValues getContentValues(Convidado convidado) {
        ContentValues cv = new ContentValues();
        cv.put(TabConvidado.C_FIELD_ID, convidado.getId());
        cv.put(TabConvidado.C_FIELD_NAME, convidado.getName());
        cv.put(TabConvidado.C_FIELD_STATUS, convidado.getStatus());

        return cv;
    }
}
