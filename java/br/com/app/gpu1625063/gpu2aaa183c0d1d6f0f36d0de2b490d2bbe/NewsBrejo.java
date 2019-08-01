package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.util.webClient;

public class NewsBrejo extends AppCompatActivity {

    private ProgressBar bar;
    private WebView web;
    private TextView erro;
    private String url = "http://blog.joandesonandrade.com.br/android.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_brejo);

        getSupportActionBar().setTitle("Brejo News");
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bar =  (ProgressBar)findViewById(R.id.news_bar);
        web =  (WebView)findViewById(R.id.news_web);
        erro = (TextView)findViewById(R.id.news_erro);


        /**obtem a identificação da notificação**/
        if(getIntent().getStringExtra("id")!=null){
            url ="http://blog.joandesonandrade.com.br/ler/?id="+getIntent().getStringExtra("id");
        }

        web.loadUrl(url);
        web.getSettings().setJavaScriptEnabled(true);
        //web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        web.setWebViewClient(new webClient(getApplicationContext(),bar,web,erro));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if(id == R.id.news_share){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "veja essa notícia disponível em "+web.getUrl());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:

                if(web.canGoBack()){
                    web.goBack();
                }else {
                    finish();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if(web.canGoBack()){
            web.goBack();
        }else {
            finish();
        }
    }
}
