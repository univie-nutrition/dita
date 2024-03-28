package dita.globodiet.survey;

import java.util.List;
import java.util.function.Function;

import org.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import org.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import org.causewaystuff.companion.applib.services.search.SearchService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;

import lombok.NonNull;

@Configuration
@Import({
    ModuleConfig.class,
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
