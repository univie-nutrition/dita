package dita.globodiet.manager;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;

public class CamelCaseSessionCustomizer implements SessionCustomizer {
    @Override
    public void customize(final Session session) {
        for (ClassDescriptor descriptor : session.getDescriptors().values()) {
            descriptor.getFields().forEach(field -> {
                String camelCaseName = convertToCamelCase(field.getName());
                field.setName(camelCaseName);
            });
        }
    }

    private String convertToCamelCase(final String name) {

        System.err.printf("!!! name %s%n", name);

        return name;
    }
}

