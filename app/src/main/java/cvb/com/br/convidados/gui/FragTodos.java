package cvb.com.br.convidados.gui;

import android.content.Context;

import java.util.List;

import cvb.com.br.convidados.R;
import cvb.com.br.convidados.control.ControlConvidado;
import cvb.com.br.convidados.model.Convidado;

public class FragTodos extends FragBase {
    @Override
    public List<Convidado> getListaConvidados(Context ctx) {
        return (new ControlConvidado()).getListaConvidadosTodos(ctx);
    }

    @Override
    public void loadTitle(Context ctx) {
        titleInfo    = ctx.getString(R.string.todos);
        titleIDImage = R.drawable.ic_todos;
    }
}
