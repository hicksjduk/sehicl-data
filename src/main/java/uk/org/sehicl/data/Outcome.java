package uk.org.sehicl.data;

import uk.org.sehicl.website.rules.Rules;

public interface Outcome
{
    Completeness getCompleteness(Rules rules);
}
