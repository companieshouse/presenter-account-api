package uk.gov.companieshouse.presenter.account.serialiser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.serializer.SerializerException;

import uk.gov.companieshouse.presenter_account.PresenterCreated;

@Component
public class PresenterCreatedSerialiser {

    public byte[] serialize(final PresenterCreated data) {
        // Copy from kafka-models README
        final DatumWriter<PresenterCreated> datumWriter = new SpecificDatumWriter<>();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            final Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            datumWriter.setSchema(data.getSchema());
            datumWriter.write(data, encoder);
            encoder.flush();

            final byte[] serializedData = out.toByteArray();
            encoder.flush();

            return serializedData;
        } catch (final IOException ex) {
            throw new SerializerException(ex.getMessage());
        }
    }

}
