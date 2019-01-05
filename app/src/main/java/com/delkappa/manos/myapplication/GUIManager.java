package com.delkappa.manos.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GUIManager {
    private static final GUIManager instance = new GUIManager();

    private GUIManager()
    {
        // System.out.println("Construction du Singleton au premier appel");
    }

    public static final GUIManager getInstance()
    {
        return instance;
    }

    public void generateUsersButtons(User user, int j, Context context, View view, final String departmentName ) {
        LinearLayout parent = view.findViewById(R.id.mainLinearLayout);

        final User userScope = user;
        StringBuffer textToSet = new StringBuffer();
        Log.i("ttt","dze");
        Log.i("posttEST",user.toString());
        Button myButton = new Button(context);
        // myButton.setText(user.userFirstName + " " + user.userName);
        textToSet.append(user.userFirstName + " " + user.userName);

        if (user.status.equals("able")) {
            myButton.setBackgroundColor(Color.parseColor("#ccff66"));
            textToSet.append(" - Able");
        } else if (user.status.equals("absent")) {
            myButton.setBackgroundColor(Color.parseColor("#ff9966"));
            textToSet.append(" - Absent");
        } else if (user.status.equals("occupied")) {
            myButton.setBackgroundColor(Color.parseColor("#ff6666"));
            textToSet.append(" - Occupied");
        } else if (user.status.equals("meeting")) {
            myButton.setBackgroundColor(Color.parseColor("#66ccff"));
            textToSet.append(" - In Meeting");
        }

        myButton.setText(textToSet.toString());

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i("MessageButton","Yeah");
                Intent mIntent = new Intent(v.getContext(), MessageActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("DepartmentName", departmentName);
                mBundle.putString("Number", userScope.getPhoneNumber());
                mIntent.putExtras(mBundle);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.i("INTENTTESTCONTEXT", v.getContext().toString());
                v.getContext().startActivity(mIntent);
                /*
                String srcRepository = "com.delkappa.manos.myapplication.";
                String departmentName = department.toString()+"Activity";
                String activityToStart = srcRepository + departmentName;
                */
                /*
                Intent intent;
                try {
                    Class<?> c = Class.forName(activityToStart);
                    intent = new Intent(DirectionActivity.this, c);
                    Log.i("testNAME", activityToStart);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                    Log.i("EXCEPTIONOFDEATH", activityToStart);
                }
                */
            }
        });
        //this.findViewById(android.R.id.content)
        //LinearLayout parento = fin
        Log.i("ADDVIEWTEST",parent.toString());
        parent.addView(myButton,j);
    }
    @Override
    public String toString()
    {
        return String.format("Je suis le GUIManager : %s", super.toString());
    }
}
