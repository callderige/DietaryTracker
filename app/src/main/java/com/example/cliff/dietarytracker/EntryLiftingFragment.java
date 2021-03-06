package com.example.cliff.dietarytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class EntryLiftingFragment extends Fragment {
    Button submit;
    View fragmentView;
    DatabaseLifting dbLifting;
    String date;
    String name;
    String sets;
    String reps;
    String weight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_entry_lifting, container, false);

        submit = (Button) fragmentView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> LiftingList = getSubmittedLifts(fragmentView);
                date = Calendar.getInstance().get(Calendar.MONTH)+1 +"/" +
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +"/" +
                        Calendar.getInstance().get(Calendar.YEAR)+"";
                name = LiftingList.get(0);
                sets = LiftingList.get(1);
                reps = LiftingList.get(2);
                weight = LiftingList.get(3);

                dbLifting = new DatabaseLifting(getContext());
                dbLifting.insert(date, name, sets, reps, weight);
                dbLifting.close();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    public ArrayList<String> getSubmittedLifts(View view) {
        ArrayList<String> arrayList = new ArrayList<String>();
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.activity_lifting_entry);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view2 = layout.getChildAt(i);
            if (view2 instanceof EditText) {
                if (((EditText) view2).getText().toString().trim().equals("")) {
                    /* TYPE_CLASS_TEXT const value = 1
                       TYPE_TEXT_FLAG_CAP_SENTENCES const value = 16384
                       in fragment_entry_cardio.xml android:inputType="text|textCapSentences" returns a const value of 16385 */
                    if (((EditText) view2).getInputType() == InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_FLAG_CAP_SENTENCES) {
                        arrayList.add("NA");
                    } else if (((EditText) view2).getInputType() == InputType.TYPE_CLASS_NUMBER) {
                        arrayList.add("0");
                    } else {
                        arrayList.add("An error occurred");
                    }
                } else {
                    arrayList.add(((EditText) view2).getText().toString());
                }
            }
        }

        return arrayList;
    }
}
