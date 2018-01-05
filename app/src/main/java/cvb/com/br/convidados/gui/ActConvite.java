package cvb.com.br.convidados.gui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import cvb.com.br.convidados.R;
import cvb.com.br.convidados.control.ControlConvidado;
import cvb.com.br.convidados.model.Convidado;

public class ActConvite extends AppCompatActivity {

    private class ViewHolder {
        private EditText edName;
        private Button btGravar;
        private RadioButton rbNaoConfirmado;
        private RadioButton rbPresente;
        private RadioButton rbAusente;

        private void init(Activity act) {

            edName = act.findViewById(R.id.ed_nome);

            rbNaoConfirmado = act.findViewById(R.id.rb_nao_confirmado);
            rbPresente      = act.findViewById(R.id.rb_presente);
            rbAusente       = act.findViewById(R.id.rb_ausente);

            btGravar = act.findViewById(R.id.bt_gravar);
        }
    }

    private ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convite);

        vh.init(this);

        setListener();
    }

    private void setListener() {
        vh.btGravar.setOnClickListener(btGravarListener);
    }

    private Context getContext() {
        return this;
    }

    View.OnClickListener btGravarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int status;
            if (vh.rbNaoConfirmado.isChecked())
                status = Convidado.C_CONVIDADO_NAO_CONFIRMADO;
            else if (vh.rbPresente.isChecked())
                status = Convidado.C_CONVIDADO_PRESENTE;
            else
                status = Convidado.C_CONVIDADO_AUSENTE;

            Convidado convidado = new Convidado(vh.edName.getText().toString(), status);

            ControlConvidado ctrConvidado = new ControlConvidado();
            ctrConvidado.inserir(getContext(), convidado);
        }
    };
}
