# Go-Game
This is my implementation of the popular Japanese game of Go.
I have used multitrading so that the game can be played by 2 players. 
The game logic is implemented in a purely functional style.

Rules: https://www.siammandalay.com/blogs/puzzles/play-go-board-game-instructions-rules-strategies

The game has command interface.
Commands:
  n - creates a new board with dimentions 9x9\n
  new - creates a new board with dimentions 9x9\n
  n-d-k - creates a new board with dimentions d and komi -k\n
  i-j - plays a move on position (i j)\n
  pass - current player passes his/her turn\n
  s - you surrender the game\n
  h - prints all commands\n
