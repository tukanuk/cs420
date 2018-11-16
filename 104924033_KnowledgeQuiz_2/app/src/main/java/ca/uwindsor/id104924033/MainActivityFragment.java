// MainActivityFragment.java
// Contains the Flag Quiz logic
package ca.uwindsor.id104924033;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivityFragment extends Fragment {
   // String used when logging error messages
   private static final String TAG = "FlagQuiz Activity";

   private static final int GROUPS_IN_QUIZ = 10;

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

   private TextView quizTextView; // dispalys the questiosn
   private List<String> answerList; // stores the answers

   private LinearLayout[] guessLinearLayouts; // rows of answer Buttons
   private TextView answerTextView; // displays correct answer
   private TextView scoreTextView; // displays the current score
   private Button exitButton; // exit button

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

      answerList = new ArrayList<>();

      // load the shake animation that's used for incorrect answers
      shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
         R.anim.incorrect_shake);
      shakeAnimation.setRepeatCount(3); // animation repeats 3 times

      // get references to GUI components
      quizLinearLayout =
              view.findViewById(R.id.quizLinearLayout);
      questionNumberTextView =
              view.findViewById(R.id.questionNumberTextView);
//      elementImageView = view.findViewById(R.id.elementImageView);

      quizTextView = view.findViewById((R.id.quizTextView)); // quest text view

      guessLinearLayouts = new LinearLayout[5];
      guessLinearLayouts[0] =
              view.findViewById(R.id.row1LinearLayout);
//      guessLinearLayouts[1] =
//              view.findViewById(R.id.row2LinearLayout);
//      guessLinearLayouts[2] =
//              view.findViewById(R.id.row3LinearLayout);
//      guessLinearLayouts[3] =
//              view.findViewById(R.id.row4LinearLayout);
//      guessLinearLayouts[4] =
//              view.findViewById(R.id.row5LinearLayout);
      answerTextView = view.findViewById(R.id.answerTextView);
      scoreTextView = view.findViewById(R.id.scoreTextView);
      exitButton = view.findViewById(R.id.exitButton);

      // configure listeners for the guess Buttons
      for (LinearLayout row : guessLinearLayouts) {
         for (int column = 0; column < row.getChildCount(); column++) {
            Button button = (Button) row.getChildAt(column);
            button.setOnClickListener(guessButtonListener);
         }
      }

      // set questionNumberTextView's text
      questionNumberTextView.setText(getString(R.string.question, 1, GROUPS_IN_QUIZ));
      scoreTextView.setText("_");

      return view; // return the fragment's view for display
   }

   // set up and start the next quiz
   public void resetQuiz() {
      // use AssetManager to get image file names for enabled regions
//      AssetManager assets = getActivity().getAssets();
      fileNameList.clear(); // empty list of image file names

      fileNameList.add("The colour of the universe is black");
      fileNameList.add("The chicken came before the egg");
      fileNameList.add("The sun is actually a star");
      fileNameList.add("DNA is made from protein");
      fileNameList.add("Bats aren't blind");
      fileNameList.add("The memory span of a goldfish is a few seconds.");
      fileNameList.add("6 cows, 9 chickens and 12 humans have 66 legs");
      fileNameList.add("Vegetarian diet can provide enough protein");
      fileNameList.add("Eating less than an hour before swimming causes cramps");
      fileNameList.add("Lightning strikes the same place twice");

      // store the answers
      answerList.add("False");
      answerList.add("True");
      answerList.add("True");
      answerList.add("False");
      answerList.add("True");
      answerList.add("False");
      answerList.add("True");
      answerList.add("True");
      answerList.add("False");
      answerList.add("True");


      correctAnswers = 0; // reset the number of correct answers made
      totalGuesses = 0; // reset the total number of guesses the user made
      quizElementsList.clear(); // clear prior list of quiz countries

      int elementCounter = 1;
      int numberOfElements = 10; //fileNameList.size();

   }

   // after the user guesses a correct element, load the next element
   private void loadNextElement() {
      // get file name of the next flag and remove it from the list
//      String nextImage = quizElementsList.remove(0);
      String nextQuestion = quizElementsList.remove(0);
      String nextAnswer = answerList.remove(0);

      correctAnswer = nextAnswer;
//      correctAnswer = nextImage; // update the correct answer
      answerTextView.setText(""); // clear answerTextView

      // display current question number and current score
       double displayScore = Math.round((double) correctAnswers / (double) totalGuesses * 10000) / 100.0;
       questionNumberTextView.setText(
               getString(R.string.question, (correctAnswers+1), GROUPS_IN_QUIZ));
       if (correctAnswers == 0)
           scoreTextView.setText("");
       else {
          scoreTextView.setText("Score: " + String.valueOf(displayScore) + "%");

          if (displayScore == 100.0) {
               scoreTextView.setTextColor(
                       getResources().getColor(R.color.great_score)
               );
          }
          else if (displayScore < 100.0 && displayScore >= 50.0) {
               scoreTextView.setTextColor(
                       getResources().getColor(R.color.good_score)
               );
          }
          else {
               scoreTextView.setTextColor(
                       getResources().getColor(R.color.bad_score)
               );
          }
       }

       quizTextView.setText(nextQuestion);

         animate(false); // animate the flag onto the screen

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
         Button guessButton = ((Button) v);
         String guess = guessButton.getText().toString();
         String answer = correctAnswer;
         ++totalGuesses; // increment number of guesses the user has made

         if (guess.equals(answer))
         {
            ++correctAnswers;

            answerTextView.setText("Correct :)");
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
            quizTextView.startAnimation(shakeAnimation);

            answerTextView.setText("Incorrect !!");
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

   public void exitApp() {
      int pid = android.os.Process.myPid();
      android.os.Process.killProcess(pid);
   }
}


