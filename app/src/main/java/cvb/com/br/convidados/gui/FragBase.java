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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.List;

import cvb.com.br.convidados.R;
import cvb.com.br.convidados.control.ControlConvidado;
import cvb.com.br.convidados.model.Convidado;
import cvb.com.br.convidados.util.Constant;
import cvb.com.br.convidados.util.ToastUtil;

public abstract class FragBase extends Fragment {
    public class ViewHolderTela {
        RecyclerView rc;

        TextView tvNaoConfirmado;
        TextView tvPresente;
        TextView tvAusente;
        TextView tvTotal;

        ImageView ivTitle;
        TextView  tvTitle;

        private void init(View v) {
            rc = v.findViewById(R.id.recyclerview_convidados);

            tvNaoConfirmado = v.findViewById(R.id.tv_nao_confirmado);
            tvPresente      = v.findViewById(R.id.tv_presente);
            tvAusente       = v.findViewById(R.id.tv_ausente);
            tvTotal         = v.findViewById(R.id.tv_total);

            tvTitle = v.findViewById(R.id.tv_title);
            ivTitle = v.findViewById(R.id.iv_title);
        }
    }

    //------------------------------------------------------------------

    private ViewHolderTela vh = new ViewHolderTela();
    private Context ctx;

    protected String titleInfo;
    protected int titleIDImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base, container, false);
        vh.init(v);

        ctx = v.getContext();

        vh.rc.setLayoutManager(new LinearLayoutManager(ctx));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadPainel();
        loadData();
    }

    private ListConvidadosEvent listConvidadosEvent = new ListConvidadosEvent() {
        @Override
        public void onConvidadoClick(int id) {
            Intent it = new Intent(ctx, ActConvite.class);
            it.putExtra(Constant.EXTRA_ID, id);

            startActivity(it);
        }

        @Override
        public void onConvidadoDelete(int id) {
            ControlConvidado ctrConvidado = new ControlConvidado();
            if (ctrConvidado.delete(ctx, id)) {
                ToastUtil.showMessage(ctx, getString(R.string.exclusao_ok));

                loadPainel();
                loadData();
            }
            else
                ToastUtil.showMessage(ctx, getString(R.string.exclusao_erro));
        }
    };

    private void loadData() {
        List<Convidado> listConvidados = getListaConvidados(ctx);

        RecyclerView.Adapter adapter = new AdpterConvidados(ctx, listConvidados, listConvidadosEvent);
        vh.rc.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void loadPainel() {
        Hashtable<Integer, Integer> hashtable = (new ControlConvidado()).getQtdConvidados(ctx);

        loadTitle(ctx);

        vh.tvTitle.setText(titleInfo);
        vh.ivTitle.setImageResource(titleIDImage);

        vh.tvNaoConfirmado.setText(String.valueOf(hashtable.get(Convidado.C_CONVIDADO_NAO_CONFIRMADO)));
        vh.tvTotal.setText(String.valueOf(hashtable.get(Convidado.C_CONVIDADO_TODOS)));
        vh.tvAusente.setText(String.valueOf(hashtable.get(Convidado.C_CONVIDADO_AUSENTE)));
        vh.tvPresente.setText(String.valueOf(hashtable.get(Convidado.C_CONVIDADO_PRESENTE)));
    }

    public abstract List<Convidado> getListaConvidados(Context ctx);
    public abstract void loadTitle(Context ctx);

    //------------------------------------------------------------------

    private interface ListConvidadosEvent {
        void onConvidadoClick(int id);
        void onConvidadoDelete(int id);
    }

    //------------------------------------------------------------------

    private class ViewHolderConvidado extends RecyclerView.ViewHolder {
        private TextView tvNome;
        private ImageView ivExcluir;
        private Context ctx;

        public ViewHolderConvidado(Context ctx, View view) {
            super(view);
            this.ctx = ctx;

            tvNome    = view.findViewById(R.id.tv_nome);
            ivExcluir = view.findViewById(R.id.iv_excluir);
        }

        public void bind(final Convidado convidado, final ListConvidadosEvent listConvidadosEvent) {
            tvNome.setText(convidado.getName());
            tvNome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listConvidadosEvent.onConvidadoClick(convidado.getId());
                }
            });

            ivExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                    dialog.setTitle(R.string.remocao_convidado);
                    dialog.setMessage(R.string.remocao_convido_msg);
                    dialog.setIcon(R.drawable.ic_excluir);
                    dialog.setPositiveButton(R.string.bt_sim, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listConvidadosEvent.onConvidadoDelete(convidado.getId());
                        }
                    });
                    dialog.setNegativeButton(R.string.bt_nao, null);
                    dialog.show();
                }
            });

            /*
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
            */
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
