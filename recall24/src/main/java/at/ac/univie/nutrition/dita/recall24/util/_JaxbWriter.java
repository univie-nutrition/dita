package at.ac.univie.nutrition.dita.recall24.util;

import java.io.StringWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.JaxbUtils;

import at.ac.univie.nutrition.dita.recall24.dto.InterviewSetDto;
import lombok.SneakyThrows;

class _JaxbWriter {

    private final JAXBContext jaxbContext;
    private final Marshaller jaxbMarshaller;

    @SneakyThrows
    _JaxbWriter() {
        jaxbContext = JaxbUtils.jaxbContextFor(InterviewSetDto.class, true);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }

    Try<String> tryToString(final InterviewSetDto survey) {
        return Try.call(()->{
            final var stringWriter = new StringWriter();
            jaxbMarshaller.marshal(survey, stringWriter);
            return stringWriter.toString();
        });
    }

    Try<Void> tryWriteTo(final InterviewSetDto survey, final DataSink dataSink) {
        return Try.run(()->{
            dataSink.writeAll(os->{
                jaxbMarshaller.marshal(survey, os);
            });
        });
    }

}
