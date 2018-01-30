package cvb.com.br.convidados.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cvb.com.br.convidados.R;
import cvb.com.br.convidados.dao.DAOConvidado;
import cvb.com.br.convidados.model.Convidado;

public class FragTodos extends Fragment {
    public class ViewHolderTela {
        RecyclerView rc;

        private void init(View v) {
            rc = v.findViewById(R.id.recyclerview_convidados);
        }
    }

    private ViewHolderTela vh = new ViewHolderTela();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todos, container, false);
        vh.init(v);

        Context ctx = v.getContext();
        vh.rc.setLayoutManager(new LinearLayoutManager(ctx));

        List<Convidado> listaConvidados = (new DAOConvidado(ctx)).getListTodos();
        RecyclerView.Adapter adapter = new AdpterConvidados(ctx, listaConvidados);
        vh.rc.setAdapter(adapter);
        return v;
    }

    //---------

    private class ViewHolderConvidado extends RecyclerView.ViewHolder {
        private TextView tvNome;

        public ViewHolderConvidado(View view) {
            super(view);
            tvNome = view.findViewById(R.id.tv_nome);
        }

        public void bind(Convidado convidado) {
            tvNome.setText(convidado.getName());
        }
    }

    //---------

    private class AdpterConvidados extends RecyclerView.Adapter<ViewHolderConvidado> {
        private List<Convidado> listaConvidados;
        private LayoutInflater inflater;

        public AdpterConvidados(Context ctx, List<Convidado> listaConvidados) {
            this.listaConvidados = listaConvidados;
            this.inflater = LayoutInflater.from(ctx);
        }

        @Override
        public ViewHolderConvidado onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = this.inflater.inflate(R.layout.row_convidado, parent, false);
            return new ViewHolderConvidado(v);
        }

        @Override
        public void onBindViewHolder(ViewHolderConvidado holder, int position) {
            holder.bind(listaConvidados.get(position));
        }

        @Override
        public int getItemCount() {
            return (listaConvidados == null ? 0 : listaConvidados.size());
        }
    }
}
