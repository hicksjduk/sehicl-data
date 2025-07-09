package uk.org.sehicl.data;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder(value =
{ "runs", "out", "notes" })
public class Batsman extends Performance implements Comparable<Batsman>
{
    private static final Comparator<Batsman> COMPARATOR = Comparator
            .comparingInt(Batsman::getRunsScored)
            .reversed()
            .thenComparing(Batsman::isOut);

    private int runsScored;
    private boolean out;

    @JacksonXmlProperty(localName = "runs")
    public int getRunsScored()
    {
        return runsScored;
    }

    @JacksonXmlProperty(localName = "runs")
    public void setRunsScored(int runsScored)
    {
        this.runsScored = runsScored;
    }

    public boolean isOut()
    {
        return out;
    }

    public void setOut(boolean out)
    {
        this.out = out;
    }

    @Override
    public int compareTo(Batsman o)
    {
        return COMPARATOR.compare(this, o);
    }
}
