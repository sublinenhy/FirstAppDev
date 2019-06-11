package com.example.camden.greenhandproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

public class EditActivity extends AppCompatActivity {

    private EditText title ;
    private EditText content;
    private EditText picture_link;
    private NoteDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //引入了第三方包https://github.com/gyf-dev/ImmersionBar
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.colorPrimary)
                .init();

        title = findViewById(R.id.et_title);
        content = findViewById(R.id.et_content);
        picture_link = findViewById(R.id.et_picture_link);
        dbHelper = new NoteDatabaseHelper(this,"Note",null,1);

        Log.d("camden","database created");

    }

    public void submit(View view) {

        NoteBean note = new NoteBean();
        note.setTitle(title.getText().toString());
        note.setContent(content.getText().toString());
        note.setPictureLink(picture_link.getText().toString());
        note.setType("text_note");
        dbHelper.addNote(note);
        Toast.makeText(EditActivity.this,"笔记已提交",Toast.LENGTH_LONG).show();

    }

    public void back(View view) {

        Intent intent=new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
