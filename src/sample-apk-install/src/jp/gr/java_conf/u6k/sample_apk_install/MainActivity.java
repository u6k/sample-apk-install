/*
Copyright (c) 2012 Yuichi Naono (u6k.yu1.main@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package jp.gr.java_conf.u6k.sample_apk_install;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText _saveStringEdit;

    private TextView _savedStringText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _saveStringEdit = (EditText) findViewById(R.id.save_string_edit);
        Button saveButton = (Button) findViewById(R.id.save_button);
        Button loadButton = (Button) findViewById(R.id.load_button);
        _savedStringText = (TextView) findViewById(R.id.saved_string_text);

        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
            String title = getTitle() + " v" + packageInfo.versionName;
            setTitle(title);
        } catch (NameNotFoundException e) {
            Log.e("sample-apk-install", "", e);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_button) {
            onClickSaveButton(v);
        } else if (v.getId() == R.id.load_button) {
            onClickLoadButton(v);
        } else {
            throw new RuntimeException();
        }
    }

    private void onClickSaveButton(View v) {
        String saveString = _saveStringEdit.getText().toString();

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Editor prefEditor = pref.edit();
        prefEditor.putString("str", saveString);
        if (!prefEditor.commit()) {
            throw new RuntimeException();
        }

        Toast.makeText(this, "文字列を保存しました。", Toast.LENGTH_SHORT).show();
    }

    private void onClickLoadButton(View v) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String saveString = pref.getString("str", null);

        _savedStringText.setText(saveString);

        Toast.makeText(this, "文字列を読み込みました。", Toast.LENGTH_SHORT).show();
    }

}
