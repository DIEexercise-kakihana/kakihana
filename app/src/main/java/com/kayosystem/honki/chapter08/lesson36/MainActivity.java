package com.kayosystem.honki.chapter08.lesson36;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.*;//SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private TextView mTxtToday;
    private TextView mTxtHistory;
    private String userNum = "0";
    private String name = "西澤";
    private String groupId = "0";

    /**
     * 現在年月日(yyyy-MM-dd;mm)を取得する
     *
     * @return 現在年月日(yyyy-MM-dd:mm)
     */
    private String getDate() {
        CharSequence charSequence = android.text.format.DateFormat.format("yyyy-MM-dd-HH:mm", Calendar.getInstance());
        return charSequence.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtToday = (TextView) findViewById(R.id.txToday);
        mTxtToday.setText(getDate());

        findViewById(R.id.btnArrive).setOnClickListener(this);
        findViewById(R.id.btnLeave).setOnClickListener(this);

        mTxtHistory = (TextView) findViewById(R.id.txtHistory);

        setup();
    }

    /**
     * Firebaseのデータベースからデータを取得する
     */

    private void setup() {

        String stNum = (String) userNum;
        DatabaseReference reference = database.getReference("attendance/" + stNum);
        Query query = reference.orderByKey();


        ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mTxtHistory.setText(null);

                StringBuilder sb = new StringBuilder();


                Study_data study_data[] = new Study_data[10];
                for(int k = 0;k < 10;k++){
                    study_data[k] = new Study_data();
                }
                int i = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();

                    //タイムスタンプ以外の要素は省く
                    if (key.toString().equals("login")) {
                        break;
                    }
                    if (key.toString().equals("charactorID")) {
                        break;
                    }
                    if (key.toString().equals("groupID")) {
                        break;
                    }

                    String subject = (String) snapshot.child("subject").getValue();
                    String time = (String) snapshot.child("time").getValue();

                    String dateStr = time;

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date formatDate = null;


                    try {
                        // Date型変換
                        formatDate = sdf.parse(dateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    study_data[i].subject = subject;
                    study_data[i].time = formatDate;
                    if(i<10 - 1){
                        i++;
                    }

                    //sb.append(key).append(" ").append(subject).append(" ").append(formatDate);//.append(" ").append(time);
                    //sb.append("\n");
                }


                for (int j = 0; j < 10; j++) {
                    sb.append(study_data[j].subject);
                    sb.append("\n");
                    sb.append(study_data[j].time);
                    sb.append("\n");
                }


                mTxtHistory.setText(sb.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    /**
     * 出社時間の追加
     * 同日に２回呼び出した場合は上書きする
     *
     * @param date   年月日
     * @param arrive 出社時刻
     */
    private void add(String date, String arrive) {

        //String userNum = "0";
        String name = "西澤";
        String groupId = "0";

        //Attendance attendance = new Attendance();
        //attendance.name = name;
        //attendance.groupId = groupId;
        //attendance.date = date;

        UserId userId = new UserId();
        userId.time = "1:00";
        userId.subject = "MATH";

        //DatabaseReference reference = database.getReference("attendance" + "/" + userNum);
        //reference.setValue(attendance);

        DatabaseReference reference_2 = database.getReference("attendance" + "/" + userNum+  "/" + date);
        reference_2.setValue(userId);
    }

    /**
     * 退社時間の更新
     *
     * @param date  年月日
     * @param leave 退社時刻
     */
    private void update(String date, String leave) {


        Map<String, Object> map = new HashMap<>();
        map.put("leave", leave);

        DatabaseReference reference = database.getReference("attendance" + "/" + date);
        reference.updateChildren(map);
    }

    /**
     * 現在時刻(HH:mm)を取得する
     *
     * @return 現在時刻(HH:mm)
     */
    private String getTime() {

        CharSequence charSequence = android.text.format.DateFormat.format("HH:mm", Calendar.getInstance());
        return charSequence.toString();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (R.id.btnArrive == id) {

            add(getDate(), getTime());
        } else {

            update(getDate(), getTime());
        }

    }
}
