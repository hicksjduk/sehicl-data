package uk.org.sehicl.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder(value =
{ "teamRef", "battingFirst", "innings" })
public class TeamInMatch
{
    private String teamId;
    private boolean battingFirst;
    private Innings innings;

    @JsonIgnore
    public String getTeamId()
    {
        return teamId;
    }

    public TeamReference getTeamRef()
    {
        TeamReference answer = new TeamReference();
        answer.setId(teamId);
        return answer;
    }

    public void setTeamRef(TeamReference teamRef)
    {
        this.teamId = teamRef == null ? null : teamRef.getId();
    }

    public boolean isBattingFirst()
    {
        return battingFirst;
    }

    public void setBattingFirst(boolean battingFirst)
    {
        this.battingFirst = battingFirst;
    }

    @JacksonXmlProperty(localName = "innings")
    @JacksonXmlElementWrapper(useWrapping = false)
    public Innings getInnings()
    {
        return innings;
    }

    @JacksonXmlProperty(localName = "innings")
    public void setInnings(Innings innings)
    {
        this.innings = innings;
    }

    @Override
    public String toString()
    {
        return "TeamInMatch [teamId=" + teamId + ", battingFirst=" + battingFirst + ", innings="
                + innings + "]";
    }
}
