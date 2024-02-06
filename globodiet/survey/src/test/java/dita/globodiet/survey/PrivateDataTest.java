package dita.globodiet.survey;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("/secret/test.properties")
@DisabledIfSystemProperty(named = "isRunningWithSurefire", matches = "true")
@Retention(RUNTIME)
@Target(TYPE)
public @interface PrivateDataTest {
}
