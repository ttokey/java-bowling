package bowling.domain.frame;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NormalFrame extends Frame {
    private NormalFrame(List<Score> scores) {
        this.scores = scores;
    }

    public static NormalFrame valueOf(int score) {
        return new NormalFrame(Arrays.asList(Score.valueOf(score)));
    }

    public static NormalFrame valueOf(List<Integer> scores) {
        return new NormalFrame(generateScores(scores));
    }

    @Override
    public boolean isFinished() {
        if (scores.size() == 2) {
            return true;
        }
        if (scores.get(0) == Score.STRIKE) {
            return true;
        }
        return false;
    }

    @Override
    public Frame addScore(int score) {
        return NormalFrame.valueOf(3);
    }

    private static List<Score> generateScores(List<Integer> scores) {
        return scores.stream()
                .map(score -> Score.valueOf(score))
                .collect(Collectors.toList());
    }
}
