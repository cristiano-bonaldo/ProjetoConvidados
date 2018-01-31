package cvb.com.br.convidados.control;

import android.content.Context;

import java.util.List;

import cvb.com.br.convidados.dao.DAOConvidado;
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

    public Convidado getConvidado(Context ctx, int id) {
        return (new DAOConvidado(ctx)).getConvidadoByID(id);
    }
}
