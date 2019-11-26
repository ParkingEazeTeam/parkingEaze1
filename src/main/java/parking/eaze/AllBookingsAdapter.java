//parkingEaze Team
package parking.eaze;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Osama Khalid on 8/2/2017.
 */

public class AllBookingsAdapter  extends ArrayAdapter<Booking>{
    List<Booking> bookings= new ArrayList<Booking>();
    private TextView location;
    private TextView slotno;
    private TextView startdate;
    private TextView enddate;
    private Button deleteButton;
    private String type;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public AllBookingsAdapter(@NonNull Context context, List<Booking> bookings, String type) {
        super(context, R.layout.allbookings_items,bookings);
        this.bookings=bookings;
        this.type=type;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = convertView;
        if (customView == null) {
            customView = LayoutInflater.from(getContext()).inflate(R.layout.allbookings_items, parent, false);
        }
        final Booking booking1= getItem(position);
        location=(TextView) customView.findViewById(R.id.location_admin);
        slotno=(TextView) customView.findViewById(R.id.slotnum_admin);
        startdate=(TextView) customView.findViewById(R.id.startdate_admin);
        enddate=(TextView) customView.findViewById(R.id.enddate_admin);
        deleteButton=(Button) customView.findViewById(R.id.delete_booking_admin);
        if(type.equals("user")){
           deleteButton.setText("Cancel Booking");
        }
        else if(type.equals("admin")){
         deleteButton.setText("Delete Booking");
        }
        location.setText("Location: "+booking1.getLocation());
        slotno.setText("Slot number: "+booking1.getSlotnum());
        startdate.setText("Start date: "+booking1.getStartdate());
        enddate.setText("End date: "+booking1.getEnddate());
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Booking deletebooking= getItem(position);
                firebaseDatabase= FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference().child("Bookings");
                databaseReference.child(deletebooking.getBookingkey()).removeValue();
                bookings.remove(deletebooking);
                notifyDataSetChanged();
            }
        });
        return customView;
    }
    public void setBooking(Booking booking) {
        bookings.add(booking);
        notifyDataSetChanged();
    }

    public void removeBooking(Booking booking){
        bookings.remove(booking);
        notifyDataSetChanged();
    }
}
