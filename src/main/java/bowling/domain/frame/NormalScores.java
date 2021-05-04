package bowling.domain.frame;


import java.util.List;

public class NormalScores extends Scores {

    public NormalScores() {
        this.status = Status.NOT_FINISH;
    }

    @Override
    public boolean isFinished() {
        if (scores.isEmpty()) {
            return false;
        }
        if (scores.size() == 2) {
            return true;
        }
        if (scores.get(0).equals(Score.STRIKE)) {
            return true;
        }
        return false;
    }

    @Override
    public void addScore(int score) throws Exception {
        if (!status.equals(Status.NOT_FINISH)) {
            throw new Exception("종료된 프레임입니다.");
        }
        validScore(score);
        if (firstScore == null) {
            setFirstScore(score);
            return;
        }
        if (secondScore == null) {
            setSecondScore(score);
            return;
        }
    }

    private void setFirstScore(int score) {
        firstScore = Score.valueOf(score);
        if (firstScore.equals(Score.STRIKE)) {
            status = Status.STRIKE;
        }
    }

    private void setSecondScore(int score) {
        secondScore = Score.valueOf(score);
        if (firstScore.equals(Score.STRIKE)) {
            status = Status.STRIKE;
        }
    }

    private void validScore(int score) {
        if (score > 10) {
            throw new IllegalArgumentException("score는 10을 넘을 수 없습니다.");
        }
        if (firstScore.getScore() + score > 10) {
            throw new IllegalArgumentException("점수의 합은 10을 넘을 수 없습니다.");
        }
    }

    @Override
    public List<Score> getScores() {
        return scores;
    }

    private void status(Status status) {

    }

}
