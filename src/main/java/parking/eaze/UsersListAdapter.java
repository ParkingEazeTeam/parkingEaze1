//parkingEazeTeam

package parking.eaze;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class UsersListAdapter extends ArrayAdapter<User> {
    List<User> users= new ArrayList<User>();
    private TextView name;
    private TextView phoneno;
    private TextView startdate;
    private TextView enddate;
    private Button deleteButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference bookingref;
    public UsersListAdapter(@NonNull Context context, List<User> users) {
        super(context, R.layout.userslist_items,users);
        this.users=users;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = convertView;
        if (customView == null) {
            customView = LayoutInflater.from(getContext()).inflate(R.layout.userslist_items, parent, false);
        }
        final User user= getItem(position);
        name=(TextView) customView.findViewById(R.id.name_user);
        phoneno=(TextView) customView.findViewById(R.id.phoneno_user);
        deleteButton=(Button) customView.findViewById(R.id.delete_user_admin);
        name.setText("Name: "+user.getName());
        phoneno.setText("Phone Number: "+user.getPhonenum());
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User deleteuser= getItem(position);
                firebaseDatabase= FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference().child("Users");
                databaseReference.child(deleteuser.getKey()).removeValue();
                users.remove(deleteuser);
                notifyDataSetChanged();
                bookingref=firebaseDatabase.getReference().child("Bookings");
                Query query= bookingref;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            Booking deleteBookings=snapshot.getValue(Booking.class);
                            if(deleteuser.getKey().equals(deleteBookings.getUserid())){
                                bookingref.child(deleteBookings.getBookingkey()).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        return customView;
    }
    public void setUser(User user) {
        users.add(user);
        notifyDataSetChanged();
    }
    public void removeUser(User user){
        users.remove(user);
        notifyDataSetChanged();
    }
}
