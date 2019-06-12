package com.example.camden.greenhandproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteDatabaseHelper dbHelper;
    private LinkedList<NoteBean> notes;
    private static final String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //引入了第三方包https://github.com/gyf-dev/ImmersionBar
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.colorPrimary)
                .init();


        dbHelper = new NoteDatabaseHelper(this,"Note",null,1);
        notes = dbHelper.readNotes();

        mRecyclerView = findViewById(R.id.rv_note_display);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        MyAdapter mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        basicReadWrite();



    }

    public void toEdit(View view) {

        Intent intent=new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);

    }

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message/test1");

        myRef.setValue("Hello, World!");
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        class MyViewHolder extends RecyclerView.ViewHolder{
            public View itemView;
            public TextView note_title, note_content;
            public ImageView note_dot, note_picture;


            public MyViewHolder(@NonNull View v) {
                super(v);
                itemView = v;
                note_title = itemView.findViewById(R.id.tv_note_title);
                note_content = itemView.findViewById(R.id.tv_note_content);
                note_dot = itemView.findViewById(R.id.iv_note_dot);
                note_picture = itemView.findViewById(R.id.iv_note_picture);
            }
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_item_view, viewGroup, false);
            MyViewHolder vh = new MyViewHolder(itemView);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder viewHolder, int i) {

            viewHolder.note_title.setText(notes.get(i).getTitle());
            viewHolder.note_content.setText(notes.get(i).getContent());
//            viewHolder.note_picture.
            viewHolder.note_dot.setImageResource(R.drawable.blue_dot);
            viewHolder.note_picture.setImageResource(R.drawable.image_mini_shape);
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }
    }



}
