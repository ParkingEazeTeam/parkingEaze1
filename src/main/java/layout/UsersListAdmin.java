//parkingEaze Team
package layout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import parking.eaze.R;
import parking.eaze.User;
import parking.eaze.UsersListAdapter;

public class UsersListAdmin extends Fragment {
    private ListView mylistview;
    private UsersListAdapter myadapter;
    private ChildEventListener childEventListener;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private List<User> users= new ArrayList<User>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view=inflater.inflate(R.layout.fragment_users_list_admin, container, false);
        mylistview=(ListView) view.findViewById(R.id.users_list_admin);
        myadapter= new UsersListAdapter(getActivity(),users);
        mylistview.setAdapter(myadapter);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Users");
        childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User adduser=dataSnapshot.getValue(User.class);
                users.add(adduser);
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User deleteuser=dataSnapshot.getValue(User.class);
                System.out.println("checkff userid="+users.contains(deleteuser));

                //myadapter.removeUser(deleteuser);
                users.remove(deleteuser);
                for(int i=0;i<users.size();i++) {


                    System.out.println("har bar "+users.get(i).getKey().equals(deleteuser.getKey()));
                    if(users.get(i).getKey().equals(deleteuser.getKey())){
                        System.out.println(deleteuser.getKey()+"xcvb");
                        users.remove(i);
                    }

                }


                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(childEventListener);
        return view;
    }

}
