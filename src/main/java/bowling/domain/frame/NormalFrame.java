package bowling.domain.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NormalFrame extends Frame {

    private NormalFrame(NormalScores normalScores, boolean nextFinalFrame) {
        this.scores = normalScores;
        this.nextFrame = new NormalFrame();
        if (nextFinalFrame) {
            this.nextFrame = new FinalFrame();
        }
    }

    private NormalFrame(boolean nextFinalFrame) {
        this.scores = new NormalScores();
        this.nextFrame = new NormalFrame();
        if (nextFinalFrame) {
            this.nextFrame = new FinalFrame();
        }
    }

    private NormalFrame() {
        this.scores = new NormalScores();
        this.nextFrame = null;
    }

    public static NormalFrame nextFinalFrame() {
        return new NormalFrame(true);
    }

    public static NormalFrame nextFinalFrame(NormalScores normalScores) {
        return new NormalFrame(normalScores, true);
    }

    public static NormalFrame nextNormalFrame() {
        return new NormalFrame(false);
    }

    public static NormalFrame nextNormalFrame(NormalScores normalScores) {
        return new NormalFrame(normalScores, false);
    }

    @Override
    public Optional<Integer> frameScore() {
        if (scores.getScores().isEmpty() || !isFinished()) {
            return Optional.empty();
        }

        Optional<Integer> frameScore = Optional.of(scores.transSpareScores()
                .stream()
                .mapToInt(score -> score.getScore())
                .sum());

        if (scores.getScores().contains(Score.SPARE)) {
            frameScore = getBonusScoreSpare(frameScore);
        }
        if (scores.getScores().contains(Score.STRIKE)) {
            frameScore = getBonusScoreStrike(frameScore);
        }
        return frameScore;
    }

    @Override
    public Optional<List<Score>> getTwoScores() {
        if (this.scores.getScores().size() == 2) {
            return Optional.of(this.scores.transSpareScores());
        }
        if (this.scores.getScores().contains(Score.STRIKE) && nextFrame.getScores().size() >= 1) {
            List<Score> result = new ArrayList<>(scores.transSpareScores());
            result.add(nextFrame.scores.getScores().get(0));
            return Optional.of(result);
        }
        return Optional.empty();
    }

    public Optional<Integer> getBonusScoreSpare(Optional<Integer> frameScore) {
        if (nextFrame.getOneScore().isPresent()) {
            int bonusScore = nextFrame.getOneScore()
                    .get()
                    .getScore();
            return Optional.of(frameScore.get() + bonusScore);
        }
        return Optional.empty();
    }

    public Optional<Integer> getBonusScoreStrike(Optional<Integer> frameScore) {
        if (nextFrame.getTwoScores().isPresent()) {
            int bonusScore = nextFrame.getTwoScores()
                    .get()
                    .stream()
                    .mapToInt(score -> score.getScore())
                    .sum();
            return Optional.of(frameScore.get() + bonusScore);
        }
        return Optional.empty();
    }


}