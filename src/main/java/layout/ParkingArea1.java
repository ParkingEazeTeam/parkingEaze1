//parkingEaze Team
package layout;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import parking.eaze.Booking;
import parking.eaze.R;

public class ParkingArea1 extends Fragment {
    private String location = "ParkingArea1";
    private TextView Heading;
    private EditText dd;
    private EditText mm;
    private EditText yyyy;
    private Spinner startTime;
    private Spinner startmins;
    private Spinner duration;
    private String start;
    private String durationhrs;
    private String minstart;
    private Button slot1;
    private Button slot2;
    private Button slot3;
    private SimpleDateFormat mdformat;
    private Button slot4;
    private String enterstartdate;
    private Button slot5;
    private Date enteredstart=null;
    private Date enteredend=null;
    private Button slot6;
    private Button showslots;
    private boolean slot1flag = false;
    private boolean slot2flag = false;
    private boolean slot3flag = false;
    private boolean slot4flag = false;
    private boolean slot5flag = false;
    private boolean slot6flag = false;
    private int d;
    private int month;
    private int year;
    private int currenthours;
    private int currentmins;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference bookingReference;
    Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_parkingarea, container, false);
        if (getArguments() != null) {
            location = getArguments().getString("location");
        }

              calendar = Calendar.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        bookingReference = firebaseDatabase.getReference().child("Bookings");

        slot1 = (Button) view.findViewById(R.id.ParkingArea1_slot1);
        slot2 = (Button) view.findViewById(R.id.ParkingArea1_slot2);
        slot3 = (Button) view.findViewById(R.id.ParkingArea1_slot3);
        slot4 = (Button) view.findViewById(R.id.ParkingArea1_slot4);
        slot5 = (Button) view.findViewById(R.id.ParkingArea1_slot5);
        slot6 = (Button) view.findViewById(R.id.ParkingArea1_slot6);
        showslots = (Button) view.findViewById(R.id.show_slots);
        dd = (EditText) view.findViewById(R.id.ParkingArea1_start_dd);
        mm = (EditText) view.findViewById(R.id.ParkingArea1_start_mm);
        yyyy = (EditText) view.findViewById(R.id.ParkingArea1_start_yyyy);
        showslots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(dd.getText().toString())) {
                    dd.setError("Enter date");
                    return;
                }
                if (TextUtils.isEmpty(mm.getText().toString())) {
                    mm.setError("Enter month");
                    return;
                }
                if (TextUtils.isEmpty(yyyy.getText().toString())) {
                    yyyy.setError("Enter year");
                    return;
                } else {
                    mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Date currentdate=null;
                    try {
                         currentdate = mdformat.parse(mdformat.format(calendar.getTime()));
                    }
                    catch (Exception c){
                        c.printStackTrace();
                    }
                     enterstartdate=yyyy.getText().toString()+"/"+mm.getText().toString()+"/"+dd.getText().toString()
                            +" "+start+":"+minstart;


                    try {
                        enteredstart = mdformat.parse(enterstartdate);
                        Calendar cal = Calendar.getInstance(); // creates calendar
                        cal.setTime(enteredstart); // sets calendar time/date
                        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+Integer.parseInt(durationhrs));
                        System.out.println("checkff"+ mdformat.format(cal.getTime()));
                        enteredend=cal.getTime();
                    }
                    catch (Exception c){
                        c.printStackTrace();
                    }
                    System.out.println("checkff value="+currentdate.compareTo(enteredstart));
                    if (currentdate.compareTo(enteredstart)==1) {
                        Toast.makeText(getActivity(), "Entered date is less than current date!", Toast.LENGTH_SHORT).show();
                        dd.setText("");
                        mm.setText("");
                        yyyy.setText("");
                        return;
                    }
                }
                slot1.setVisibility(View.VISIBLE);
                slot2.setVisibility(View.VISIBLE);
                slot3.setVisibility(View.VISIBLE);
                slot4.setVisibility(View.VISIBLE);
                slot5.setVisibility(View.VISIBLE);
                slot6.setVisibility(View.VISIBLE);
                slot6.setBackgroundColor(Color.GREEN);
                slot2.setBackgroundColor(Color.GREEN);
                slot3.setBackgroundColor(Color.GREEN);
                slot4.setBackgroundColor(Color.GREEN);
                slot5.setBackgroundColor(Color.GREEN);
                slot1.setBackgroundColor(Color.GREEN);
                slot1flag = false;
                slot2flag = false;
                slot3flag = false;
                slot4flag = false;
                slot5flag = false;
                slot6flag = false;
                checkBookedSlots();
            }

        });
        slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBookedSlots();
                if (slot1flag == false) {
                    String key= bookingReference.push().getKey();
                    Booking booking = new Booking(FirebaseAuth.getInstance().getCurrentUser().getUid(), "slot1", location,enterstartdate,mdformat.format(enteredend),key);
                    bookingReference.child(key).setValue(booking);
                    Toast.makeText(getActivity(),"Slot1 booked!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Slot1 is already booked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBookedSlots();
                if (slot2flag == false) {
                    String key= bookingReference.push().getKey();
                    Booking booking = new Booking(FirebaseAuth.getInstance().getCurrentUser().getUid(), "slot2", location,enterstartdate,mdformat.format(enteredend),key);
                    bookingReference.child(key).setValue(booking);
                    Toast.makeText(getActivity(),"Slot2 booked!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Slot2 is already booked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        slot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBookedSlots();
                if (slot3flag == false) {
                    String key= bookingReference.push().getKey();
                    Booking booking = new Booking(FirebaseAuth.getInstance().getCurrentUser().getUid(), "slot3", location,enterstartdate,mdformat.format(enteredend),key);
                    bookingReference.child(key).setValue(booking);
                    Toast.makeText(getActivity(),"Slot3 booked",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Slot3 is already booked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        slot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBookedSlots();
                if (slot4flag == false) {
                    String key= bookingReference.push().getKey();
                    Booking booking = new Booking(FirebaseAuth.getInstance().getCurrentUser().getUid(), "slot4", location,enterstartdate,mdformat.format(enteredend),key);
                    bookingReference.child(key).setValue(booking);
                    Toast.makeText(getActivity(),"Slot4 booked",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Slot4 is already booked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        slot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBookedSlots();
                if (slot5flag == false) {
                    String key= bookingReference.push().getKey();
                    Booking booking = new Booking(FirebaseAuth.getInstance().getCurrentUser().getUid(), "slot5", location,enterstartdate,mdformat.format(enteredend),key);
                    bookingReference.child(key).setValue(booking);
                    Toast.makeText(getActivity(),"Slot5 booked",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Slot5 is already booked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        slot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBookedSlots();
                if (slot6flag == false) {
                    String key= bookingReference.push().getKey();
                    Booking booking = new Booking(FirebaseAuth.getInstance().getCurrentUser().getUid(), "slot6", location,enterstartdate,mdformat.format(enteredend),key);
                    bookingReference.child(key).setValue(booking);
                    Toast.makeText(getActivity(),"Slot6 booked",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Slot6 is already booked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        startTime = (Spinner) view.findViewById(R.id.ParkingArea1_start_time);
        startmins = (Spinner) view.findViewById(R.id.ParkingArea1_start_mins);
        duration = (Spinner) view.findViewById(R.id.duration);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adaptermins = ArrayAdapter.createFromResource(getActivity(), R.array.min, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterduration = ArrayAdapter.createFromResource(getActivity(), R.array.durationhrs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startmins.setAdapter(adaptermins);
        startTime.setAdapter(adapter);
        duration.setAdapter(adapterduration);
        start = startTime.getSelectedItem().toString();
        minstart = startmins.getSelectedItem().toString();
        durationhrs = duration.getSelectedItem().toString();
        startTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                start = startTime.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startmins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                minstart = startmins.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                durationhrs = duration.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
    public void checkBookedSlots(){
        Query query = bookingReference;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Booking checkbook = snapshot.getValue(Booking.class);
                    if (checkbook.getLocation().equals(location)) {
                        checkSlots(checkbook);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void checkSlots(Booking dbBook) {
        Date dbstartdate=null;
        Date dbenddate=null;
        try {
             dbstartdate = mdformat.parse(dbBook.getStartdate());
             dbenddate=mdformat.parse(dbBook.getEnddate());
        }
        catch (Exception e){
            e.printStackTrace();
        }
//       int dbstart = Integer.parseInt(dbBook.getStarttime());
//        int dbend= Integer.parseInt(dbBook.getDuration())+dbstart;
//        int end = Integer.parseInt(durationhrs)+Integer.parseInt(start);
//        if (dbBook.getDate().equals(dd.getText().toString()) && dbBook.getMonth().equals(mm.getText().toString()) && dbBook.getYear().equals(yyyy.getText().toString())) {
//            System.out.println("checkff dbstart="+dbstart+" dbend=" +dbend+" start="+Integer.parseInt(start)+" end="+end);
//            if (dbstart > Integer.parseInt(start) && dbstart < end) {
//                System.out.println("checkff slot="+dbBook.getSlotnum());
//               changeSlotColor(dbBook.getSlotnum());
//            }
//            else if(dbstart<Integer.parseInt(start) && dbend>Integer.parseInt(start)){
//                changeSlotColor(dbBook.getSlotnum());
//            }
//            else if(dbstart>Integer.parseInt(start) && dbend<end){
//                changeSlotColor(dbBook.getSlotnum());
//            }
//            else if(dbstart<Integer.parseInt(start) && dbend>end){
//                changeSlotColor(dbBook.getSlotnum());
//            }
//            else if(dbstart==Integer.parseInt(start)){
//                changeSlotColor(dbBook.getSlotnum());
//            }
//            else if(dbend==Integer.parseInt(start)){
//                if(Integer.parseInt(dbBook.getStartmins())>Integer.parseInt(minstart)){
//                    changeSlotColor(dbBook.getSlotnum());
//                }
//            }
//            else if(dbstart==end){
//               if(Integer.parseInt(minstart)>Integer.parseInt(dbBook.getStartmins())){
//                    changeSlotColor(dbBook.getSlotnum());
//                }
//            }
//        }
        if(dbstartdate.compareTo(enteredstart)==0){
            changeSlotColor(dbBook.getSlotnum());
        }
        else if(dbstartdate.compareTo(enteredstart)==1 && dbstartdate.compareTo(enteredend)==-1){
            changeSlotColor(dbBook.getSlotnum());
        }
        else if(dbstartdate.compareTo(enteredstart)==-1 && dbenddate.compareTo(enteredstart)==1){
            changeSlotColor(dbBook.getSlotnum());
        }
        else if(dbstartdate.compareTo(enteredstart)==1 && dbenddate.compareTo(enteredend)==-1){
            changeSlotColor(dbBook.getSlotnum());
        }
}
    public void changeSlotColor(String slotnum){
        if (slotnum.equals("slot1")) {
            slot1.setBackgroundColor(Color.RED);
            slot1flag = true;
        }
        else if (slotnum.equals("slot2")) {
            slot2.setBackgroundColor(Color.RED);

            slot2flag = true;
        }
        else if (slotnum.equals("slot3")) {
            slot3.setBackgroundColor(Color.RED);
            slot3flag = true;
        }
        else if (slotnum.equals("slot4")) {
            slot4.setBackgroundColor(Color.RED);
            slot4flag = true;

        }
        else if (slotnum.equals("slot5")) {
            slot5.setBackgroundColor(Color.RED);
            slot5flag = true;
        }
        else if (slotnum.equals("slot6")) {
            slot6.setBackgroundColor(Color.RED);
            slot6flag = true;
        }
        else {
            slot6.setBackgroundColor(Color.GREEN);
            slot2.setBackgroundColor(Color.GREEN);
            slot3.setBackgroundColor(Color.GREEN);
            slot4.setBackgroundColor(Color.GREEN);
            slot5.setBackgroundColor(Color.GREEN);
            slot1.setBackgroundColor(Color.GREEN);

        }
    }
}
