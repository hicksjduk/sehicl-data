package uk.org.sehicl.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import uk.org.sehicl.website.rules.Rules;

@JsonPropertyOrder(value =
{ "reason", "countAsPlayed" })
public class UnplayedMatch implements Outcome
{
    private String reason;
    private boolean countAsPlayed;

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    @Override
    public Completeness getCompleteness(Rules rules)
    {
        return Completeness.CONSISTENT;
    }

    public boolean getCountAsPlayed()
    {
        return countAsPlayed;
    }

    public void setCountAsPlayed(boolean countAsPlayed)
    {
        this.countAsPlayed = countAsPlayed;
    }

    @Override
    public String toString()
    {
        return "UnplayedMatch [reason=" + reason + ", countAsPlayed=" + countAsPlayed + "]";
    }
}
