package uk.org.sehicl.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "model")
public class Model
{
    private final List<League> leagues = new ArrayList<>();

    @JacksonXmlProperty(localName = "league")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<League> getLeagues()
    {
        return leagues;
    }

    @JacksonXmlProperty(localName = "league")
    public void addLeague(League league)
    {
        leagues.add(league);
    }

    public League getLeague(String leagueId)
    {
        League answer = null;
        for (League league : leagues)
        {
            if (leagueId.equals(league.getId()))
            {
                answer = league;
                break;
            }
        }
        return answer;
    }

    public Team getTeam(String teamId)
    {
        Team answer = null;
        for (League league : leagues)
        {
            if ((answer = league.getTeam(teamId)) != null)
            {
                break;
            }
        }
        return answer;
    }
    
    public Match getMatch(String teamId1, String teamId2)
    {
        Match answer = null;
        for (League league : leagues)
        {
            answer = league.getMatch(teamId1, teamId2);
            if (answer != null)
            {
                break;
            }
        }
        return answer;
    }
}
