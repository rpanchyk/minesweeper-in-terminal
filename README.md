# MineSweeper In Terminal

The legendary game ported to terminal.

## Requirements

- [Java 17](https://jdk.java.net/archive/)
- [Apache Maven](https://maven.apache.org/)
- [GNU Make](https://www.gnu.org/software/make/)

## Build

    make build

## Run

    make run

## Rules

The goal of the game is to open all cells on the board and mark mines with flags.

Type of cells:

- `.` - cell not touched yet.
- `0...N` - cell shows adjacent mines number.
- `F` - cell flagged as mine.
- `X` - cell with exploded mine.
- `M` - cell with mine (shows after game is finished).

## Gameplay

### Start game

![alt](docs/msit-1-start.png)

### Open cell

![alt](docs/msit-2-open.png)

### Set flag

![alt](docs/msit-3-flag.png)

### Explode on mine

![alt](docs/msit-4-explode.png)

### Win another game

![alt](docs/msit-5-win.png)
