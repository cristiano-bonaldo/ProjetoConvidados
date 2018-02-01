package cvb.com.br.convidados.control;

import android.content.Context;

import java.util.Hashtable;
import java.util.List;

import cvb.com.br.convidados.dao.DAOConvidado;
import cvb.com.br.convidados.db.table.TabConvidado;
import cvb.com.br.convidados.model.Convidado;

public class ControlConvidado {

    public boolean save(Context ctx, Convidado convidado) {
        try {
            if (convidado.getId() > 0)
                return (new DAOConvidado(ctx)).update(convidado);
            else
                return (new DAOConvidado(ctx)).insert(convidado);
        } catch (Exception E) {
            return false;
        }
    }

    public boolean delete(Context ctx, int id) {
        try {
            return (new DAOConvidado(ctx)).delete(id);
        } catch (Exception E) {
            return false;
        }
    }

    public List<Convidado> getListaConvidadosTodos(Context ctx) {
        return (new DAOConvidado(ctx)).getListTodos();
    }

    public List<Convidado> getListaConvidadosAusentes(Context ctx) {
        return (new DAOConvidado(ctx)).getListAusentes();
    }

    public List<Convidado> getListaConvidadosPresentes(Context ctx) {
        return (new DAOConvidado(ctx)).getListPresentes();
    }

    public List<Convidado> getListaConvidadosNaoConfirmados(Context ctx) {
        return (new DAOConvidado(ctx)).getListNaoConfirmados();
    }

    public Hashtable<Integer, Integer> getQtdConvidados(Context ctx) {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();

        DAOConvidado dao = new DAOConvidado(ctx);

        hashtable.put(Convidado.C_CONVIDADO_NAO_CONFIRMADO, dao.getQtdConvidado(Convidado.C_CONVIDADO_NAO_CONFIRMADO));
        hashtable.put(Convidado.C_CONVIDADO_PRESENTE, dao.getQtdConvidado(Convidado.C_CONVIDADO_PRESENTE));
        hashtable.put(Convidado.C_CONVIDADO_AUSENTE, dao.getQtdConvidado(Convidado.C_CONVIDADO_AUSENTE));
        hashtable.put(Convidado.C_CONVIDADO_TODOS, dao.getQtdConvidado(Convidado.C_CONVIDADO_TODOS));

        return hashtable;
    }

    public Convidado getConvidado(Context ctx, int id) {
        return (new DAOConvidado(ctx)).getConvidadoByID(id);
    }
}
