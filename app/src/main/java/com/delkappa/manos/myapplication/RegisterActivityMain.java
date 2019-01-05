package com.delkappa.manos.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivityMain extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mFirstName;
    private EditText mName;
    private EditText mLogin;
    private EditText mPhoneNumber;

    private View mProgressView;
    private View mLoginFormView;
    private Spinner mDepartmentSpinnerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String TAG;

    private String employeesCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        // Set up the login form.

        TAG = "registerMail";
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mFirstName = findViewById(R.id.userFirstName);
        mName = findViewById(R.id.userName);
        mLogin = findViewById(R.id.login);
        mPhoneNumber = findViewById(R.id.phoneNumber);

        mAuth = FirebaseAuth.getInstance();
        // mDepartmentSpinnerView = findViewById(R.id.spinner);
        mDatabase = FirebaseDatabase.getInstance().getReference("Departments");
        // mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();
                // employeesCounter = FirebaseDatabase.getInstance().getReference("employeesCounter").getV;
                for (DataSnapshot departmentSnapshot: dataSnapshot.getChildren()) {
                    String departmentName = departmentSnapshot.child("departmentName").getValue(String.class);
                    areas.add(departmentName);
                }

                mDepartmentSpinnerView = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(RegisterActivityMain.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mDepartmentSpinnerView.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("employeesCounter");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG,"before");
                employeesCounter = dataSnapshot.getValue(String.class);
                Log.i(TAG,"after");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void register(View view) {
        // String userID = "";
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String firstName = mFirstName.getText().toString();
        final String name = mName.getText().toString();
        final String login = mLogin.getText().toString();
        final String phoneNumber = mPhoneNumber.getText().toString();
        final String department = mDepartmentSpinnerView.getSelectedItem().toString();
        /*
            private EditText mFirstName;
    private EditText mName;
    private EditText mLogin;
    private EditText mPhoneNumber;
    */

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // mDatabase = FirebaseDatabase.getInstance().getReference("employeesCounter");
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    int value = Integer.parseInt(employeesCounter);
                    Log.i(TAG, String.valueOf(value));
                    value = value+1;
                    String userID = "user"+String.valueOf(value);
                    // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("employeesCounter");
                    FirebaseDatabase.getInstance().getReference("employeesCounter").setValue(String.valueOf(value));
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(department);
                    // ref.setValue(userID);
                    /*
                    ref.child(userID).child("email").setValue(email);
                    ref.child(userID).child("login").setValue(login);
                    ref.child(userID).child("password").setValue(password);
                    ref.child(userID).child("phoneNumber").setValue(phoneNumber);
                    ref.child(userID).child("status").setValue("able");
                    ref.child(userID).child("userFirstName").setValue(firstName);
                    ref.child(userID).child("userName").setValue(name);
                    */
                    Map<String, Object> userToAdd = new HashMap<>();
                    userToAdd.put(userID, new User(email, login, password, phoneNumber, "able", firstName, name));

                    ref.updateChildren(userToAdd);
                    // mDatabase.child(department).child("0001").child("userID").setValue("user0001");
                    /*
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // employeesCounter = FirebaseDatabase.getInstance().getReference("employeesCounter").getV;
                            // employeesCounter = dataSnapshot.getValue(String.class);
                            employeesCounter = dataSnapshot.child("employeesCounter").getValue(String.class);
                            int value = Integer.parseInt(employeesCounter);
                            Log.i(TAG, String.valueOf(value));
                            value = value+1;
                            String userID = "user"+String.valueOf(value);
                            // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("employeesCounter");
                            FirebaseDatabase.getInstance().getReference("employeesCounter").setValue(String.valueOf(value));
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(department);
                            ref.push().setValue(userID);
                            ref.child(userID).child("email").setValue(email);
                            ref.child(userID).child("login").setValue(login);
                            ref.child(userID).child("password").setValue(password);
                            ref.child(userID).child("phoneNumber").setValue(phoneNumber);
                            ref.child(userID).child("status").setValue("able");
                            ref.child(userID).child("userFirstName").setValue(firstName);
                            ref.child(userID).child("userName").setValue(name);
                            //dataSnapshot.child("employeesCounter").setValue(userID);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                    */
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivityMain.this, "Register failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

                // [START_EXCLUDE]
                // hideProgressDialog();
                // [END_EXCLUDE]
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            /*
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);

            findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());
            */
            Log.i("USERUILOG","user different de null");
            Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
            startActivity(intent);
            finish();
        } else {
            /*
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
            */
            Log.i("USERUILOG","user egal a null");
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivityMain.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

