package com.eternal.kidzero;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;
import static com.eternal.kidzero.core.CallbackManager.callCallbak;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.core.ParentChildsManager;
import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.interfaces.functions.Functions;
import com.eternal.kidzero.models.QuestModel;
import com.eternal.kidzero.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FDatabase {
    public static FDatabase instance;

    public String TAG = "FDatabase";
    public UserModel curentUserData;
    public ParentChildsManager parentChildsManager;
    private DatabaseReference mDatabase;
    HashMap<String,ValueEventListener> events;



    public static FDatabase getInstance() {
        if (instance == null) {
            instance = new FDatabase();
        }
        return instance;
    }

    public FDatabase() {
        parentChildsManager = new ParentChildsManager();
        addPostEventListener(getDb().child("users").child(FbCore.getInstance().get_user().getPhoneNumber()),dataSnapshot -> {
            if(dataSnapshot.getValue() != null) {
                setCurentUserData(dataSnapshot.getValue(UserModel.class).getUserModel(dataSnapshot));
            }else {
                setCurentUserData(new UserModel().setName("None").setRole(Role.None));
            }

            callCallbak(UserModel.class.getName(), curentUserData);
        },databaseError -> {
            setCurentUserData(new UserModel().setName("None").setRole(Role.None));
            callCallbak(UserModel.class.getName(), curentUserData);
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

    public void setCurentUserData(UserModel curentUserData) {
        this.curentUserData = curentUserData;
        callCallbak("curentUserData",curentUserData);
    }

    public void getCurentUserData(CallbackManager.Callback callback) {
        if(curentUserData!=null) {
            callback.apply( curentUserData);
        }else{
            addCallbak("curentUserData", callback);
        }

    }


    public static ParentChildsManager getParentChildManager() {
        if (FDatabase.getInstance().parentChildsManager == null) {
            FDatabase.getInstance().parentChildsManager = new ParentChildsManager();
        }
        return FDatabase.getInstance().parentChildsManager;
    }


    public void addPostEventListener(DatabaseReference mPostReference, Functions.Action<DataSnapshot> onDataChange, Functions.Action<DatabaseError> onCancelled) {
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