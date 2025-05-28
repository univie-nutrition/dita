package dita.globodiet.survey;

import java.util.List;
import java.util.function.Function;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.persistence.jpa.eclipselink.CausewayModulePersistenceJpaEclipselink;
import org.apache.causeway.security.bypass.CausewayModuleSecurityBypass;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({
    DitaModuleGdSurvey.class,
    CausewayModuleCoreRuntimeServices.class,
    CausewayModuleSecurityBypass.class,
    CausewayModulePersistenceJpaEclipselink.class,
})
@PropertySources({
    @PropertySource(CausewayPresets.H2InMemory_withUniqueSchema)
})
public class DitaTestModuleGdSurvey {

    @Bean
    public SearchService searchService() {
        return new SearchService() {
            @Override
            public <T> List<T> search(
                    @NonNull final Class<T> entityType,
                    @NonNull final Function<T, String> searchOn,
                    @Nullable final String searchString) {
                return List.of();
            }
        };
    }

    @Bean
    public ForeignKeyLookupService foreignKeyLookupService() {
        return new ForeignKeyLookupService() {

            @Override
            public <T> Can<ISecondaryKey<T>> decodeLookupKeyList(final Class<T> type, final String stringList) {
                return Can.empty();
            }

            @Override
            public int switchOn(final Object entity) {
                return 0;
            }

            @Override
            public <T> T unique(final ISecondaryKey<T> lookupKey) {
                return null;
            }

            @Override
            public <T> T nullable(final ISecondaryKey<T> lookupKey) {
                return null;
            }

            @Override
            public void clearCache(final Class<?>... types) {
            }

            @Override
            public void clearAllCaches() {
            }
        };

    }

}
