package uk.org.sehicl.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder(value =
{ "name", "excludedFromTables", "fixturePos", "pointsDeduction", "player" })
public class Team implements Comparable<Team>
{
    private String id;
    private String name;
    private String sortKey;
    private final List<Player> players = new ArrayList<>();
    private Integer fixturePos;
    private final List<PointsDeduction> pointsDeductions = new ArrayList<>();
    private boolean excludedFromTables;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        this.sortKey = name.replaceAll("\\W+", "");
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

    @JacksonXmlProperty(localName = "player")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Player> getPlayers()
    {
        return players;
    }

    @JacksonXmlProperty(localName = "player")
    public void addPlayer(Player player)
    {
        players.add(player);
    }

    @JsonInclude(Include.NON_NULL)
    public Integer getFixturePos()
    {
        return fixturePos;
    }

    public void setFixturePos(Integer fixturePos)
    {
        this.fixturePos = fixturePos;
    }

    @JacksonXmlProperty(localName = "pointsDeduction")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<PointsDeduction> getPointsDeductions()
    {
        return pointsDeductions;
    }

    @JacksonXmlProperty(localName = "pointsDeduction")
    public void addPointsDeduction(PointsDeduction pointsDeduction)
    {
        this.pointsDeductions.add(pointsDeduction);
    }

    @JacksonXmlProperty(localName = "excludedFromTables")
    @JsonInclude(Include.NON_NULL)
    private String getExcludedFromTables()
    {
        return excludedFromTables ? "" : null;
    }

    public boolean isExcludedFromTables()
    {
        return excludedFromTables;
    }

    public void setExcludedFromTables(String excludedFromTables)
    {
        this.excludedFromTables = true;
    }

    public Player getPlayer(String playerId)
    {
        Player answer = null;
        for (Player player : players)
        {
            if (playerId.equals(player.getId()))
            {
                answer = player;
                break;
            }
        }
        return answer;
    }

    @Override
    public int compareTo(Team o)
    {
        return sortKey.compareToIgnoreCase(o.sortKey);
    }
}
