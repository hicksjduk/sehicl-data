package uk.org.sehicl.data;

import static org.assertj.core.api.Assertions.*;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

class MarshalUnmarshalTest
{
    static final String xmlFileName = "data/2010-11.xml";
    static final String ymlFileName = "data/2010-11.yml";

    InputStream dataFileStream(String fileName)
    {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

    @Test
    void testXmlToXml() throws Exception
    {
        var model = new XmlMapper().readValue(dataFileStream(xmlFileName), Model.class);
        var w = new StringWriter();
        try (w)
        {
            new XmlMapper().writeValue(w, model);
        }
        var diff = DiffBuilder
                .compare(Input.fromStream(dataFileStream(xmlFileName)))
                .withTest(Input.fromReader(new StringReader(w.toString())))
                .checkForSimilar()
                .build();
        assertThat(diff.hasDifferences()).isFalse();
    }
}
