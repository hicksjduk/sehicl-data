package uk.org.sehicl.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder(value =
{ "runsScored", "wicketsLost", "ballsBowled", "batsman", "bowler" })
public class Innings
{
    private int runsScored;
    private Integer wicketsLost;
    private Integer ballsBowled;
    private final List<Performance> performances = new ArrayList<>();

    public int getRunsScored()
    {
        return runsScored;
    }

    public void setRunsScored(int runsScored)
    {
        this.runsScored = runsScored;
    }

    @JsonInclude(Include.NON_NULL)
    public Integer getWicketsLost()
    {
        return wicketsLost;
    }

    @JsonIgnore
    public int getWicketsLost(int maxWickets)
    {
        return wicketsLost == null ? maxWickets : wicketsLost;
    }

    public void setWicketsLost(Integer wicketsLost)
    {
        this.wicketsLost = wicketsLost;
    }

    @JsonInclude(Include.NON_NULL)
    public Integer getBallsBowled()
    {
        return ballsBowled;
    }

    @JsonIgnore
    public int getBallsBowled(int maxBalls)
    {
        return ballsBowled == null ? maxBalls : ballsBowled;
    }

    public void setBallsBowled(Integer ballsBowled)
    {
        this.ballsBowled = ballsBowled;
    }

    @JsonIgnore
    public List<Performance> getPerformances()
    {
        return performances;
    }

    @JacksonXmlProperty(localName = "batsman")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Batsman> getBatsmen()
    {
        return filterPerformances(Batsman.class);
    }

    @JacksonXmlProperty(localName = "batsman")
    public void addBatsman(Batsman batsman)
    {
        performances.add(batsman);
    }

    @JacksonXmlProperty(localName = "bowler")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Bowler> getBowlers()
    {
        return filterPerformances(Bowler.class);
    }

    private <T extends Performance> List<T> filterPerformances(Class<T> type)
    {
        List<T> answer = new ArrayList<>();
        for (Performance perf : performances)
        {
            if (type.isAssignableFrom(perf.getClass()))
            {
                answer.add(type.cast(perf));
            }
        }
        return answer;
    }

    @JacksonXmlProperty(localName = "bowler")
    public void addBowler(Bowler bowler)
    {
        performances.add(bowler);
    }

    @Override
    public String toString()
    {
        return "Innings [runsScored=" + runsScored + ", wicketsLost=" + wicketsLost
                + ", ballsBowled=" + ballsBowled + ", performances=" + performances + "]";
    }

    public Completeness getCompleteness(Integer minBalls, Integer maxWickets)
    {
        Completeness answer = Completeness.CONSISTENT;
        if ((minBalls != null && ballsBowled != null && ballsBowled < minBalls)
                || (maxWickets != null && wicketsLost != null && wicketsLost > maxWickets))
        {
            answer = Completeness.COMPLETE;
        }
        return answer;
    }
    
    public boolean isAllOut(int maxWickets)
    {
        return getWicketsLost(maxWickets) == maxWickets;
    }
}
