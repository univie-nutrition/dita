package dita.globodiet.survey;

import java.util.List;
import java.util.function.Function;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.persistence.jdo.datanucleus.CausewayModulePersistenceJdoDatanucleus;
import org.apache.causeway.security.bypass.CausewayModuleSecurityBypass;

import lombok.NonNull;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({
    ModuleConfig.class,
    CausewayModuleCoreRuntimeServices.class,
    CausewayModuleSecurityBypass.class,
    CausewayModulePersistenceJdoDatanucleus.class,
})
@PropertySources({
    @PropertySource(CausewayPresets.H2InMemory_withUniqueSchema),
    @PropertySource(CausewayPresets.UseLog4j2Test),
})
public class DitaTestModuleGdSurvey {

    @Bean
    public SearchService searchService() {
        return new SearchService() {
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
