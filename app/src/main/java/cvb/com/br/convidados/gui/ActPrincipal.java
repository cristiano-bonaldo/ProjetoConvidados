package cvb.com.br.convidados.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import cvb.com.br.convidados.R;

public class ActPrincipal extends AppCompatActivity {

    private class ViewHolder {
        private FloatingActionButton btConvite;
        private Toolbar toolbar;
        private NavigationView navigationView;

        private void init() {
            btConvite = findViewById(R.id.bt_convite);

            toolbar = findViewById(R.id.toolbar);

            navigationView = findViewById(R.id.nav_view);
        }
    }

    // Firebase - Analytics
    private FirebaseAnalytics mFirebaseAnalytics;

    //============================================

    private ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        vh.init();

        adicaoPublicidadeAdMob();

        adicaoFirebase();

        setSupportActionBar(vh.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, vh.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        vh.navigationView.setNavigationItemSelectedListener(navigationViewListener);

        MenuItem itemMenuInicial = vh.navigationView.getMenu().getItem(0);
        itemMenuInicial.setChecked(true);
        navigationViewListener.onNavigationItemSelected(itemMenuInicial);

        setListener();
    }

    private void adicaoFirebase() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);
    }

    private void adicaoPublicidadeAdMob() {
        //Publicidade AdMob
        String AdMob_ID_APP = "ca-app-pub-1791259810056092~3359783446";
        MobileAds.initialize(this, AdMob_ID_APP);

        AdView mAdView = this.findViewById(R.id.adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void setListener() {
        vh.btConvite.setOnClickListener(btConviteListener);
    }

    private Context getContext() {
        return this;
    }

    View.OnClickListener btConviteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent(getContext(), ActConvite.class);
            getContext().startActivity(it);
        }
    };

    NavigationView.OnNavigationItemSelectedListener navigationViewListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /*
            Firebase - Crashlytics
            --
            Forçar ERRO8
            String erro = null;
            Log.i("CVB", "Erro nao tratado -> Crash do App" + erro.length());
            */

            int id = item.getItemId();

            Class fragmentClass;
            if (id == R.id.nav_todos) {
                fireBaseSelectContent("id_nav_todos", "ItemSelected");
                fragmentClass = FragTodos.class;

            } else if (id == R.id.nav_presentes) {
                fireBaseSelectContent("id_nav_presentes", "ItemSelected");
                fragmentClass = FragPresentes.class;

            } else if (id == R.id.nav_ausentes) {
                fireBaseSelectContent("id_nav_ausentes", "ItemSelected");
                fragmentClass = FragAusentes.class;

            } else if (id == R.id.nav_nao_confirmados) {
                fireBaseSelectContent("id_nav_nao_confirmados", "ItemSelected");
                fragmentClass = FragNaoConfirmados.class;

            } else {
                return true;
            }

            Fragment fragment = null;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            setFragment(fragment);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    private void fireBaseSelectContent(String id, String content) {
        /*
        Firebase - Analytics - Inicio
        */
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_config) {
            gerarErroCrashlytics();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    /*
    private void gerarErroCrashlytics() {
    // Firebase - Crashlytics - Inicio
    // --
    // Log para erros tratatos
        String msgErro = null;
        try {
            int x = msgErro.length();
        } catch (Exception E) {
            Log.i("CVB", "Erro -> Gerar Crashlytics");
            Crashlytics.log("Erro Tratado - onOptionsItemSelected");
            Crashlytics.logException(E);
        }
    // Firebase - Crashlytics - Fim
    }
    */

    private void setFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frag_container, fragment).commit();
    }
}
