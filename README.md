# MGE Mini Project
## Overview
The goal is to code a quiz app about solar system facts using the Solar System OpenData API.
Questions are generated at random using the solar system API and templates for questions.

Examples:
- How many more moons does PlanetX have than PlanetY?
- How many times is PlanetX bigger than PlanetY?
- What is the temperature on PlanetX?
- Which planet has the stronger gravity, PlanetX or PlanetY?
- By whom was PlanetX discovered?
- What is PlanetX's orbital speed?
- Who of the following people has never discovered a planet?

## Points
### Functionality per screen (each 1-5 points):
#### > Startpage
Provides navigation to quizpage and statisticspage
#### > Quizpage
Displays question and appropiate fields for inputting the answer (e.g. number field for temperature or orbital speed, slider for size comparisons, textfield or multiple choice for facts). Furthermore contains button to skip a given question.  
#### > Statisticspage
Contains statistics about the user's guessing abilities/knowledge.
Examples
- Average wrong guesses per question
- Most well-known planet
- Average time to answer a given question
- Amount of skipped questions per round
- Total rounds played
- Average total improvement between rounds
- For numeric questions: Average deviation from correct answer
### Persistence (1-3 points)
Persist the statisticts as well as the values needed for computing said statistics.
### Usage of webservices (1-3 points)
Usage of the Solar System OpenData API

## Solar System OpenData API
For more information click [here](https://api.le-systeme-solaire.net/en).
