package com.example.expensemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanager.Model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Floating button
    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;

    //Floating button textView
    private TextView fab_income_text;
    private TextView fab_expense_text;

    //boolean
    private boolean isOpen= false;

    //Animation
    private Animation FadOpen, FadClose;

    //Dashboard income and expense result...
    private TextView totalIncomeResult;
    private TextView totalExpenseResult;

    //Firebase...
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_dash_board, container, false);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uid= mUser.getUid();

        mIncomeDatabase= FirebaseDatabase.getInstance().getReference("IncomeData").child(uid);
        mExpenseDatabase= FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        //connect floating button to layout
        fab_main_btn=myview.findViewById(R.id.fb_main_plus_btn);
        fab_income_btn=myview.findViewById(R.id.income_ft_btn);
        fab_expense_btn=myview.findViewById(R.id.expense_ft_btn);

        //connect floating text to layout
        fab_income_text=myview.findViewById(R.id.income_ft_text);
        fab_expense_text=myview.findViewById(R.id.expense_ft_text);

        //Total income and expense set...
        totalIncomeResult=myview.findViewById(R.id.income_set_result);
        totalExpenseResult=myview.findViewById(R.id.expense_set_result);

        //animation connect...
        FadOpen= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadClose= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);


        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addData();

                if(isOpen){
                    fab_income_btn.startAnimation(FadClose);
                    fab_expense_btn.startAnimation(FadClose);
                    fab_income_btn.setClickable(false);
                    fab_expense_btn.setClickable(false);

                    fab_income_text.startAnimation(FadClose);
                    fab_expense_text.startAnimation(FadClose);
                    fab_income_text.setClickable(false);
                    fab_expense_text.setClickable(false);
                    isOpen=false;
                }else {
                    fab_income_btn.startAnimation(FadOpen);
                    fab_expense_btn.startAnimation(FadOpen);
                    fab_income_btn.setClickable(true);
                    fab_expense_btn.setClickable(true);

                    fab_income_text.startAnimation(FadOpen);
                    fab_expense_text.startAnimation(FadOpen);
                    fab_income_text.setClickable(true);
                    fab_expense_text.setClickable(true);
                    isOpen=true;
                }
            }
        });

        //Calculate total income...
        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalSum=0;
                for(DataSnapshot mysnap:dataSnapshot.getChildren()){
                    Data data=mysnap.getValue(Data.class);
                    totalSum+=data.getAmount();

                    String stResult=String.valueOf(totalSum);
                    totalIncomeResult.setText(stResult);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Calculate total expense...
        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalsum=0;
                for(DataSnapshot mysnap:dataSnapshot.getChildren()){
                    Data data=mysnap.getValue(Data.class);
                    totalsum+=data.getAmount();

                    String strTotalsum=String.valueOf(totalsum);
                    totalExpenseResult.setText(strTotalsum);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return myview;
    }

    //Floating button animation

    private void ftAnimation(){
        if(isOpen){
            fab_income_btn.startAnimation(FadClose);
            fab_expense_btn.startAnimation(FadClose);
            fab_income_btn.setClickable(false);
            fab_expense_btn.setClickable(false);

            fab_income_text.startAnimation(FadClose);
            fab_expense_text.startAnimation(FadClose);
            fab_income_text.setClickable(false);
            fab_expense_text.setClickable(false);
            isOpen=false;
        }else {
            fab_income_btn.startAnimation(FadOpen);
            fab_expense_btn.startAnimation(FadOpen);
            fab_income_btn.setClickable(true);
            fab_expense_btn.setClickable(true);

            fab_income_text.startAnimation(FadOpen);
            fab_expense_text.startAnimation(FadOpen);
            fab_income_text.setClickable(true);
            fab_expense_text.setClickable(true);
            isOpen=true;
        }
    }

    private void addData(){
        // Fab button income...

        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                incomeDataInsert();
            }
        });

        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expenseDataInsert();
            }
        });
    }
    public void incomeDataInsert(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myviewm=inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        mydialog.setView(myviewm);
        final AlertDialog dialog=mydialog.create();

        dialog.setCancelable(false);

        final EditText EdtAmmount=myviewm.findViewById(R.id.ammount_edt);
        final EditText EdtType=myviewm.findViewById(R.id.type_edt);
        final EditText EdtNote=myviewm.findViewById(R.id.note_edt);

        Button btnSAVE=myviewm.findViewById(R.id.btnSave);
        Button btnCANCEL=myviewm.findViewById(R.id.btnCancel);

        btnSAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String type=EdtType.getText().toString().trim();
                String ammount=EdtAmmount.getText().toString().trim();
                String note=EdtNote.getText().toString().trim();

                if(TextUtils.isEmpty(type)){
                    EdtType.setError("Required Field...");
                    return;
                }

                if(TextUtils.isEmpty(ammount)){
                    EdtAmmount.setError("Required Field...");
                    return;
                }

                int ouramontint = Integer.parseInt(ammount);

                if (TextUtils.isEmpty(note)){
                    EdtNote.setError("Required Field..");
                    return;
                }

                String id= mIncomeDatabase.push().getKey();

                String mDate= DateFormat.getDateInstance().format(new Date());

                Data data = new Data(ouramontint, type, note, id, mDate);

                mIncomeDatabase.child(id).setValue(data);

                Toast.makeText(getActivity(), "Data ADDED", Toast.LENGTH_SHORT).show();
                ftAnimation();
                dialog.dismiss();

            }
        });
        btnCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void expenseDataInsert(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview=inflater .inflate(R.layout.custom_layout_for_insertdata, null);
        mydialog.setView(myview);

        final AlertDialog dialog=mydialog.create();

        dialog.setCancelable(false);

        final EditText ammount=myview.findViewById(R.id.ammount_edt);
        final EditText type=myview.findViewById(R.id.type_edt);
        final EditText note=myview.findViewById(R.id.note_edt);

        Button btnSave=myview.findViewById(R.id.btnSave);
        Button btnCancel=myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmAmmount=ammount.getText().toString().trim();
                String tmType=type.getText().toString().trim();
                String tmNote=note.getText().toString().trim();

                if(TextUtils.isEmpty(tmAmmount)){
                    ammount.setError("Required Fiel...");
                    return;
                }
                int inammount=Integer.parseInt(tmAmmount);
                if (TextUtils.isEmpty(tmType)){
                    type.setError("Required Fiel...");
                    return;
                }
                if(TextUtils.isEmpty(tmNote)){
                    note.setError("Required Fiel...");
                    return;
                }

                String id= mExpenseDatabase.push().getKey();
                String mDate= DateFormat.getDateInstance().format(new Date());

                Data data = new Data(inammount, tmType, tmNote, id, mDate);
                mExpenseDatabase.child(id).setValue(data);

                Toast.makeText(getActivity(), "Data ADDED", Toast.LENGTH_SHORT).show();

                ftAnimation();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}