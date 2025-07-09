package uk.org.sehicl.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Player implements Comparable<Player>
{
    private String id;
    private String name;
    private String sortKey;

    @JacksonXmlProperty(isAttribute = true)
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        sortKey = getSortKey(name);
    }

    @Override
    public int compareTo(Player o)
    {
        return sortKey.compareToIgnoreCase(o.sortKey);
    }

    private String getSortKey(String name)
    {
        String[] split = name.split("\\s+", 2);
        String answer = split.length == 1 ? name : String.format("%s %s", split[1], split[0]);
        return answer;
    }

    @Override
    public String toString()
    {
        return "Player [id=" + id + ", name=" + name + ", sortKey=" + sortKey + "]";
    }
}
