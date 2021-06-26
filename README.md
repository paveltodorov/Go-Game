# Go-Game
This is my implementation of the popular Japanese game of Go.<br>
I have used multitrading so that the game can be played by 2 players.<br> 
The game logic is implemented in a purely functional style.<br>

Rules: https://www.siammandalay.com/blogs/puzzles/play-go-board-game-instructions-rules-strategies<br>

The game has command interface.<br>
Commands:<br>
  n - creates a new board with dimentions 9x9<br> 
  new - creates a new board with dimentions 9x9<br>
  n-d-k - creates a new board with dimentions d and komi k<br>
  i-j - plays a move on position (i j)<br>
  pass - current player passes his/her turn<br>
  s - you surrender the game<br>
  h - prints all commands<br>
