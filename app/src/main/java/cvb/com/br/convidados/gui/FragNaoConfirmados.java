package cvb.com.br.convidados.gui;

import android.content.Context;

import java.util.List;

import cvb.com.br.convidados.R;
import cvb.com.br.convidados.control.ControlConvidado;
import cvb.com.br.convidados.model.Convidado;

public class FragNaoConfirmados extends FragBase {
    @Override
    public List<Convidado> getListaConvidados(Context ctx) {
        return (new ControlConvidado()).getListaConvidadosNaoConfirmados(ctx);
    }

    @Override
    public void loadTitle(Context ctx) {
        titleInfo    = ctx.getString(R.string.nao_confirmados);
        titleIDImage = R.drawable.ic_nao_confirmados;
    }
}
