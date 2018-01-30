package cvb.com.br.convidados.dao;

import android.content.Context;

import java.util.List;

import cvb.com.br.convidados.db.table.TabConvidado;
import cvb.com.br.convidados.model.Convidado;

public class DAOConvidado {

    private TabConvidado tabConvidado;

    public DAOConvidado(Context ctx) {
        tabConvidado = new TabConvidado(ctx);
    }

    public boolean inserir(Convidado convidado) {
        long id = tabConvidado.inserir(convidado);

        return (id >= 0);
    }

    public List<Convidado> getListTodos() {
        return tabConvidado.getList(Convidado.C_CONVIDADO_TODOS);
    }

//    public List<Convidado> getListTodos() {
//        return tabConvidado.getList(Convidado.C_CONVIDADO_TODOS);
//    }
//
//    public List<Convidado> getListTodos() {
//        return tabConvidado.getList(Convidado.C_CONVIDADO_TODOS);
//    }
}
