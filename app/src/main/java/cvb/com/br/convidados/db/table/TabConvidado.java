package cvb.com.br.convidados.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cvb.com.br.convidados.model.Convidado;

public class TabConvidado {

    private static String C_TABLE_NAME = "CONVIDADOS";

    private static String C_FIELD_ID     = "ID";
    private static String C_FIELD_NAME   = "NAME";
    private static String C_FIELD_STATUS = "STATUS";

    public static String C_SCRIPT_V1 =
            "CREATE TABLE " + C_TABLE_NAME + " (" +
            C_FIELD_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            C_FIELD_NAME   + " TEXT(200), " +
            C_FIELD_STATUS + " INTEGER(1) " +
            ")";

    private SQLiteDatabase db;

    public TabConvidado(SQLiteDatabase db) {
        this.db = db;
    }

    public long inserir(Convidado convidado) {
        return db.insert(C_TABLE_NAME, null, getContentValues(convidado));
    }

    public long atualizar(Convidado convidado) {
        return db.update(C_TABLE_NAME, getContentValues(convidado), TabConvidado.C_FIELD_ID + " = ? ", new String[] { String.valueOf(convidado.getId()) } );
    }

    public long excluir(int id) {
        return db.delete(C_TABLE_NAME, TabConvidado.C_FIELD_ID + " = ? ", new String[] { String.valueOf(id) } );
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
                c = db.query(C_TABLE_NAME, null,C_FIELD_STATUS + " = ?", new String[] { String.valueOf(status) }, null, null, C_FIELD_NAME);
            else
                c = db.query(C_TABLE_NAME, null,null, null, null, null, C_FIELD_NAME);

            if (c == null || c.getCount() == 0)
                return lista;

            while (c.moveToNext()) {
                lista.add(new Convidado(c.getInt(c.getColumnIndex(C_FIELD_ID)), c.getString(c.getColumnIndex(C_FIELD_NAME)), c.getInt(c.getColumnIndex(C_FIELD_STATUS))));
            }
        }
        catch (Exception E) {
            return lista;
        }
        finally {
            if (c != null)
                c.close();
        }

        return lista;
    }

    public int getRecordCount(int status) {
        int qtd = 0;

        Cursor c = null;
        try {
            if (status == Convidado.C_CONVIDADO_TODOS)
                c = db.rawQuery("SELECT count(*) FROM " + TabConvidado.C_TABLE_NAME, null);
            else
                c = db.rawQuery("SELECT count(*) FROM " + TabConvidado.C_TABLE_NAME + " WHERE " + C_FIELD_STATUS + " = ?" , new String[] { String.valueOf(status) });

            if (c == null || c.getCount() == 0)
                return qtd;

            while (c.moveToNext())
                qtd = c.getInt(0);
        }
        catch (Exception E) {
            return qtd;
        }
        finally {
            if (c != null)
                c.close();
        }

        return qtd;
    }

    public Convidado getID(int id) {
        Convidado convidado = null;

        Cursor c = null;
        try {
            c = db.query(C_TABLE_NAME, null, C_FIELD_ID + " = ?", new String[] {String.valueOf(id) }, null, null, C_FIELD_NAME);

            if (c == null || c.getCount() == 0)
                return null;

            if (c.moveToNext()) {
                convidado = new Convidado(c.getInt(c.getColumnIndex(C_FIELD_ID)), c.getString(c.getColumnIndex(C_FIELD_NAME)), c.getInt(c.getColumnIndex(C_FIELD_STATUS)));
            }
        } catch (Exception E) {
            return null;
        } finally {
            if (c != null && !c.isClosed())
                c.close();
        }

        return convidado;
    }
}
