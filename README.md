<h1 align="center">Katz-tastrophe - eine Miniatur Programmier Lernumgebung</h1>

<p align = "center">
<img src="images_github/logo.gif" alt="logo" />

<h2 align="center">English version follows the German one</h2>

In Rahmen eines Universitätsmodul der Carl-von-Ossietzky Universität Oldenburg wurde dieses Programm im Wintersemester
2021/22 geschrieben. Ziel dieses Programmes ist es Programmierneulinge an das Konzept des objektorientierten Programmierens heranzuführen.
Er kann sowohl die unten aufgeführten Befehle innerhalb einer Main-Methode eingeben und ausführen lassen, als auch außerhalb der Main-Methode
neue Methoden schreiben und Variable definieren und diese dann in der Main-Methode aufrufen.

Sofern nicht anders am Anfang einer Methode angemerkt, wurde sämtlicher Code von Grund auf selber geschrieben.

## Voraussetzungen

Es wurden keine anderen Programme oder Frameworks benutzt. Zum Ausführen wird somit lediglich Java 11 oder höher und Maven benötigt.

## Setup über die Kommandozeile

1. Sollte das Programm über die Kommandozeile ausgeführt werden sollen, so müssen folgende Kommandos eingegeben werden:

   ```sh
   $ git clone https://github.com/RCRDMK/Katz-tastrophe
   $ cd Katz-tastrophe
   $ mvn package
   $ cd target
   $ java -jar Katz-tastrophe-1.0.jar
   
Diese Methode erfordert jedoch neben Java 11 und Maven zusätzlich noch eine Git Installation.

## Über das Projekt

<img src="images_github/Application_Window.png" alt="Screenshot of the application window" />

In Katz-tastrophe versucht der Akteur Sebastian sich aus der Küche etwas zu trinken zu holen. Leider befinden
sich auf den Weg dahin Katzen, welche nicht aus dem Weg gehen wollen. Der Spieler hat nun die Wahl entweder um die
Katzen drum herum zu gehen oder über sie herüber zu steigen. Steigt er über sie rüber, dann teilen sich Katze und Akteur
kurz das Feld.

In der Küche angekommen kann der Spieler nun das Trinken aufnehmen und zurück gehen. Hierbei ist zu beachten, dass der
Akteur entweder nur Trinken oder eine Katze, aber nicht beides tragen kann. Auch kann er nur eine Einheit davon auf
einmal tragen.

Solange der Akteur Trinken trägt, kann er nicht über Katzen herüber steigen, da er sich bei dieser Aktion mit seinen
Händen, wegen der eingeschränkten Trittfläche, welche er sich kurzzeitig mit der Katze teilt, ausbalancieren muss. Wenn es
keinen Weg um die Katze herum, sondern nur über sie rüber, gibt, kann der Spieler das Trinken ablegen und in einem
Quadrat um sich rum legen. Angelehnt an den Hamster-Simulator befindet sich der Akteur in einer Kachelwelt. Hindernisse
auf die er trifft sind Wände, wobei Katzen und Trinken als passive Gegenstände realisiert wurden.

## Befehle

void lookHere(String blickrichtung): Lässt den Akteur in die gewählte Richtung blicken, ohne dass er sich bewegt.
Gültige Eingaben hier sind „up“, „down“, „left“ und „right“.

void moveUp(): Bewegt sich auf das Feld überhalb des Akteurs. Nicht möglich, wenn dort eine Wand oder Trinken ist.

void moveDown(): Bewegt sich auf das Feld unterhalb des Akteurs. Nicht möglich, wenn dort eine Wand oder Trinken ist.

void moveLeft(): Bewegt sich auf das Feld links vom Akteur. Nicht möglich, wenn dort eine Wand oder Trinken ist.

void moveRight(): Bewegt sich auf das Feld rechts vom Akteur. Nicht möglich, wenn dort eine Wand oder Trinken ist.

void takeCat(): Nimmt die Katze auf, auf die der Akteur gerade anschaut. Zusätzlich wird geprüft ob die Hände des
Akteurs frei sind. Sind sie es, dann wird in der dazugehörigen Variable markiert, dass der Akteur momentan eine Katze
trägt. Sind die Hände schon belegt, wird die Katze nicht aufgehoben.

void takeDrink(): Nimmt das Trinken, welches der Akteur gerade anschaut. Zusätzlich wird geprüft ob die Hände des
Akteurs frei sind. Sind sie es, dann wird in der dazugehörigen Variable markiert, dass der Akteur momentan Trinken
trägt. Sind die Hände schon belegt, wird Trinken nicht aufgehoben.

void putCatDown(): Wenn eine Katze aufgehoben ist, wird sie in der Richtung abgelegt, in welcher der Akteur gerade
schaut.

void putDrinkDown(): Wenn Trinken aufgehoben ist, wird es in der Richtung abgelegt, in welcher der Akteur gerade
schaut.

Boolean handsFree(): Überprüft, ob die Hände des Akteurs frei sind. True wird zurückgegeben, wenn sie es sind und False
falls die Hände nicht frei sind.

Boolean catThere(): Überprüft, ob auf dem nächsten Feld der momentanen Blickrichtung des Akteurs eine Katze liegt. Liegt
dort keine Katze wird False zurückgegeben und True wenn eine Katze vor ihm liegt.

Boolean stepOverCatPossible(): Überprüft, ob es möglich ist über Katze rüber zu steigen. True wird zurückgegeben, wenn
es möglich ist und False wenn es nicht möglich ist.

## Lizenz

Der Code dieses Projektes untersteht der GNU General Public License. In der LICENSE Datei sind hier zu weitere
Informationen zu finden.

----------------------------------------------------------------------------------

<h1 align="center">Cat-tastrophe - a miniature programming learn environment</h1>

<p align = "center">
<img src="images_github/logo.gif" alt="logo" />

In the course of a class for the Carl-von-Ossietzky University Oldenburg this program was being created during the winter-term 2021/22. The goal of it is to show people the ropes of object-oriented programming, even if they never have been programming before in their life. As such you can input the commands, who are written down below, in the main-method, but can also create new methods and variables outside the main-method to call them then in the main-method.

If not being referred to otherwise at the beginning of a method, each and every line of code was being written by myself.

## Requirements

All you need is Java 11 and Maven. Other than these no other framworks were being used and it doesn't requires any additional programs.

## Setup over the CLI

1. Should the program be executed over the CLI following commands have to be inputed:

   ```sh
   $ git clone https://github.com/RCRDMK/Katz-tastrophe
   $ cd Katz-tastrophe
   $ mvn package
   $ cd target
   $ java -jar Katz-tastrophe-1.0.jar
   
