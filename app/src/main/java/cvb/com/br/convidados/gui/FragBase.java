package cvb.com.br.convidados.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cvb.com.br.convidados.R;
import cvb.com.br.convidados.control.ControlConvidado;
import cvb.com.br.convidados.model.Convidado;
import cvb.com.br.convidados.util.Constant;
import cvb.com.br.convidados.util.ToastUtil;

public abstract class FragBase extends Fragment {
    public class ViewHolderTela {
        RecyclerView rc;

        private void init(View v) {
            rc = v.findViewById(R.id.recyclerview_convidados);
        }
    }

    private ViewHolderTela vh = new ViewHolderTela();

    //------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base, container, false);
        vh.init(v);

        final Context ctx = v.getContext();

        ListConvidadosEvent listConvidadosEvent = new ListConvidadosEvent() {
            @Override
            public void onConvidadoClick(int id) {
                Intent it = new Intent(ctx, ActConvite.class);
                it.putExtra(Constant.EXTRA_ID, id);

                startActivity(it);
            }

            @Override
            public void onConvidadoDelete(int id) {
                ControlConvidado ctrConvidado = new ControlConvidado();
                if (ctrConvidado.delete(getContext(), id))
                    ToastUtil.showMessage(getContext(), getString(R.string.exclusao_ok));
                else
                    ToastUtil.showMessage(getContext(), getString(R.string.exclusao_erro));
            }
        };

        vh.rc.setLayoutManager(new LinearLayoutManager(ctx));

        List<Convidado> listConvidados = getListaConvidados(ctx);

        RecyclerView.Adapter adapter = new AdpterConvidados(ctx, listConvidados, listConvidadosEvent);
        vh.rc.setAdapter(adapter);
        return v;
    }

    public abstract List<Convidado> getListaConvidados(Context ctx);

    //------------------------------------------------------------------

    private interface ListConvidadosEvent {
        public void onConvidadoClick(int id);
        public void onConvidadoDelete(int id);
    }

    //------------------------------------------------------------------

    private class ViewHolderConvidado extends RecyclerView.ViewHolder {
        private TextView tvNome;
        private Context ctx;

        public ViewHolderConvidado(Context ctx, View view) {
            super(view);
            this.ctx = ctx;

            tvNome = view.findViewById(R.id.tv_nome);
        }

        public void bind(final Convidado convidado, final ListConvidadosEvent listConvidadosEvent) {
            tvNome.setText(convidado.getName());
            tvNome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listConvidadosEvent.onConvidadoClick(convidado.getId());
                }
            });

            tvNome.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                    dialog.setTitle(R.string.remocao_convidado);
                    dialog.setMessage(R.string.remocao_convido_msg);
                    dialog.setIcon(android.R.drawable.ic_delete);
                    dialog.setPositiveButton(R.string.bt_sim, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listConvidadosEvent.onConvidadoDelete(convidado.getId());
                        }
                    });
                    dialog.setNegativeButton(R.string.bt_nao, null);
                    dialog.show();

                    return true;
                }
            });
        }
    }

    //---------

    private class AdpterConvidados extends RecyclerView.Adapter<ViewHolderConvidado> {
        private List<Convidado> listaConvidados;
        private LayoutInflater inflater;
        private ListConvidadosEvent listConvidadosEvent;
        private Context ctx;

        public AdpterConvidados(Context ctx, List<Convidado> listaConvidados, ListConvidadosEvent listConvidadosEvent) {
            this.ctx                 = ctx;
            this.listaConvidados     = listaConvidados;
            this.inflater            = LayoutInflater.from(ctx);
            this.listConvidadosEvent = listConvidadosEvent;
        }

        @Override
        public ViewHolderConvidado onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = this.inflater.inflate(R.layout.row_convidado, parent, false);
            return new ViewHolderConvidado(ctx, v);
        }

        @Override
        public void onBindViewHolder(ViewHolderConvidado holder, int position) {
            holder.bind(listaConvidados.get(position), this.listConvidadosEvent);
        }

        @Override
        public int getItemCount() {
            return (listaConvidados == null ? 0 : listaConvidados.size());
        }
    }
}
