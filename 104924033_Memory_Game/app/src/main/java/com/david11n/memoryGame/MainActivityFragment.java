// MainActivityFragment.java
// Contains the Flag Quiz logic
package com.david11n.memoryGame;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivityFragment extends Fragment {
   // String used when logging error messages
   private static final String TAG = "MemoryGame Activity";

   private static final int GROUPS_IN_QUIZ = 1;

   private List<String> fileNameList; // element file names
   private List<String> quizElementsList; // elements in current quiz
   private Set<String> elementGroupSet; // element groups in current quiz
   private String correctAnswer; // correct element for the current element image
   private int totalGuesses; // number of guesses made
   private int correctAnswers; // number of correct guesses
   private int guessRows; // number of rows displaying guess Buttons
   private SecureRandom random; // used to randomize the quiz
   private Handler handler; // used to delay loading next element image
   private Animation shakeAnimation; // animation for incorrect guess

   private LinearLayout quizLinearLayout; // layout that contains the quiz
   private TextView questionNumberTextView; // shows current question #
//   private ImageView elementImageView; // displays an element
   private LinearLayout[] guessLinearLayouts; // rows of answer Buttons
   private TextView answerTextView; // displays correct answer
   private TextView scoreTextView; // displays the current score
//   private Button exitButton; // exit button

   // configures the MainActivityFragment when its View is created
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
      View view =
         inflater.inflate(R.layout.fragment_main, container, false);

      fileNameList = new ArrayList<>();
      quizElementsList = new ArrayList<>();
      random = new SecureRandom();
      handler = new Handler();

      // load the shake animation that's used for incorrect answers`
      shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
         R.anim.incorrect_shake);
      shakeAnimation.setRepeatCount(3); // animation repeats 3 times

      // get references to GUI components
      quizLinearLayout =
              view.findViewById(R.id.quizLinearLayout);
      questionNumberTextView =
              view.findViewById(R.id.questionNumberTextView);
//      elementImageView = view.findViewById(R.id.elementImageView);
      guessLinearLayouts = new LinearLayout[5];
      guessLinearLayouts[0] =
              view.findViewById(R.id.row1LinearLayout);
      guessLinearLayouts[1] =
              view.findViewById(R.id.row2LinearLayout);
      guessLinearLayouts[2] =
              view.findViewById(R.id.row3LinearLayout);
      guessLinearLayouts[3] =
              view.findViewById(R.id.row4LinearLayout);
      guessLinearLayouts[4] =
              view.findViewById(R.id.row5LinearLayout);
//      answerTextView = view.findViewById(R.id.answerTextView);
//      scoreTextView = view.findViewById(R.id.scoreTextView);
//      exitButton = view.findViewById(R.id.exitButton);      // add the exit button

      // configure listeners for the guess Buttons
      for (LinearLayout row : guessLinearLayouts) {
         for (int column = 0; column < row.getChildCount(); column++) {
            ImageButton button = (ImageButton) row.getChildAt(column);
            button.setOnClickListener(guessButtonListener);
         }
      }

      // set questionNumberTextView's text
