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
import cvb.com.br.convidados.util.Constant;
import cvb.com.br.convidados.util.ToastUtil;

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

    private Convidado convidado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convite);

        vh.init(this);

        loadConvidado();

        setListener();
    }

    private void loadConvidado() {
        int id = 0;
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constant.EXTRA_ID)) {
            id = getIntent().getExtras().getInt(Constant.EXTRA_ID, 0);
        }

        convidado = null;
        if (id > 0) {
            convidado = (new ControlConvidado()).getConvidado(this, id);

            vh.edName.setText(convidado.getName());
            vh.rbPresente.setChecked(convidado.isPresente());
            vh.rbAusente.setChecked(convidado.isAusente());
            vh.rbNaoConfirmado.setChecked(convidado.isNaoConfirmado());
        }
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
            if (!this.valicacao())
                return;

            int status;
            if (vh.rbNaoConfirmado.isChecked())
                status = Convidado.C_CONVIDADO_NAO_CONFIRMADO;
            else if (vh.rbPresente.isChecked())
                status = Convidado.C_CONVIDADO_PRESENTE;
            else
                status = Convidado.C_CONVIDADO_AUSENTE;

            if (convidado == null)
                convidado = new Convidado(vh.edName.getText().toString(), status);
            else {
                convidado.setName(vh.edName.getText().toString());
                convidado.setStatus(status);
            }

            ControlConvidado ctrConvidado = new ControlConvidado();
            if (ctrConvidado.save(getContext(), convidado)) {
                ToastUtil.showMessage(getContext(), getString(R.string.gravacao_ok));
                finish();
            }
            else
                ToastUtil.showMessage(getContext(), getString(R.string.gravacao_erro));
        }

        private boolean valicacao() {
            if (vh.edName.getText().toString().isEmpty()) {
                vh.edName.setError(getString(R.string.valida_nome));
                return false;
            }

            return true;
        }
    };
}
