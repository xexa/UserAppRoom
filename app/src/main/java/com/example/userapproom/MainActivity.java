package com.example.userapproom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_USER_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private UserViewModel userViewModel;

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private UserAdapter userAdapter;
    private RelativeLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab);
        parentLayout = findViewById(R.id.parent_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                //update recycler view here
                userAdapter.setUsers(users);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                userViewModel.delete(userAdapter.getUserAt(viewHolder.getAdapterPosition()));

                Toast.makeText(MainActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(MainActivity.this, AddEditUserActivity.class);
                Toast.makeText(MainActivity.this, user.getName() + " clicked", Toast.LENGTH_SHORT).show();

                intent.putExtra(AddEditUserActivity.EXTRA_ID, user.getId());
                intent.putExtra(AddEditUserActivity.EXTRA_NAME, user.getName());
                intent.putExtra(AddEditUserActivity.EXTRA_PHONE, user.getPhone());
                intent.putExtra(AddEditUserActivity.EXTRA_EMAIL, user.getEmail());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditUserActivity.class);
                startActivityForResult(intent, NEW_USER_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String name = data.getStringExtra(AddEditUserActivity.EXTRA_NAME);
            String phone = data.getStringExtra(AddEditUserActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditUserActivity.EXTRA_EMAIL);


            User user = new User(name, phone, email);

            userViewModel.insert(user);

            Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditUserActivity.EXTRA_ID, -1);

            if (id == -1){
                Toast.makeText(this, "User cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditUserActivity.EXTRA_NAME);
            String phone = data.getStringExtra(AddEditUserActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditUserActivity.EXTRA_EMAIL);

            User user = new User(name,phone,email);

            user.setId(id);

            userViewModel.update(user);

            Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "User not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
