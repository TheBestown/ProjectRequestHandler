package com.klpi.projectrequesthandler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();

    TextView view_poin;
    TextView view_trk;
    TextView nama_member;
    TextView kode_member;
    TextView view_no_telp;
    TextView view_ktp;
    TextView view_tglLahir;
    TextView view_kota;
    TextView view_leader;
    TextView view_saldo;
    TextView verifikasi;

    Button dataTransaksi;

    String trk;
    String id_Member;
    String saldo;
    String nama;
    String kodeMember;
    String no_telp;
    String ktp;
    String tglLahir;
    String kota;
    String leaderM;
    String verified;

    double poin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        String newString;


        id_Member = "919";
        verified = "1";



        nama_member = (TextView) findViewById(R.id.nama_member);
        kode_member = (TextView)findViewById(R.id.kode_member);
        view_saldo = (TextView)findViewById(R.id.saldoMember);
        view_no_telp = (TextView)findViewById(R.id.noTelp);
        view_ktp = (TextView)findViewById(R.id.ktp);
        view_tglLahir = (TextView)findViewById(R.id.tglLahir);
        view_kota = (TextView)findViewById(R.id.kota);
        view_leader = (TextView)findViewById(R.id.leader);
        view_poin = (TextView) findViewById(R.id.poin);
        view_trk = (TextView) findViewById(R.id.trns);

        dataTransaksi = (Button) findViewById(R.id.DataTransaksi);
        dataTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataSaya= new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(dataSaya);

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getDataMember();
    }

    private void getDataMember(){


        class GetDataMember extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        ProfileActivity.this,
                        "Mengambil Data...",
                        "Mohon Menunggu...",
                        false,
                        false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
//                showEmployee(s);
            }

            @Override
            protected String doInBackground(String... arg0) {

                String key = "12345";
//                String key = "--[Your Token]--";
                List<NameValuePair> nvp = new ArrayList<NameValuePair>();
//                nvp.add(new BasicNameValuePair("id", id_Member));//POST MENGGUNAKAN INI
                nvp.add(new BasicNameValuePair("token", key));//POST MENGGUNAKAN INI

//                JSONObject json = jsonParser.makeHttpRequest("https://daun.thedemit.com/api/auth/TestCheckProfile", "POST", nvp);
                JSONObject json = jsonParser.makeHttpRequest("https://daun.thedemit.com/api/auth/TestCheckProfile/"+id_Member, "GET", nvp);
                try {
                    String success = json.getString("success");// nilainya 1
                    Log.e("error", "nilai sukses=" + success);

                    JSONArray hasil = json.getJSONArray("data");

                    if (success.equals("1")) {

                        for (int i = 0; i < hasil.length(); i++) {

                            JSONObject c = hasil.getJSONObject(i);

                            nama = c.getString("nama_member").trim();
                            kodeMember = c.getString("kode_member").trim();
                            saldo = c.getString("saldo_member").trim();

                            no_telp = c.getString("nomor_telepon").trim();
                            ktp = c.getString("id_ktp").trim();
                            tglLahir = c.getString("tgl_lahir").trim();
                            kota = c.getString("kota").trim();
                            trk = c.getString("transaksi").trim();
                            leaderM = c.getString("leader").trim();
                            Log.e("ok", " ambil data");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nama_member.setText(nama);
                                kode_member.setText(kodeMember);
                                view_saldo.setText(saldo);

                                view_no_telp.setText(no_telp);
                                view_ktp.setText(ktp);
                                view_tglLahir.setText(tglLahir);
                                view_kota.setText(kota);
                                int result = Integer.parseInt(trk);
                                poin = result/5;

                                view_trk.setText(trk);
                                view_poin.setText(String.format("%.0f", poin));
//                                view_leader.setText(String.format("Welcome %s", km));
                                view_leader.setText(Html.fromHtml("Leader (<b>" + leaderM + "</b>)  "));
//                                view_leader.setText(leader);
                            }
                        });


                    } else {
                        String gagal = json.getString("message");

                        Log.e("error", "nilai sukses=" + gagal);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error AsynTask",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return null;
            }
        }
        GetDataMember gdm = new GetDataMember();
        gdm.execute();
    }


    public void dataSaya(View view) {
        Intent dataSaya= new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(dataSaya);
    }

    public void kontakSaya(View view) {
        Intent kontakSaya= new Intent(ProfileActivity.this, MalformedInputException.class);
        startActivity(kontakSaya);
    }
}
