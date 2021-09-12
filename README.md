# Clock
 
For this application we used an activity which in turn includes 3 fragments, one for drawing the analog clock, and two for the timer.
For the activity I created a menu at the bottom of the screen using the BottomNavigationView class. A shape for the design has been created for the menu.

In the first fragment for the drawing part of the analog clock, I used the RadialGradient class to create a design as attractive as possible for the watch.

In the second fragment, the one for the timer, we created a time picker to be able to select the desired time, and by pressing a button we switch to the third fragment and transmit the selected time print-run SharedPreferences.

In the third fragment, a circular ProgressBar was created, which animates the passage of time. Once the time is up, an alarm created with the help of the AlarmManager class starts, which can be stopped before starting by pressing a button, namely "Cancel", or once started it ends after 10 seconds.

Moreover, different shapes were used for the design part.
And for the technical part I used BroadcastReceiver, together with Thread and Handler for a better coordination between the progress bar and the past time.

The application was designed and created for the Pixel3a API28.
