# MGE Mini Project
## Overview
The is a quiz app about solar system facts using the Solar System OpenData API.
Questions are generated at random using the solar system API and templates for questions (see QuestionGenerator).

## Points
### Functionality
#### > Quizpage (1-3 points)
Displays question and appropriate fields for inputting the answer.
The quizpage contains a textview to display the current question, an image view which holds the picture of the drawn astronaut and lastly a fragment container view.
The fragment contains the answer elements that correspond to the given question type.
Information needed for the fragments answer elements is passed by the activity through the usage of a bundle.
As example: Single Choice Question requires the labelling of the buttons; these values are passed by a bundle.
Another example: The slider question requires step size, start value and end value.
The fragment then implements the logic to obtain the user input and passes it back to the activity by usage of the DataInterface. The QuizActivity subsequently displays a dialog with the result. Upon confirming the dialog, the next question (of random type) is loaded.

Currently 3 types of questions exist:
  - Single Choice Question (1-3 points)
  - Slider Question [always provides 10 slider points for convenient user entry] (1-3 points)
  - TextView Question (1-3 points)

### Persistence (1-3 points)
Persistence is implemented using the Room DB (allows creation and access of a sqlite database).
After the app is started it requests all solar bodies from the Solar System OpenData API and loads them into the database.

If there is no internet connection (or the api is unreachable) and the database is populated from a previous start of the app, then the app will continue to function normally.

If there is no internet connection and the app was never started before then there is no data available for the app to base the questions on. Therefore it informs the user of the fact that the very first start of the app does require an internet connection and subsequently closes the app.

### Usage of webservices (1-3 points)
Usage of the Solar System OpenData API. Requests are sent through Volley.

## Solar System OpenData API
For more information click [here](https://api.le-systeme-solaire.net/en).

## Screenshots
![Single Choice Question](./Screenshots/SingleChoiceQuestion.png)
![Slider Question](./Screenshots/SliderQuestion.png)
![TextView Question](./Screenshots/TextViewQuestion.png)
![Correct Answer](./Screenshots/CorrectAnswer.png)
![Wrong Answer](./Screenshots/WrongAnswer.png)
![Offline Capabilities](./Screenshots/OfflineCapabilities.png)
