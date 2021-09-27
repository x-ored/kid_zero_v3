package com.eternal.kidzero;

import static com.eternal.kidzero.core.CallbackManager.callCallbak;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.interfaces.functions.FunctionsP1V;
import com.eternal.kidzero.interfaces.functions.FunctionsP3V;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.models.ParentModel;
import com.eternal.kidzero.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.function.Function;

public class FDatabase {
    public static FDatabase instance;

    public String TAG = "FDatabase";
    public UserModel curentUserData;
    public ParentChilds parentChilds;
    private DatabaseReference mDatabase;
    HashMap<String,ValueEventListener> events;

    public static FDatabase getInstance() {
        if (instance == null) {
            instance = new FDatabase();
        }
        return instance;
    }

    public FDatabase() {
        parentChilds = new ParentChilds();
        addPostEventListener(getDb().child("users").child(FbCore.getInstance().get_user().getPhoneNumber()),dataSnapshot -> {

            curentUserData = dataSnapshot.getValue(UserModel.class);
            if(curentUserData != null) {
                if (curentUserData.getRole() == Role.Parent) {
                    curentUserData = dataSnapshot.getValue(ParentModel.class);
                }
                if (curentUserData.getRole() == Role.Child) {
                    curentUserData = dataSnapshot.getValue(ChildModel.class);
                }

            }
            callCallbak(UserModel.class.getName(), curentUserData);
        },databaseError -> {

        });
    }


    public  DatabaseReference getDb() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return mDatabase;
    }
    public static UserModel curentUser() {
        return FDatabase.getInstance().curentUserData;
    }

    public static ParentChilds getChildManager() {
        if (FDatabase.getInstance().parentChilds == null) {
            FDatabase.getInstance().parentChilds = new ParentChilds();
        }
        return FDatabase.getInstance().parentChilds;
    }


    public void addPostEventListener(DatabaseReference mPostReference, FunctionsP1V<DataSnapshot> onDataChange, FunctionsP1V<DatabaseError> onCancelled) {
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
               // Post post = dataSnapshot.getValue(Post.class);
                // ..
                if (onDataChange != null) {
                    onDataChange.apply(dataSnapshot);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                if (onCancelled != null) {
                    onCancelled.apply(databaseError);
                }

            }
        };
        if(events == null) events  = new HashMap<>();

        if(!events.containsKey(mPostReference.toString()))
        {
            events.put(mPostReference.toString(), postListener);
            mPostReference.addValueEventListener(postListener);
        }
        // [END post_value_event_listener]
    }
    public void removePostEventListener(DatabaseReference mPostReference) {
        if(events == null) events  = new HashMap<>();
        if(events.containsKey(mPostReference.toString()))
        {
            mPostReference.removeEventListener(events.get(mPostReference.toString()));
            events.remove(mPostReference.toString());
        }

    }
}