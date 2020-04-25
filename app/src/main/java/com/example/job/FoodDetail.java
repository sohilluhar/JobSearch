//package com.example.job;
//
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
//import com.google.android.material.appbar.CollapsingToolbarLayout;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//import com.stepstone.apprating.AppRatingDialog;
//import com.stepstone.apprating.listener.RatingDialogListener;
//
//import java.util.Arrays;
//
//import io.kustrad.dakaar.Database.DataBase;
//import io.kustrad.dakaar.Model.Food;
//import io.kustrad.dakaar.Model.Order;
//import io.kustrad.dakaar.Model.Rating;
//
//public class FoodDetail extends AppCompatActivity implements RatingDialogListener {
//
//    TextView food_name, food_price, food_discription;
//    ImageView food_image;
//    CollapsingToolbarLayout collapsingToolbarLayout;
//    FloatingActionButton btnCart, btnRating;
//    ElegantNumberButton numberButton;
//    String foodId = "";
//    RatingBar ratingBar;
//
//    FirebaseDatabase database;
//    DatabaseReference foods;
//    DatabaseReference ratingTbl;
//
//
//    Food currentFood;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_food_detail);
//
//        //Firebase
//        database = FirebaseDatabase.getInstance();
//        foods = database.getReference("Foods");
//        ratingTbl = database.getReference("Rating");
//
//        //Init view
//        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
//        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
//        btnRating = (FloatingActionButton) findViewById(R.id.btn_rating);
//        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//
//        btnRating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showRatingDailog();
//            }
//        });
//
//        btnCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DataBase(getBaseContext()).addToCart(new Order(
//                        foodId,
//                        currentFood.getName(),
//                        numberButton.getNumber(),
//                        currentFood.getPrice(),
//                        currentFood.getDiscount()
//
//                ));
//
//                Toast.makeText(FoodDetail.this, "Added To Cart", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        food_discription = (TextView) findViewById(R.id.food_description);
//        food_name = (TextView) findViewById(R.id.food_name);
//        food_price = (TextView) findViewById(R.id.food_price);
//        food_image = (ImageView) findViewById(R.id.image_food);
//
//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
//        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedAppbar);
//
//        //Get Food Id From Intent
//        if (getIntent() != null)
//            foodId = getIntent().getStringExtra("FoodId");
//        if (!foodId.isEmpty()) {
//            if (Common.isConnectedToInternet(getBaseContext())) {
//                getDetailFood(foodId);
//                getRatingFood(foodId);
//            } else {
//                Toast.makeText(FoodDetail.this, "Please check your connection !!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//
//    }
//
//    private void getRatingFood(String foodId) {
//
//        com.google.firebase.database.Query foodRating = ratingTbl.orderByChild("foodId").equalTo(foodId);
//
//        foodRating.addValueEventListener(new ValueEventListener() {
//            int count = 0, sum = 0;
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Rating item = postSnapshot.getValue(Rating.class);
//                    sum += Integer.parseInt(item.getRateValue());
//                    count++;
//                }
//                if (count != 0) {
//                    float average = sum / count;
//                    ratingBar.setRating(average);
//                }
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
//    private void showRatingDailog() {
//
//        new AppRatingDialog.Builder()
//                .setPositiveButtonText("Submit")
//                .setNegativeButtonText("Cancel")
//                .setNoteDescriptions(Arrays.asList("Very Bad", "Not Good", "Quite ok", "Very Good", "Excellent"))
//                .setDefaultRating(1)
//                .setTitle("Rate this food")
//                .setDescription("Pelease select some stars and give your feedback")
//                .setTitleTextColor(R.color.colorPrimary)
//                .setDescriptionTextColor(R.color.colorPrimary)
//                .setHint("Plese write your comment here...")
//                .setHintTextColor(R.color.colorAccent)
//                .setCommentTextColor(android.R.color.white)
//                .setCommentBackgroundColor(R.color.colorPrimaryDark)
//                .setWindowAnimation(R.style.RatingDailogFadeAnim)
//                .create(FoodDetail.this)
//                .show();
//    }
//
//
//    private void getDetailFood(String foodId) {
//
//        foods.child(foodId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                currentFood = dataSnapshot.getValue(Food.class);
//
//                //set Image
//                Picasso.with(getBaseContext()).load(currentFood.getImage())
//                        .into(food_image);
//
//                collapsingToolbarLayout.setTitle(currentFood.getName());
//
//                food_price.setText(currentFood.getPrice());
//
//                food_name.setText(currentFood.getName());
//
//                food_discription.setText(currentFood.getDescription());
//                {
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onPositiveButtonClicked(int value, String comments) {
//
//        //Get rating and upload to Firebase
//        final Rating rating = new Rating(Common.currentUser.getPhone(),
//                foodId,
//                String.valueOf(value),
//                comments);
//        ratingTbl.child(Common.currentUser.getPhone()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.child(Common.currentUser.getPhone()).exists()) {
//                    //Remove old value (you can delete or let it be - useless function :D)
//                    ratingTbl.child(Common.currentUser.getPhone()).removeValue();
//                    //Update new value
//                    ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);
//                } else {
//                    //Update new value
//                    ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);
//                }
//                Toast.makeText(FoodDetail.this, "Thank you for submit rating !!!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onNegativeButtonClicked() {
//
//    }
//}
