package uk.org.sehicl.website.rules;

import java.util.Optional;

public class Rules
{
    private final int pointsPerWin;
    private final int pointsPerTie;
    private final int pointsPerDefeat;
    private final int ballsPerOver;
    private final int oversPerInnings;
    private final int maxWickets;
    private final int battingPointsThreshold;
    private final int battingPointsIncrement;
    private final int wicketsInHandThreshold;
    private final int wicketsInHandIncrement;
    private final int bowlingPointsThreshold;
    private final int bowlingPointsIncrement;
    private final int battingPointsForAwardedMatch;
    private final int bowlingPointsForAwardedMatch;
    private final int maxBattingPoints;
    private final int minRunsForBattingHighlight;
    private final int minWicketsForBowlingHighlight;
    private final boolean orderByAveragePoints;

    public Rules(Builder builder)
    {
        this.pointsPerWin = builder.pointsPerWin;
        this.pointsPerTie = builder.pointsPerTie;
        this.pointsPerDefeat = builder.pointsPerDefeat;
        this.ballsPerOver = builder.ballsPerOver;
        this.oversPerInnings = builder.oversPerInnings;
        this.maxWickets = builder.maxWickets;
        this.battingPointsThreshold = builder.battingPointsThreshold;
        this.battingPointsIncrement = builder.battingPointsIncrement;
        this.wicketsInHandThreshold = builder.wicketsInHandThreshold;
        this.wicketsInHandIncrement = builder.wicketsInHandIncrement;
        this.bowlingPointsThreshold = builder.bowlingPointsThreshold;
        this.bowlingPointsIncrement = builder.bowlingPointsIncrement;
        this.battingPointsForAwardedMatch = builder.battingPointsForAwardedMatch;
        this.bowlingPointsForAwardedMatch = builder.bowlingPointsForAwardedMatch;
        this.maxBattingPoints = builder.maxBattingPoints;
        this.minRunsForBattingHighlight = builder.minRunsForBattingHighlight;
        this.minWicketsForBowlingHighlight = builder.minWicketsForBowlingHighlight;
        this.orderByAveragePoints = builder.orderByAveragePoints;
    }

    public int getPointsPerWin()
    {
        return pointsPerWin;
    }

    public int getPointsPerTie()
    {
        return pointsPerTie;
    }

    public int getPointsPerDefeat()
    {
        return pointsPerDefeat;
    }

    public int getBallsPerOver()
    {
        return ballsPerOver;
    }

    public int getOversPerInnings()
    {
        return oversPerInnings;
    }

    public int getMaxWickets()
    {
        return maxWickets;
    }

    public int getBattingPointsThreshold()
    {
        return battingPointsThreshold;
    }

    public int getBattingPointsIncrement()
    {
        return battingPointsIncrement;
    }

    public int getWicketsInHandThreshold()
    {
        return wicketsInHandThreshold;
    }

    public int getWicketsInHandIncrement()
    {
        return wicketsInHandIncrement;
    }

    public int getBowlingPointsThreshold()
    {
        return bowlingPointsThreshold;
    }

    public int getBowlingPointsIncrement()
    {
        return bowlingPointsIncrement;
    }

    public int getBattingPointsForAwardedMatch()
    {
        return battingPointsForAwardedMatch;
    }

    public int getBowlingPointsForAwardedMatch()
    {
        return bowlingPointsForAwardedMatch;
    }

    public int getMaxBattingPoints()
    {
        return maxBattingPoints;
    }

    public int getMinRunsForBattingHighlight()
    {
        return minRunsForBattingHighlight;
    }

    public int getMinWicketsForBowlingHighlight()
    {
        return minWicketsForBowlingHighlight;
    }

    public boolean isOrderByAveragePoints()
    {
        return orderByAveragePoints;
    }

    public int getBattingPoints(int runsScored, Integer wicketsLostifWonBattingSecond)
    {
        int runsPoints = Math.max(0, runsScored - battingPointsThreshold) / battingPointsIncrement;
        int wicketsPoints = 0;
        if (wicketsLostifWonBattingSecond != null)
        {
            int wicketsInHand = maxWickets - wicketsLostifWonBattingSecond;
            wicketsPoints = Math.max(0, wicketsInHand - wicketsInHandThreshold)
                    / wicketsInHandIncrement;
        }
        int answer = Math.min(maxBattingPoints, runsPoints + wicketsPoints);
        return answer;
    }

    public int getBowlingPoints(Integer wicketsTaken)
    {
        int wickets = wicketsTaken == null ? maxWickets : wicketsTaken;
        int answer = Math.max(0, wickets - bowlingPointsThreshold) / bowlingPointsIncrement;
        return answer;
    }

