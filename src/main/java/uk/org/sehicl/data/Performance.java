package uk.org.sehicl.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public abstract class Performance
{
    private String playerId;
    private String notes;

    @JacksonXmlProperty(localName = "player", isAttribute=true)
    public String getPlayerId()
    {
        return playerId;
    }

    @JacksonXmlProperty(localName = "player")
    public void setPlayerId(String playerId)
    {
        this.playerId = playerId;
    }

    @JsonInclude(Include.NON_NULL)
    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }
}
