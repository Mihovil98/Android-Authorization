# Android Authorization

## Description

The user has the possibility to register and only after that can start using the application. Each registered user has his own profile where he can enter personal information and add a picture. The main functionality of the application is to play the game and collect points for correct answers. After the achieved result, the user can save his result, which will be displayed on the highscore.

## Used Concepts

Activity, Explicit Intent, Fragment, Navigation, Safe Args, SoundPool, RecyclerView: display of results pulled from the database, SQLite database: registration, login, saving results

## Game

Three words and five buttons are given. Buttons are colored purple, blue, pink, yellow and green respectively. Each given word will be either purple, blue, pink, yellow or green and will be colored in one of the mentioned colors that is different from its name.

Every word has a unique combination of text and color. Two of the three given words will have in common text and color and the third word will have nothing in common with the other two.

Every correct answer scores one point and every wrong answer subducts one point. One game lasts thirty seconds and your task is to score as much points as you can!
