package cvb.com.br.convidados.dao;

import android.content.Context;

import java.util.List;

import cvb.com.br.convidados.db.table.TabConvidado;
import cvb.com.br.convidados.model.Convidado;

public class DAOConvidado {

    private TabConvidado tabConvidado;

    public DAOConvidado(Context ctx) {
        tabConvidado = new TabConvidado(DAOUtil.getInstance(ctx).getDB());
    }

    public boolean insert(Convidado convidado) {
        long id = tabConvidado.inserir(convidado);

        return (id >= 0);
    }

    public boolean update(Convidado convidado) {
        long qtd = tabConvidado.atualizar(convidado);

        return (qtd >= 0);
    }

    public boolean delete(int id) {
        long qtd = tabConvidado.excluir(id);

        return (qtd >= 0);
    }

    public List<Convidado> getListTodos() {
        return getListConvidado(Convidado.C_CONVIDADO_TODOS);
    }

    public List<Convidado> getListAusentes() {
        return getListConvidado(Convidado.C_CONVIDADO_AUSENTE);
    }

    public List<Convidado> getListPresentes() {
        return getListConvidado(Convidado.C_CONVIDADO_PRESENTE);
    }

    public List<Convidado> getListNaoConfirmados() {
        return getListConvidado(Convidado.C_CONVIDADO_NAO_CONFIRMADO);
    }

    private List<Convidado> getListConvidado(int tipo) {
        return tabConvidado.getList(tipo);
    }

    public int getQtdConvidado(int tipo) {
        return tabConvidado.getRecordCount(tipo);
    }

    public Convidado getConvidadoByID(int id) {
        return tabConvidado.getID(id);
    }
}