//      questionNumberTextView.setText(getString(R.string.question, 1, GROUPS_IN_QUIZ));
//      scoreTextView.setText("_");

      return view; // return the fragment's view for display
   }

   // update guessRows based on value in SharedPreferences
   public void updateGuessRows(SharedPreferences sharedPreferences) {
      // get the number of guess buttons that should be displayed
      String choices =
              sharedPreferences.getString(MemoryGameActivity.CHOICES, null);
      guessRows = Integer.parseInt(choices) / 6;

      // hide all guess button LinearLayouts
      for (LinearLayout layout : guessLinearLayouts)
         layout.setVisibility(View.GONE);

      // display appropriate guess button LinearLayouts
      for (int row = 0; row < guessRows; row++)
         guessLinearLayouts[row].setVisibility(View.VISIBLE);
   }

   // update element groups for quiz based on values in SharedPreferences
   public void updateRegions(SharedPreferences sharedPreferences) {
      elementGroupSet =
              sharedPreferences.getStringSet(MemoryGameActivity.GROUPS, null);
   }

   // set up and start the next quiz
   public void resetQuiz() {
      // use AssetManager to get image file names for enabled regions
      AssetManager assets = getActivity().getAssets();
      fileNameList.clear(); // empty list of image file names

      try {
         // loop through each group
         for (String region : elementGroupSet) {
            // get a list of all flag image files in this region
            String[] paths = assets.list(region);

            for (String path : paths) {
               fileNameList.add(path.replace(".png", ""));
            }
         }
      }
      catch (IOException exception) {
         Log.e(TAG, "Error loading image file names", exception);
      }

      correctAnswers = 0; // reset the number of correct answers made
      totalGuesses = 0; // reset the total number of guesses the user made
      quizElementsList.clear(); // clear prior list of quiz countries

      int elementCounter = 1;
      int numberOfElements = fileNameList.size();

      // add GROUPS_IN_QUIZ random file names to the quizElementsList
      while (elementCounter <= GROUPS_IN_QUIZ) {
         int randomIndex = random.nextInt(numberOfElements);

         // get the random file name
         String filename = fileNameList.get(randomIndex);

         // if the element is enabled and it hasn't already been chosen
         if (!quizElementsList.contains(filename)) {
            quizElementsList.add(filename); // add the file to the list
            ++elementCounter;
         }
      }

      loadNextElement(); // start the quiz by loading the first element
   }

   // after the user guesses a correct element, load the next element
   private void loadNextElement() {
      // get file name of the next flag and remove it from the list
      String nextImage = quizElementsList.remove(0);
      correctAnswer = nextImage; // update the correct answer
//      answerTextView.setText(""); // clear answerTextView

      // display current question number and current score
//      double displayScore = Math.round((double) correctAnswers / (double) totalGuesses * 10000) / 100.0;
//      questionNumberTextView.setText(
//              getString(R.string.question, (correctAnswers+1), GROUPS_IN_QUIZ));
//      if (correctAnswers == 0)
//         scoreTextView.setText("");
//      else {
//         scoreTextView.setText("Score: " + String.valueOf(displayScore) + "%");
//
//         if (displayScore == 100.0) {
//            scoreTextView.setTextColor(
//                    getResources().getColor(R.color.great_score)
//            );
//         }
//         else if (displayScore < 100.0 && displayScore >= 50.0) {
//            scoreTextView.setTextColor(
//                    getResources().getColor(R.color.good_score)
//            );
//         }
//         else {
//            scoreTextView.setTextColor(
//                    getResources().getColor(R.color.bad_score)
//            );
//         }
//      }

      // extract the element from the next image's name
      String element = nextImage.substring(0, nextImage.indexOf('-'));

      // use AssetManager to load next image from assets folder
      AssetManager assets = getActivity().getAssets();

      // get an InputStream to the asset representing the next element
      // and try to use the InputStream
      try (InputStream stream =
                   assets.open(element + "/" + nextImage + ".png")) {
         // load the asset as a Drawable and display on the elementImageView
//         Drawable elementImage = Drawable.createFromStream(stream, nextImage);
//         elementImageView.setImageDrawable(elementImage);

         animate(false); // animate the flag onto the screen
      }
      catch (IOException exception) {
         Log.e(TAG, "Error loading " + nextImage, exception);
      }

      Collections.shuffle(fileNameList); // shuffle file names

      // put the correct answer at the end of fileNameList
      int correct = fileNameList.indexOf(correctAnswer);
      fileNameList.add(fileNameList.remove(correct));

      // add 6, 12, 18, 24 or 30 guess Buttons based on the value of guessRows
      for (int row = 0; row < guessRows; row++) {
         // place Buttons in currentTableRow
         for (int column = 0;
              column < guessLinearLayouts[row].getChildCount();
              column++) {
            // get reference to Button to configure
            ImageButton newGuessButton =
                    (ImageButton) guessLinearLayouts[row].getChildAt(column);
            newGuessButton.setEnabled(true);

            // get element name and set it as newGuessButton's text
            String filename = fileNameList.get((row * 2) + column);
//            newGuessButton.setText(getElementName(filename));
         }
      }

      // randomly replace one Button with the correct answer
      int row = random.nextInt(guessRows); // pick random row
      int column = random.nextInt(2); // pick random column
      LinearLayout randomRow = guessLinearLayouts[row]; // get the row
      String elementName = getElementName(correctAnswer);
//      ((ImageButton) randomRow.getChildAt(column)).setText(elementName);
   }

   // parses the element file name and returns the element name
   private String getElementName(String name) {
      name.replace(".png", "");
      return name.substring(name.indexOf('-') + 1).replace('_', ' ');

   }

   // animates the entire quizLinearLayout on or off screen
   private void animate(boolean animateOut) {
      // prevent animation into the the UI for the first flag
      if (correctAnswers == 0)
         return;

      // calculate center x and center y
      int centerX = (quizLinearLayout.getLeft() +
              quizLinearLayout.getRight()) / 2; // calculate center x
      int centerY = (quizLinearLayout.getTop() +
              quizLinearLayout.getBottom()) / 2; // calculate center y

      // calculate animation radius
      int radius = Math.max(quizLinearLayout.getWidth(),
              quizLinearLayout.getHeight());

      Animator animator;

      // if the quizLinearLayout should animate out rather than in
      if (animateOut) {
         // create circular reveal animation
         animator = ViewAnimationUtils.createCircularReveal(
                 quizLinearLayout, centerX, centerY, radius, 0);
         animator.addListener(
                 new AnimatorListenerAdapter() {
                    // called when the animation finishes
                    @Override
                    public void onAnimationEnd(Animator animation) {
                       loadNextElement();
                    }
                 }
         );
      }
      else { // if the quizLinearLayout should animate in
         animator = ViewAnimationUtils.createCircularReveal(
                 quizLinearLayout, centerX, centerY, 0, radius);
      }

      animator.setDuration(500); // set animation duration to 500 ms
      animator.start(); // start the animation
   }

   // called when a guess Button is touched
   private OnClickListener guessButtonListener = new OnClickListener() {
      @Override
      public void onClick(View v) {
         ImageButton guessButton = ((ImageButton) v);
         Drawable d = Drawable.createFromPath(correctAnswer);
         String guess = correctAnswer;
         guessButton.setBackground(d);
         String answer = getElementName(correctAnswer);
         ++totalGuesses; // increment number of guesses the user has made

         if (guess.equals(answer))
         {
            ++correctAnswers;

            answerTextView.setText(answer + "!");
            answerTextView.setTextColor(
                    getResources().getColor(R.color.correct_answer,
                            getContext().getTheme()));

            disableButtons();

            if (correctAnswers == GROUPS_IN_QUIZ)
            {
               AlertDialog quizResults = new AlertDialog.Builder(getActivity()).create();
               double score = Math.round((double) correctAnswers / (double) totalGuesses * 10000) / 100.0;
               quizResults.setTitle("Your Score is " + score + "%");
               quizResults.setMessage("Play again?");
               quizResults.setButton(AlertDialog.BUTTON_POSITIVE, "Reset", new DialogInterface.OnClickListener(){
                  public void onClick (DialogInterface dialog, int which) {
                     resetQuiz();
                  }
               });
               quizResults.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit", new DialogInterface.OnClickListener(){
                  public void onClick (DialogInterface dialog, int which) {
                     System.exit(1);
                  }
               });

               quizResults.show();
            }
            else {


               handler.postDelayed(
                       new Runnable() {
                          @Override
                          public void run() {
                             animate(true);
                          }
                       }, 2000);
            }
         }
         else {
//            elementImageView.startAnimation(shakeAnimation);

            answerTextView.setText(R.string.incorrect_answer);
            answerTextView.setTextColor(getResources().getColor(R.color.incorrect_answer, getContext().getTheme()));
            guessButton.setEnabled(false);
         }

      }
   };


   // utility method that disables all answer Buttons
   private void disableButtons() {
      for (int row = 0; row < guessRows; row++) {
         LinearLayout guessRow = guessLinearLayouts[row];
         for (int i = 0; i < guessRow.getChildCount(); i++)
            guessRow.getChildAt(i).setEnabled(false);
      }
   }
}


