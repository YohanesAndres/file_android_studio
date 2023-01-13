package com.if5a.chattogether;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.if5a.chattogether.databinding.ActivityMainBinding;
import com.if5a.chattogether.databinding.ItemChatBinding;
import com.if5a.chattogether.models.ChatMessage;
import com.if5a.chattogether.models.Data;
import com.if5a.chattogether.models.Sender;
import com.if5a.chattogether.models.User;
import com.if5a.chattogether.models.ViewData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;

    private String mUsername;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";

    private LinearLayoutManager mLinearLayoutManager;

    private FirebaseAuth mAuth;
    private DatabaseReference mRoot, mRef;
    private FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder> mFirebaseAdapter;
    private String userId;

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    final Uri uri = result.getData().getData();
                    Log.d(TAG, "Uri : " + uri.toString());

                    ChatMessage tempMessage = new ChatMessage(null, userId, LOADING_IMAGE_URL);
                    mRoot.child("messages").push()
                            .setValue(tempMessage, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        String key = databaseReference.getKey();
                                        StorageReference storageReference = FirebaseStorage.getInstance()
                                                .getReference(mAuth.getCurrentUser().getUid())
                                                .child(key)
                                                .child(uri.getLastPathSegment());

                                        putImageInStorage(storageReference, uri, key);
                                    } else {
                                        Log.w(TAG, "Unable to write database", databaseError.toException());
                                    }
                                }
                            });
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(binding.getRoot());

            FirebaseMessaging.getInstance().subscribeToTopic("messages");

            userId = mAuth.getCurrentUser().getUid();
            mRoot = FirebaseDatabase.getInstance().getReference();
            mRef = mRoot.child("users").child(userId);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    mUsername = user.getFullName();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mUsername = "Anonymous";
                }
            });

            binding.rvChat.setItemAnimator(null);

            mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);
            mLinearLayoutManager.setStackFromEnd(true);
            binding.rvChat.setLayoutManager(mLinearLayoutManager);

            SnapshotParser<ChatMessage> parser = new SnapshotParser<ChatMessage>() {
                @NonNull
                @Override
                public ChatMessage parseSnapshot(@NonNull DataSnapshot snapshot) {
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if (chatMessage != null) {
                        chatMessage.setId(snapshot.getKey());
                    }
                    return chatMessage;
                }
            };

            mRef = mRoot.child("messages");
            FirebaseRecyclerOptions<ChatMessage> options = new FirebaseRecyclerOptions.Builder<ChatMessage>()
                    .setQuery(mRef, parser)
                    .build();

            mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final ChatViewHolder holder, int position, @NonNull final ChatMessage model) {
                    mRoot.child("users").child(model.getSender()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);

                            if (model.getSender().equals(userId)) {
                                holder.itemChatBinding.llReceiver.setVisibility(View.GONE);
                                holder.itemChatBinding.llSender.setVisibility(View.VISIBLE);

                                if (model.getText() != null) {
                                    holder.itemChatBinding.tvMessageSender.setText(model.getText());
                                    holder.itemChatBinding.tvMessageSender.setVisibility(View.VISIBLE);
                                    holder.itemChatBinding.ivMessageSender.setVisibility(View.GONE);
                                } else if (model.getImageUrl() != null) {
                                    String imageUrl = model.getImageUrl();
                                    if (imageUrl.startsWith("gs://")) {
                                        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    String downloadUrl = task.getResult().toString();
                                                    Glide.with(holder.itemChatBinding.ivMessageSender.getContext())
                                                            .load(downloadUrl)
                                                            .into(holder.itemChatBinding.ivMessageSender);
                                                } else {
                                                    Log.w(TAG, "Getting download url failed!", task.getException());
                                                }
                                            }
                                        });
                                    } else {
                                        Glide.with(holder.itemChatBinding.ivMessageSender.getContext())
                                                .load(model.getImageUrl())
                                                .into(holder.itemChatBinding.ivMessageSender);
                                    }
                                    holder.itemChatBinding.ivMessageSender.setVisibility(View.VISIBLE);
                                    holder.itemChatBinding.tvMessageSender.setVisibility(View.GONE);
                                }

                                holder.itemChatBinding.tvSender.setText(user.getFullName());

                                ColorGenerator generator = ColorGenerator.Companion.getMATERIAL();
                                TextDrawable textDrawable = TextDrawable.builder()
                                        .beginConfig()
                                        .width(50)
                                        .height(50)
                                        .endConfig()
                                        .buildRound(getInitialName(user.getFullName().toUpperCase()), generator.getColor(model.getSender()));

                                holder.itemChatBinding.ivSender.setImageDrawable(textDrawable);
                            } else {
                                holder.itemChatBinding.llReceiver.setVisibility(View.VISIBLE);
                                holder.itemChatBinding.llSender.setVisibility(View.GONE);

                                if (model.getText() != null) {
                                    holder.itemChatBinding.tvMessageReceiver.setText(model.getText());
                                    holder.itemChatBinding.tvMessageReceiver.setVisibility(View.VISIBLE);
                                    holder.itemChatBinding.ivMessageReceiver.setVisibility(View.GONE);
                                } else if (model.getImageUrl() != null) {
                                    String imageUrl = model.getImageUrl();
                                    if (imageUrl.startsWith("gs://")) {
                                        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    String downloadUrl = task.getResult().toString();
                                                    Glide.with(holder.itemChatBinding.ivMessageReceiver.getContext())
                                                            .load(downloadUrl)
                                                            .into(holder.itemChatBinding.ivMessageReceiver);
                                                } else {
                                                    Log.w(TAG, "Getting download url failed!", task.getException());
                                                }
                                            }
                                        });
                                    } else {
                                        Glide.with(holder.itemChatBinding.ivMessageReceiver.getContext())
                                                .load(model.getImageUrl())
                                                .into(holder.itemChatBinding.ivMessageReceiver);
                                    }
                                    holder.itemChatBinding.ivMessageReceiver.setVisibility(View.VISIBLE);
                                    holder.itemChatBinding.tvMessageReceiver.setVisibility(View.GONE);
                                }

                                holder.itemChatBinding.tvReceiver.setText(user.getFullName());

                                ColorGenerator generator = ColorGenerator.Companion.getMATERIAL();
                                TextDrawable textDrawable = TextDrawable.builder()
                                        .beginConfig()
                                        .width(50)
                                        .height(50)
                                        .endConfig()
                                        .buildRound(getInitialName(user.getFullName().toUpperCase()), generator.getColor(model.getSender()));

                                holder.itemChatBinding.ivReceiver.setImageDrawable(textDrawable);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @NonNull
                @Override
                public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//                    return new ChatViewHolder(inflater.inflate(R.layout.item_chat, parent, false));
                    return new ChatViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
                }

                @Override
                public void onDataChanged() {
                    super.onDataChanged();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            };

            mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    int chatMessageCount = mFirebaseAdapter.getItemCount();
                    int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                    if (lastVisiblePosition == -1 ||
                            (positionStart >= (chatMessageCount - 1) &&
                                    lastVisiblePosition == (positionStart - 1))) {
                        binding.rvChat.scrollToPosition(positionStart);
                    }
                }
            });

            binding.rvChat.setAdapter(mFirebaseAdapter);

            binding.etMessage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().length() > 0) {
                        binding.btnSend.setEnabled(true);
                    } else {
                        binding.btnSend.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            binding.btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatMessage chatMessage = new ChatMessage(binding.etMessage.getText().toString(),
                            userId,
                            null);
                    mRoot.child("messages").push().setValue(chatMessage);
                    binding.etMessage.setText("");

                    Data data = new Data(mUsername, chatMessage.getText(), userId);
                    Sender sender = new Sender(data, "/topics/messages");
                    sendNotification(sender);
                }
            });

            binding.ivAddMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    activityResultLauncher.launch(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onPause() {
        mFirebaseAdapter.stopListening();
        super.onPause();
    }

    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key) {
        storageReference.putFile(uri).addOnCompleteListener(MainActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().getMetadata().getReference().getDownloadUrl()
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        ChatMessage chatMessage = new ChatMessage(null, userId, task.getResult().toString());
                                        mRoot.child("messages").child(key).setValue(chatMessage);

                                        Data data = new Data(mUsername, "Image Message", userId, task.getResult().toString());
                                        Sender sender = new Sender(data, "/topics/messages");
                                        sendNotification(sender);
                                    }
                                }
                            });
                } else {
                    Log.w(TAG, "Image upload task failed!", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_signout) {
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getInitialName(String fullName) {
        String splitName[] = fullName.split("\\s+");
        int splitCount = splitName.length;

        if (splitCount == 1) {
            return "" + fullName.charAt(0) + fullName.charAt(0);
        } else {
            int firstSpace = fullName.indexOf(" ");
            String firstName = fullName.substring(0, firstSpace);

            int lastSpace = fullName.lastIndexOf(" ");
            String lastName = fullName.substring(lastSpace + 1);

            return "" + firstName.charAt(0) + lastName.charAt(0);
        }
    }

    private void sendNotification(Sender sender) {
        APIService api = Utility.getmRetrofit().create(APIService.class);
        Call<ViewData> call = api.sendNotification(sender);
        call.enqueue(new Callback<ViewData>() {
            @Override
            public void onResponse(Call<ViewData> call, Response<ViewData> response) {
                if (response.code() == 200) {
                    System.out.println("Response : " + response.body().getMessage_id());
                    if (response.body().getMessage_id() != null) {
//                        Toast.makeText(MainActivity.this, "Pesan berhasil dikirim!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Pesan gagal dikirim!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewData> call, Throwable t) {
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
