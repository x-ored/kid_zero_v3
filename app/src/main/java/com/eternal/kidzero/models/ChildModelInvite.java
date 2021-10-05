package com.eternal.kidzero.models;

import static com.eternal.kidzero.core.CallbackManager.callCallbak;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.Helper;
import com.eternal.kidzero.enums.InviteStatus;
import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.interfaces.functions.Functions;
import com.eternal.kidzero.ui.helpers.TextFormatUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class ChildModelInvite extends UserModel {
    String name;
    String id;
    String parentId;
    InviteStatus status;

    public ChildModelInvite(){}
    public ChildModelInvite(String UserId, Role Role, String name,String parentId) {
        super(UserId,Role);
        this.name = name;
        this.parentId = parentId;
        setStatus(InviteStatus.wait);
    }
    public String getId() {
        if(id == null) id = FDatabase.getInstance().getDb().child("invites").child(parentId).push().getKey();
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getName()
    {
        return name;
    }
    @Override
    public ChildModelInvite setName(String name) {
        this.name = name;
        return this;
    }
    @Override
    public InviteStatus getStatus() {
        return status;
    }
    @Override
    public ChildModelInvite setStatus(InviteStatus status) {
        this.status = status;
        return this;
    }
    @Override
    public void sendInvite(Functions.Action<String> surcess,Functions.Action<String> canle,Functions.Action<String> waitinviteOnTick) {
        FDatabase.getInstance().addListenerForSingleValueEvent(FDatabase.getInstance().getDb().child("users").child(parentId), parentfound -> {

            if(parentfound.getValue() != null && Objects.requireNonNull(parentfound.getValue(UserModel.class)).getRole().equals(Role.Parent)) {
                DatabaseReference ref = FDatabase.getInstance().getDb().child("invites").child(parentId).child(getId());
                ref.setValue(this).addOnSuccessListener(aVoid -> {
                    CountDownTimer  resendTimer = new CountDownTimer(60*1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            if (waitinviteOnTick != null) {
                                waitinviteOnTick.apply(TextFormatUtil.msToStr(millisUntilFinished));
                            }

                        }
                        public void onFinish() {
                            try {
                                FDatabase.getInstance().removePostEventListener(ref);
                                ref.setValue(null);
                                if (canle != null) {
                                    canle.apply("timeout");
                                }
                            }catch (Exception e){}

                        }
                    }.start();


                    FDatabase.getInstance().addPostEventListener(ref, ChildModelInvite -> {
                        if(ChildModelInvite.getValue() != null) {
                            ChildModelInvite invite = ChildModelInvite.getValue(ChildModelInvite.class);
                            if(invite.getStatus().equals(InviteStatus.ok)){
                                FDatabase.getInstance().removePostEventListener(ref);
                                resendTimer.cancel();
                                ref.setValue(null);
                                if (surcess != null) {
                                    surcess.apply("ok");
                                }
                            }
                        } else {
                            FDatabase.getInstance().removePostEventListener(ref);
                            resendTimer.cancel();
                            if (canle != null) {
                                canle.apply("inviteCanleOrNull");
                            }

                        }},databaseError -> {
                        FDatabase.getInstance().removePostEventListener(ref);
                        resendTimer.cancel();
                        if (canle != null) {
                            canle.apply("databaseError");
                        }
                    });
                }).addOnFailureListener(e -> {
                    ref.setValue(null);
                    if (canle != null) {
                        canle.apply(e.toString());
                    }
                });


            } else {

                if (canle != null) {
                    canle.apply("parentnotfound");
                }
            }
        },databaseError -> {
            if (canle != null) {
                canle.apply("databaseError");
            }
        });



    }

    @Override
    public Task<Void> save() { return FDatabase.getInstance().getDb().child("invites").child(parentId).child(getId()).setValue(this); }
    @Override
    public ChildModelInvite remove() {
        if(id != null)
        FDatabase.getInstance().getDb().child("invites").child(parentId).child(getId()).setValue(null);

        return this;
    }
}
