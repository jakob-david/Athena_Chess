# Athena_Chess

Small chess game with an even smaller AI opponent.....


## Basic information about the chess logic
The chess game is able to do all moves except: 
1. Castling
2. en pasant
3. pawn promotion to something els than a queen 

## Basic information about the AI. 
The AI calculates four parameters to decide which move to take: 

1. How many moves it can make more after a possible move is taken. 
2. How many moves the opponent can make more after a possible move is taken.
3. How many (weighted) pieces the opponent has lost. 
4. Points from the recursion. 

To evaluate how good one move is a linear combination is taken. 
The weights for the combination is derived by a training process. 

### Recursion 
Each recursive step plays one move from the opponent and one move for one self. 
Both chosen by an AI without recursion. 
Moreover, the AI is reduced to only include the "piece value" (point 3).

### Training 
The training is very simple. First two AIs get initialised: 

1. best_AI
2. new_AI

Imagine there would only be one parameter x. best_AI will be initialised with x=1. 
new_AI with x = best_AI.x+1. They play a defined number of times against each other. 
If new_AI wins it will be the new best_AI and the process starts over. If not best_AI will be returned. 

Comment: Of course it is a bit more complicated but not by much.