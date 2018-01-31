package cvb.com.br.convidados.gui;

import android.content.Context;

import java.util.List;

import cvb.com.br.convidados.control.ControlConvidado;
import cvb.com.br.convidados.model.Convidado;

public class FragNaoConfirmados extends FragBase {
    @Override
    public List<Convidado> getListaConvidados(Context ctx) {
        return (new ControlConvidado()).getListaConvidadosAusentes(ctx);
    }
}
