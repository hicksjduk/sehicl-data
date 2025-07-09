package uk.org.sehicl.data;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder(value =
{ "ballsBowled", "runs", "wickets", "notes" })
public class Bowler extends Performance implements Comparable<Bowler>
{
    private static final Comparator<Bowler> COMPARATOR = Comparator
            .comparing(Bowler::getWicketsTaken)
            .reversed()
            .thenComparingDouble(Bowler::getEconomyRate);
    
    private int ballsBowled;
    private int runsConceded;
    private int wicketsTaken;

    public int getBallsBowled()
    {
        return ballsBowled;
    }

    public void setBallsBowled(int ballsBowled)
    {
        this.ballsBowled = ballsBowled;
    }

    @JacksonXmlProperty(localName = "runs")
    public int getRunsConceded()
    {
        return runsConceded;
    }

    @JacksonXmlProperty(localName = "runs")
    public void setRunsConceded(int runsConceded)
    {
        this.runsConceded = runsConceded;
    }

    @JacksonXmlProperty(localName = "wickets")
    public int getWicketsTaken()
    {
        return wicketsTaken;
    }

    @JacksonXmlProperty(localName = "wickets")
    public void setWicketsTaken(int wicketsTaken)
    {
        this.wicketsTaken = wicketsTaken;
    }

    @JsonIgnore
    public double getEconomyRate()
    {
        double answer = Double.MAX_VALUE;
        if (ballsBowled != 0)
        {
            answer = 1.0 * runsConceded / ballsBowled;
        }
        return answer;
    }

    @Override
    public int compareTo(Bowler o)
    {
        return COMPARATOR.compare(this, o);
    }
}
