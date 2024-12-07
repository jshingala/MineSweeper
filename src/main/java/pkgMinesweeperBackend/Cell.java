package pkgMinesweeperBackend;

public class Cell {
    private boolean isMine;
    private boolean isRevealed;
    private int score;
    private String message;  // Add message field

    public Cell() {
        this.isMine = false;
        this.isRevealed = false;
        this.score = 0;
        this.message = "";
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        this.isMine = mine;
        if (mine) {
            this.score = 0;
        }
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        isRevealed = true;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = this.isMine ? 0 : score;
    }
    public String getMessage() {
        if (isMine) {
            return "BOOM! You hit a mine!";
        } else {
            return "You found Pikachu!";
        }
    }
}