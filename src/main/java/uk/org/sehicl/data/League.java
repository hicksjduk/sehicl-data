package uk.org.sehicl.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder(value =
{ "name", "teamsPromoted", "teamsRelegated", "team", "match", "tableNotes" })
public class League implements Comparable<League>
{
    private static Comparator<League> comparator(String name)
    {
        boolean senior = name.charAt(0) != 'C';
        return Comparator.comparing(League::getName,
                Comparator.comparingInt((String n) -> n.charAt(0)).reversed().thenComparing(
                        (String n) -> n.charAt(n.length() - 1), senior ? Comparator.naturalOrder()
                                : Comparator.reverseOrder()));
    }

    private String id;
    private String name;
    private final List<Team> teams = new ArrayList<>();
    private int teamsPromoted;
    private int teamsRelegated;
    private final List<Match> matches = new ArrayList<>();
    private String tableNotes;
    private Comparator<League> comparator;

    @JacksonXmlProperty(localName = "team")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Team> getTeams()
    {
        return teams;
    }

    @JacksonXmlProperty(localName = "team")
    public void addTeam(Team team)
    {
        teams.add(team);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        this.comparator = comparator(name);
    }

    @JacksonXmlProperty(isAttribute = true)
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getTeamsPromoted()
    {
        return teamsPromoted;
    }

    public void setTeamsPromoted(int teamsPromoted)
    {
        this.teamsPromoted = teamsPromoted;
    }

    public int getTeamsRelegated()
    {
        return teamsRelegated;
    }

    public void setTeamsRelegated(int teamsRelegated)
    {
        this.teamsRelegated = teamsRelegated;
    }

    @JacksonXmlProperty(localName = "match")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Match> getMatches()
    {
        return matches;
    }

    @JacksonXmlProperty(localName = "match")
    public void addMatch(Match match)
    {
        matches.add(match);
    }

    public String getTableNotes()
    {
        return tableNotes;
    }

    public void setTableNotes(String tableNotes)
    {
        this.tableNotes = tableNotes;
    }

    @Override
    public int compareTo(League o)
    {
        return comparator.compare(this, o);
    }

    public Team getTeam(String teamId)
    {
        Team answer = null;
        for (Team team : teams)
        {
            if (teamId.equals(team.getId()))
            {
                answer = team;
                break;
            }
        }
        return answer;
    }

    public Match getMatch(String teamId1, String teamId2)
    {
        Match answer = null;
        if (getTeam(teamId1) != null && getTeam(teamId2) != null)
        {
            final Collection<String> teamIds = new TreeSet<>(Arrays.asList(teamId1, teamId2));
            for (Match match : matches)
            {
                if (teamIds.equals(
                        new TreeSet<>(Arrays.asList(match.getHomeTeamId(), match.getAwayTeamId()))))
                {
                    answer = match;
                    break;
                }
            }
        }
        return answer;
    }
}
