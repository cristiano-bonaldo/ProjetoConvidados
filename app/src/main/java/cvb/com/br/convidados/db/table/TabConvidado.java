package cvb.com.br.convidados.db.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cvb.com.br.convidados.dao.DAOUtil;
import cvb.com.br.convidados.model.Convidado;

public class TabConvidado {

    public static String C_TABLE_NAME = "CONVIDADOS";

    public static String C_FIELD_ID     = "ID";
    public static String C_FIELD_NAME   = "NAME";
    public static String C_FIELD_STATUS = "STATUS";

    public static String C_SCRIPT_V1 =
            "CREATE TABLE " + C_TABLE_NAME + " (" +
            C_FIELD_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
        cv.put(TabConvidado.C_FIELD_NAME, convidado.getName());
        cv.put(TabConvidado.C_FIELD_STATUS, convidado.getStatus());

        return cv;
    }

    public List<Convidado> getList(int status) {
        ArrayList<Convidado> lista = new ArrayList<>();

        Cursor c = null;
        try {
            if (status == Convidado.C_CONVIDADO_NAO_CONFIRMADO ||
                status == Convidado.C_CONVIDADO_PRESENTE ||
                status == Convidado.C_CONVIDADO_AUSENTE)
                c = db.query(C_TABLE_NAME, null,C_FIELD_STATUS + " = ?", new String [status], null, null, C_FIELD_NAME);
            else
                c = db.query(C_TABLE_NAME, null,null, null, null, null, C_FIELD_NAME);

            if (c == null || c.getCount() == 0)
                return lista;

            while (c.moveToNext()) {
                lista.add(new Convidado(c.getString(c.getColumnIndex(C_FIELD_NAME)), c.getInt(c.getColumnIndex(C_FIELD_STATUS))));
            }

            if (c != null)
                c.close();
        }
        catch (Exception E) {
            return lista;
        }

        return lista;
    }
}