Be advised that this method requires an additional Git installation on top of Java 11 and Maven.

## The project itself

<img src="images_github/Application_Window.png" alt="Screenshot of the application window" />

In Cat-tastrophe the protagonist Sebastian is trying to get himself something to drink from the kitchen. Unfortunately though, there are cats scattered on his way, which just don't want to move. The player now has the choice to either go around the cats or to step over them. If he chooses to step over them, the protagonist and the cat are sharing one space for a short amount of time. Additionally, he can also pick up a cat and place it on another space, so that he can pass by.

When arriving in the kitchen, the player can now pick up his drink and go back. It's worth mentioning, that the protagonist can only carry his drink or a cat, but not both at the same time. Also he can only carry one unit of cat or drink but not more.

As long as the protagonist is carrying his drink, he is unable to step over cats since he doesn't want to spill his drink and also has to remain his balance when sharing the same spot with a cat. As he's limited in where to step, he would the balance himself with his hands streched out but as they're already concerned with carrying his drink he can't. As a result, he has to find ways around the cat and if he's unable to he has to put down his drink, pick up a cat and put said cat on a tile around him, as Cat-tastrophe is referencing the Hamster Simulator, where the protagonist is in a tile world. His only obstacles are walls, which can be placed in advance, whereas cats and drinks where realized as passive objects.

## Commands

void lookHere(String direction where to look): Lets the protagonist look into the chosen direction without moving. Valid commands here are „up“, „down“, „left“ and „right“.

void moveUp(): Moves to the space above of the protagonist. Not possible, if there's a wall or a drink.

void moveDown(): Moves to the space below of the protagonist. Not possible, if there's a wall or a drink.

void moveLeft(): Moves to the space left of the protagonist. Not possible, if there's a wall or a drink.

void moveRight(): Moves to the space right of the protagonist. Not possible, if there's a wall or a drink.

void takeCat(): Picks up cat which the protagonist is looking at. Additionally, it will be checked if the hands of the protagonist are empty at the moment. If they are, it will be noted in the corresponding variable, that the protagonist is carrying a cat. If the hands are not empty, the cat can't be picked up.

void takeDrink(): Picks up drink which the protagonist is looking at. Additionally, it will be checked if the hands of the protagonist are empty at the moment. If they are, it will be noted in the corresponding variable, that the protagonist is carrying a drink. If the hands are not empty, the drink can't be picked up.

void putCatDown(): If a cat is picked up, it will be put down in the direction the protagonist is looking at the moment.

void putDrinkDown(): If a drink is picked up, it will be put down in the direction the protagonist is looking at the moment.

Boolean handsFree(): Checks if the hands are empty. If they are, true will be given back and false if they are not.

Boolean catThere(): Checks if on the next space a cat is. If there isn't one, false will be given back and true if there is a cat.

Boolean stepOverCatPossible(): Checks if it is possible to step over a cat. True will be given back when it is possible and false when it is not possible. 

## License

The code of this project is under the GNU General Public License. In the LICENSE file are more information about this to be found.

## Language usage

As this was only a university project, it only is available in the German language. This fact shouldn't get in the way of the functionality of this project though, as all the neccessary buttons also have images on them which should bridge the language gap to a certain degree.
