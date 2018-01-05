package cvb.com.br.convidados.control;

import android.content.Context;

import cvb.com.br.convidados.dao.DAOConvidado;
import cvb.com.br.convidados.model.Convidado;

public class ControlConvidado {

    public boolean inserir(Context ctx, Convidado convidado) {
        try {
            return (new DAOConvidado(ctx)).inserir(convidado);
        } catch (Exception E) {
            return false;
        }
    }

}
