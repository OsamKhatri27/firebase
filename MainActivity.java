package com.example.innu.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

class Product{
    public int id;
    public String name;
    public String brand;
    public float price;

    public Product(int id, String name, String brand, float price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public Product() {
    }
}

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference simple;
    DatabaseReference multi;
    DatabaseReference complex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);

        db=FirebaseDatabase.getInstance();
        simple=db.getReference("simple");
        multi=db.getReference("multi");
        complex=db.getReference("complex");

        //savesimpledata();
        //savemultidata();
        //savecomplextdata();
        //readsimpledata();
       // readmultidata();
        readcomplexdata();
    }



    //to save the data in firebase

    protected void savesimpledata()
    {
        simple.setValue("Osam");
    }

    protected  void savemultidata(){
        multi.push().setValue("geetika");
        multi.push().setValue("osam khatri");
        multi.push().setValue("saumya");
    }

    protected void savecomplextdata(){
        Product p = new Product(1234,"Samsung Galaxy S10","Samsung",56000.0F);
        complex.setValue(p);
    }


    // to read data from the firebase
    protected void readsimpledata(){
        simple.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.i("myapp",value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    protected void readmultidata(){
        multi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* iterable take a snapshopt of the children node and holding the instances of children node.
                An Iterable is a simple representation of a series of elements that can be iterated over.
                It does not have any iteration state such as a "current element". Instead, it has one method
                 that produces an Iterator.
                 */


                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                /*it retrieve the data from iterable which are holding the instances
                 An Iterator is the object with iteration state. It lets you check if it has more
                      elements using hasNext() and move to the next element (if any) using next().
                 */
                Iterator<DataSnapshot> iterator = iterable.iterator();

                while(iterator.hasNext()) {
                    DataSnapshot ds = iterator.next();
                    String data = ds.getValue().toString();
                    Log.i("myapp",data);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void readcomplexdata(){
        complex.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product p = dataSnapshot.getValue(Product.class);
                Log.i("myapp", p.id+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
