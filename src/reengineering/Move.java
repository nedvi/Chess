package reengineering;

public class Move {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean isCapture;
    private boolean isCheck;
    private boolean isCheckmate;
    private boolean isCastle;

    public Move(int startX, int startY, int endX, int endY, boolean isCapture, boolean isCheck, boolean isCheckmate, boolean isCastle) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isCapture = isCapture;
        this.isCheck = isCheck;
        this.isCheckmate = isCheckmate;
        this.isCastle = isCastle;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public boolean isCheckmate() {
        return isCheckmate;
    }

    public boolean isCastle() {
        return isCastle;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setCapture(boolean capture) {
        isCapture = capture;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void setCheckmate(boolean checkmate) {
        isCheckmate = checkmate;
    }

    public void setCastle(boolean castle) {
        isCastle = castle;
    }

    @Override
    public String toString() {
        return "Move{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                ", isCapture=" + isCapture +
                ", isCheck=" + isCheck +
                ", isCheckmate=" + isCheckmate +
                ", isCastle=" + isCastle +
                '}';
    }
}