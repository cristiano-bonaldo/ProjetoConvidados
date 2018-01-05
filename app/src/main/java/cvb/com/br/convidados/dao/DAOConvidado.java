package cvb.com.br.convidados.dao;

import android.content.Context;

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
}