    public int getRunRateBalls(Integer ballsBowled, Integer wicketsLost, Integer overLimit)
    {
        int maxBalls = (overLimit == null ? oversPerInnings : overLimit) * ballsPerOver;
        int balls = ballsBowled == null ? maxBalls : ballsBowled;
        int answer = wicketsLost != null && wicketsLost < maxWickets ? balls : maxBalls;
        return answer;
    }

    public String ballsToOvers(Integer balls)
    {
        int b = balls == null ? oversPerInnings * ballsPerOver : balls;
        String template = b % ballsPerOver == 0 ? "%d" : "%d.%d";
        String answer = String.format(template, b / ballsPerOver, b % ballsPerOver);
        return answer;
    }

    public static class Builder
    {
        private int pointsPerWin = 12;
        private int pointsPerTie = 6;
        private int pointsPerDefeat = 0;
        private int ballsPerOver = 6;
        private int oversPerInnings = 12;
        private int maxWickets = 6;
        private int battingPointsThreshold = 60;
        private int battingPointsIncrement = 10;
        private int wicketsInHandThreshold = 0;
        private int wicketsInHandIncrement = 1;
        private int bowlingPointsThreshold = 0;
        private int bowlingPointsIncrement = 1;
        private int battingPointsForAwardedMatch = 5;
        private int bowlingPointsForAwardedMatch = 5;
        private int maxBattingPoints = 6;
        private int minRunsForBattingHighlight = 20;
        private int minWicketsForBowlingHighlight = 2;
        private boolean orderByAveragePoints = true;

        public Builder()
        {
            this(null);
        }

        public Builder(Integer season)
        {
            var s = Optional.ofNullable(season);
            if (s.orElse(15) < 15)
                setBattingPointsForAwardedMatch(3).setBowlingPointsForAwardedMatch(6);
            if (s.orElse(19) < 19)
                setOrderByAveragePoints(false);
        }

        public Rules build()
        {
            return new Rules(this);
        }

        public Builder setBallsPerOver(int ballsPerOver)
        {
            this.ballsPerOver = ballsPerOver;
            return this;
        }

        public Builder setOversPerInnings(int oversPerInnings)
        {
            this.oversPerInnings = oversPerInnings;
            return this;
        }

        public Builder setMaxWickets(int maxWickets)
        {
            this.maxWickets = maxWickets;
            return this;
        }

        public Builder setBattingPointsThreshold(int battingPointsThreshold)
        {
            this.battingPointsThreshold = battingPointsThreshold;
            return this;
        }

        public Builder setBattingPointsIncrement(int battingPointsIncrement)
        {
            this.battingPointsIncrement = battingPointsIncrement;
            return this;
        }

        public Builder setWicketsInHandThreshold(int wicketsInHandThreshold)
        {
            this.wicketsInHandThreshold = wicketsInHandThreshold;
            return this;
        }

        public Builder setWicketsInHandIncrement(int wicketsInHandIncrement)
        {
            this.wicketsInHandIncrement = wicketsInHandIncrement;
            return this;
        }

        public Builder setBowlingPointsThreshold(int bowlingPointsThreshold)
        {
            this.bowlingPointsThreshold = bowlingPointsThreshold;
            return this;
        }

        public Builder setBowlingPointsIncrement(int bowlingPointsIncrement)
        {
            this.bowlingPointsIncrement = bowlingPointsIncrement;
            return this;
        }

        public Builder setBattingPointsForAwardedMatch(int battingPointsForAwardedMatch)
        {
            this.battingPointsForAwardedMatch = battingPointsForAwardedMatch;
            return this;
        }

        public Builder setBowlingPointsForAwardedMatch(int bowlingPointsForAwardedMatch)
        {
            this.bowlingPointsForAwardedMatch = bowlingPointsForAwardedMatch;
            return this;
        }

        public Builder setMaxBattingPoints(int maxBattingPoints)
        {
            this.maxBattingPoints = maxBattingPoints;
            return this;
        }

        public Builder setPointsPerWin(int pointsPerWin)
        {
            this.pointsPerWin = pointsPerWin;
            return this;
        }

        public Builder setPointsPerTie(int pointsPerTie)
        {
            this.pointsPerTie = pointsPerTie;
            return this;
        }

        public Builder setPointsPerDefeat(int pointsPerDefeat)
        {
            this.pointsPerDefeat = pointsPerDefeat;
            return this;
        }

        public Builder setMinRunsForBattingHighlight(int minRunsForBattingHighlight)
        {
            this.minRunsForBattingHighlight = minRunsForBattingHighlight;
            return this;
        }

        public Builder setMinWicketsForBowlingHighlight(int minWicketsForBowlingHighlight)
        {
            this.minWicketsForBowlingHighlight = minWicketsForBowlingHighlight;
            return this;
        }

        public Builder setOrderByAveragePoints(boolean orderByAveragePoints)
        {
            this.orderByAveragePoints = orderByAveragePoints;
            return this;
        }
    }
}
