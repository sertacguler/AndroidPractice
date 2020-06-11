package com.example.mycroft.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView gor;
    private EditText editTXT;

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView gonders =(ImageView) findViewById(R.id.button);
        gor =(TextView) findViewById(R.id.gor);
        editTXT = (EditText) findViewById(R.id.editText);

        db = FirebaseDatabase.getInstance();

        gonders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gonder();
            }
        });

        cek();

    }

    private void gonder() {

        DatabaseReference dbRef = db.getReference("messages");
        String key = dbRef.push().getKey();
        DatabaseReference dbRefYeni = db.getReference("messages/"+key);
        String message = editTXT.getText().toString().trim();
        if(!message.equals(""))
            dbRefYeni.setValue(message);

    }
    private void cek() {

        DatabaseReference okuma = db.getReference("messages");
        // Read from the database
        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                gor.setText("");
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for(DataSnapshot key:keys) {

                    gor.append(key.getValue().toString()+"\n");

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.v("asd", "Failed to read value.", error.toException());
            }
        });
    }

}
