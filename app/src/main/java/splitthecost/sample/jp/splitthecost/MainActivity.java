package splitthecost.sample.jp.splitthecost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.calcButton);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // オブジェクトを取得
                EditText etxtNum = (EditText) findViewById(R.id.eTxtNum);
                EditText etxtMoney = (EditText) findViewById(R.id.eTxtYen);
                TextView txtResult = (TextView) findViewById(R.id.calcResult);
                TextView errorMessage = (TextView)findViewById(R.id.errorMessage);

                // 設定を取得
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String frac = pref.getString(SettingPrefActivity.PREF_KEY_FRACTION, "500");
                Boolean roundup = pref.getBoolean(SettingPrefActivity.PREF_KEY_ROUNDUP, false);
                int fracVal = Integer.parseInt(frac);

                try {
                    // 入力内容を取得
                    String strNum = etxtNum.getText().toString();
                    String strMoney = etxtMoney.getText().toString();

                    // 数値に変換
                    int num = Integer.parseInt(strNum);
                    int money = Integer.parseInt(strMoney);

                    // 割り勘計算
                    int result = money / num;

                    // 切り上げ
                    if (roundup && result % fracVal != 0) {
                        result += fracVal;
                    }

                    // 端数処理
                    result = result / fracVal * fracVal;

                    // 結果表示用テキストに設定
                    txtResult.setText(String.valueOf(result));
                    errorMessage.setText("　");
                }catch(NumberFormatException e){
                    e.printStackTrace();
                    txtResult.setText("0");
                    errorMessage.setText("人数と金額を正しく入力してください");
                }
            }
        });
    }

    // 以下設定画面遷移の処理

    /**
     * menu.xmlとの関連付け
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 画面遷移メソッド
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // 設定ボタン押下処理
                // 遷移先のアクティビティを指定
                Intent intent = new Intent(MainActivity.this, SettingPrefActivity.class);
                // 遷移処理
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
