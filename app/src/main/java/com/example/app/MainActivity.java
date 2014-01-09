package com.example.app;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;

/**
 * Sample activity to illustrate usage of fragments efficiently.
 */
public class MainActivity extends FragmentActivity {

    public static final boolean DEBUG = true;
    public static final String TAG = MainActivity.class.getSimpleName();
    /**
     * To know which fragment is currently being displayed.
     */
    boolean replaced = false;
    /**
     * The main fragment that is displayed initially.
     */
    private PlaceholderFragment placeHolderFragment;
    /**
     * The other fragment that will be displayed.
     */
    private ReplacedFragment replacedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button_change);
        if (savedInstanceState == null) {
            if(DEBUG) Log.d(TAG, "New activity created");
            placeHolderFragment = new PlaceholderFragment();
            replacedFragment = new ReplacedFragment();
            //Add the fragment to the activity
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, placeHolderFragment, "place_holder")
                    .commit();
        }else{
            if(DEBUG) Log.d(TAG, "Activity re-created");
            if(DEBUG) Log.d(TAG, "Before : placeHolder : " + placeHolderFragment + " replaced : " + replacedFragment
                    + " replaced : " + replaced);
            // get the values from the savedInstance
            replaced = savedInstanceState.getBoolean("key_replaced");
            // FragmentManager stores the fragments added to the FragmentActivity. When the activity is
            // destroyed and recreated, the fragments can be retrieved 'as is' using findFragmentByTag()
            // or findFragmentById()
            if(replaced){
                replacedFragment = (ReplacedFragment) getSupportFragmentManager().findFragmentByTag("replaced");
            }else{
                placeHolderFragment = (PlaceholderFragment) getSupportFragmentManager().findFragmentByTag("place_holder");
            }
            if(DEBUG) Log.d(TAG, "after : placeHolder : " + placeHolderFragment + " replaced : " + replacedFragment);
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // beginTransaction().replace() operation first removes the fragment inside the container
                // view and then new fragment is added. So the removed fragment no longer exist i.e.
                // removed fragment is null.
                if(!replaced){
                    //the current fragment displayed is not the ReplaceFragment, so we add the replacedFragment
                    if(replacedFragment == null)
                        replacedFragment = new ReplacedFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, replacedFragment, "replaced")
                            .commit();
                    replaced = true;
                }else{
                    if(placeHolderFragment == null)
                        placeHolderFragment = new PlaceholderFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, placeHolderFragment, "place_holder")
                            .commit();
                    replaced = false;
                }
            }
        });

        if(DEBUG) Log.d(TAG, "Current : "  + (replaced ? replacedFragment : placeHolderFragment));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("key_replaced", replaced);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String toString() {
        return TAG;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public static final boolean DEBUG = true;
        public static final String TAG = PlaceholderFragment.class.getSimpleName();

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(DEBUG) Log.d(TAG, "onCreate()");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public String toString() {
            return TAG;
        }
    }

    public static class ReplacedFragment extends Fragment{
        public static final boolean DEBUG = true;
        public static final String TAG = ReplacedFragment.class.getSimpleName();

        public ReplacedFragment(){
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(DEBUG) Log.d(TAG, "onCreate()");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_replaced, container, false);
            return rootView;
        }

        @Override
        public String toString() {
            return TAG;
        }
    }
}
