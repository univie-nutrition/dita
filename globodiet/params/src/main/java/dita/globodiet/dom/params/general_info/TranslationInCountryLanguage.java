/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 */
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.general_info;

import jakarta.inject.Named;
import java.lang.String;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;

/**
 * Translation in country language
 */
@Named("dita.globodiet.params.general_info.TranslationInCountryLanguage")
@DomainObject
@DomainObjectLayout(
        describedAs = "Translation in country language"
)
@PersistenceCapable(
        table = "EPICTEXT"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class TranslationInCountryLanguage {
    /**
     * Text name
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Text name"
    )
    @Column(
            name = "ARRAY_REF",
            allowsNull = "true",
            length = 19
    )
    @Getter
    @Setter
    private String textName;

    /**
     * Text in specific Country language
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Text in specific Country language"
    )
    @Column(
            name = "TEXT",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String textInSpecificCountryLanguage;

    /**
     * Text in English
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Text in English"
    )
    @Column(
            name = "TEXT_ENG",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String textInEnglish;

    /**
     * Data entry Text in specific Country language
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Data entry Text in specific Country language"
    )
    @Column(
            name = "DE_TEXT",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String dataEntryTextInSpecificCountryLanguage;

    /**
     * Data entry Text in English
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Data entry Text in English"
    )
    @Column(
            name = "DE_TEXT_ENG",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String dataEntryTextInEnglish;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
