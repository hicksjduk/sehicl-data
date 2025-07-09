package uk.org.sehicl.data;

import java.util.Date;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import uk.org.sehicl.website.rules.Rules;

@JsonPropertyOrder(value =
{ "date", "pitch", "homeTeam", "awayTeam", "playedMatch", "awardedMatch", "unplayedMatch" })
public class Match
{
    private Date dateTime;
    private String court;
    private String homeTeamId;
    private String awayTeamId;
    private Outcome outcome;

    @JacksonXmlProperty(localName = "date")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd.HH:mm")
    public Date getDateTime()
    {
        return dateTime;
    }

    @JacksonXmlProperty(localName = "date")
    @JsonFormat(pattern = "yyyy-MM-dd.HH:mm")
    public void setDateTime(Date dateTime)
    {
        this.dateTime = dateTime;
    }

    @JacksonXmlProperty(localName = "pitch")
    public String getCourt()
    {
        return court;
    }

    @JacksonXmlProperty(localName = "pitch")
    public void setCourt(String court)
    {
        this.court = court;
    }

    @JacksonXmlProperty(localName = "homeTeam")
    private TeamReference getHomeTeamRef()
    {
        TeamReference answer = new TeamReference();
        answer.setId(homeTeamId);
        return answer;
    }

    @JsonIgnore()
    public String getHomeTeamId()
    {
        return homeTeamId;
    }

    public void setHomeTeam(TeamReference ref)
    {
        homeTeamId = ref == null ? null : ref.getId();
    }

    @JacksonXmlProperty(localName = "awayTeam")
    private TeamReference getAwayTeamRef()
    {
        TeamReference answer = new TeamReference();
        answer.setId(awayTeamId);
        return answer;
    }

    @JsonIgnore()
    public String getAwayTeamId()
    {
        return awayTeamId;
    }

    public void setAwayTeam(TeamReference ref)
    {
        awayTeamId = ref == null ? null : ref.getId();
    }

    @JsonInclude(Include.NON_NULL)
    public PlayedMatch getPlayedMatch()
    {
        return filterOutcome(PlayedMatch.class);
    }

    @JsonInclude(Include.NON_NULL)
    public AwardedMatch getAwardedMatch()
    {
        return filterOutcome(AwardedMatch.class);
    }

    @JsonInclude(Include.NON_NULL)
    public UnplayedMatch getUnplayedMatch()
    {
        return filterOutcome(UnplayedMatch.class);
    }

    private <T extends Outcome> T filterOutcome(Class<T> type)
    {
        return outcome != null && type.isAssignableFrom(outcome.getClass()) ? type.cast(outcome)
                : null;
    }

    @JsonIgnore()
    public Outcome getOutcome()
    {
        return outcome;
    }

    public void setPlayedMatch(PlayedMatch playedMatch)
    {
        outcome = playedMatch;
    }

    public void setAwardedMatch(AwardedMatch awardedMatch)
    {
        outcome = awardedMatch;
    }

    public void setUnplayedMatch(UnplayedMatch unplayedMatch)
    {
        outcome = unplayedMatch;
    }

    public Completeness getCompleteness(Rules rules)
    {
        return dateTime == null || outcome == null ? Completeness.INCOMPLETE
                : outcome.getCompleteness(rules);
    }

    public String getOpponentId(String teamId)
    {
        return Stream
                .of(homeTeamId, awayTeamId)
                .filter(id -> !id.equals(teamId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString()
    {
        return "Match [dateTime=" + dateTime + ", court=" + court + ", homeTeamId=" + homeTeamId
                + ", awayTeamId=" + awayTeamId + ", outcome=" + outcome + "]";
    }
}
