package uk.org.sehicl.data;

import static org.assertj.core.api.Assertions.*;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.Comparison.Detail;
import org.xmlunit.diff.ComparisonResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

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
        new XmlMapper().configure(SerializationFeature.CLOSE_CLOSEABLE, true).writeValue(w, model);
        var diff = DiffBuilder
                .compare(Input.fromStream(dataFileStream(xmlFileName)))
                .withTest(Input.fromReader(new StringReader(w.toString())))
                .withDifferenceEvaluator(this::overrideBatterBatsmanDifference)
                .checkForSimilar()
                .build();
        assertThat(!diff.hasDifferences());
    }

    ComparisonResult overrideBatterBatsmanDifference(Comparison comparison,
            ComparisonResult outcome)
    {
        if (outcome == ComparisonResult.DIFFERENT)
            if (isBatterBatsmanDifference(Stream
                    .of(comparison.getControlDetails(), comparison.getTestDetails())
                    .map(Detail::getValue)
                    .map(Optional::ofNullable)
                    .map(o -> o.map(Object::toString).orElse("null"))))
                return ComparisonResult.SIMILAR;
        return outcome;
    }

    boolean isBatterBatsmanDifference(Stream<String> values)
    {
        var pattern = Pattern.compile(".*bat(ters?|sm[ae]n).*");
        return values.map(pattern::matcher).allMatch(Matcher::matches);
    }

    @Test
    void testXmlToYml() throws Exception
    {
        var model = new XmlMapper().readValue(dataFileStream(xmlFileName), Model.class);
        var w = new StringWriter();
        new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER))
                .configure(SerializationFeature.CLOSE_CLOSEABLE, true)
                .writeValue(w, model);
        var expectedLines = new String(dataFileStream(ymlFileName).readAllBytes()).lines();
        var actualLines = w.toString().lines();
        compareLineByLine(expectedLines, actualLines, (exp, act, msg) ->
        {
            try
            {
                assertThat(exp).as(msg).isEqualTo(act);
            }
            catch (AssertionFailedError ex)
            {
                if (!isBatterBatsmanDifference(Stream.of(act, exp)))
                    throw ex;
            }
        });
    }
    
    interface Comparer {
        void compare(String expected, String actual, String message);
    }

    void compareLineByLine(Stream<String> expected, Stream<String> actual,
            Comparer comparer)
    {
        var actualLines = actual.iterator();
        var lineNo = new AtomicInteger();
        expected.forEach(exp ->
        {
            var msg = "On line %d".formatted(lineNo.incrementAndGet());
            var act = actualLines.next();
            comparer.compare(exp, act, msg);
        });
        assertThat(!actualLines.hasNext());
    }
}
