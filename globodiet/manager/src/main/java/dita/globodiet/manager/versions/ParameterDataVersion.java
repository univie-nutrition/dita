/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.globodiet.manager.versions;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.jspecify.annotations.NonNull;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.fa.FontAwesomeLayers;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import dita.globodiet.manager.DitaModuleGdManager;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".ParameterDataVersion")
@NoArgsConstructor
public class ParameterDataVersion {

    @Inject private VersionsService versionsService;
    @Inject private VersionsExportService versionsExportService;
    @Inject private FactoryService factoryService;

    // -- FACTORIES

    public static ParameterDataVersion fromDirectory(final @NonNull File dir) {
        var dataSource = DataSource.ofFile(new File(dir, "manifest.yml"));
        var paramDataVersion = YamlUtils.tryRead(ParameterDataVersion.class, dataSource)
            .valueAsNonNullElseFail();
        return paramDataVersion;
    }

    // -- IMPL

    @ObjectSupport
    public String title() {
        return String.format("Parameter-Data [%s]", getName());
    }

    @ObjectSupport
    public FontAwesomeLayers iconFaLayers() {
        return isSticky()
                ? FontAwesomeLayers.fromQuickNotation("file-shield .version-color-dark")
                : FontAwesomeLayers.fromQuickNotation("file-pen .version-color-light");
    }

    @Property
    @PropertyLayout(describedAs = "Autogenerated IDENTIFIER of this version.")
    @Getter @Setter
    private int __id;

    @Property
    @PropertyLayout(describedAs = "Arbitrary NAME of this version.")
    @Getter @Setter
    private String name;

    @Property
    @PropertyLayout(multiLine = 4)
    @Getter @Setter
    private String description;

    @Property
    @PropertyLayout(describedAs = "Creation Timestamp")
    @Getter @Setter
    private ZonedDateTime creationTime;

    @Property
    @PropertyLayout(describedAs = "system/version")
    @Getter @Setter
    private String systemId;

    @Property
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            describedAs = "If DELETED, does no longer appear in the user interface, "
            + "but can be RESTORED by an administrator.")
    @Getter @Setter
    private boolean deleted;

    @Property
    @PropertyLayout(describedAs = "If STICKY, cannot be deleted.")
    @Getter @Setter
    private boolean sticky;

    // -- BREADCRUMB

    @Property(snapshot = Snapshot.EXCLUDED)
    @PropertyLayout(hidden = Where.EVERYWHERE, navigable = Navigable.PARENT)
    public VersionsView getBlobStoreView() {
        return factoryService.viewModel(new VersionsView());
    }

    // -- [1] CHECKOUT

//    @Action
//    @ActionLayout(
//            describedAs = "Checkout this version for VIEWING or EDITING, "
//                    + "based on whether was already committed or not.",
//            sequence = "1",
//            fieldSetName="Details",
//            position = Position.PANEL,
//            cssClassFa = FontawesomeConstants.FA_CLOUD_ARROW_DOWN_SOLID,
//            cssClass = "btn-primary")
//    public String checkout() {
//        lookupService.clearAllCaches();
//        blobStore.checkout(this);
//        return String.format("Version '%s' checked out.", name);
//    }
//    @MemberSupport public String disableCheckout() {
//        if(true) return disableVersionManagement();
//        return guardAgainstDeleted()
//                .or(()->guardAgainstThisVersionAlreadyCheckedOut("This version is already checked out."))
//                .orElse(null);
//    }

    // -- [2] CLONE

//    @Action
//    @ActionLayout(
//            describedAs = "Clone this version for EDITING under a new IDENTITY.",
//            sequence = "2",
//            fieldSetName="Details",
//            position = Position.PANEL,
//            cssClassFa = FontawesomeConstants.FA_CLONE_SOLID)
//    public String clone(
//            @Parameter(optionality = Optionality.MANDATORY)
//            final String clonedVersionName,
//
//            @Parameter(optionality = Optionality.OPTIONAL)
//            @ParameterLayout(multiLine = 4)
//            final String clonedVersionDescription) {
//
//        var cloneRequest = new ParameterDataVersion();
//        cloneRequest.setName(clonedVersionName);
//        cloneRequest.setDescription(clonedVersionDescription);
//        cloneRequest.setCommitted(false);
//
//        blobStore.clone(this, cloneRequest);
//        return String.format("Version '%s' cloned as '%s'.", name, clonedVersionName);
//    }
//    @MemberSupport public String disableClone() {
//        if(true) return disableVersionManagement();
//        return guardAgainstDeleted()
//                .orElse(null);
//    }
//    @MemberSupport public String default0Clone() {
//        return String.format("%s (Clone)", getName());
//    }
//    @MemberSupport public String default1Clone() {
//        return _Strings.isNotEmpty(getDescription())
//                ? String.format("%s (Cloned)", getDescription())
//                : null;
//    }

    // -- [3] COMMIT

//    @Action
//    @ActionLayout(
//            sequence = "3",
//            named = "Commit / Generate BAK",
//            describedAs = "Generates a BAK file, "
//                    + "that is made available for download. "
//                    + "Also locks this version (disables further EDITING).",
//            fieldSetName="Details",
//            position = Position.PANEL,
//            cssClassFa = FontawesomeConstants.FA_CLOUD_ARROW_UP_SOLID,
//            cssClass = "btn-success")
//    public String commitAndGenerateBAK() {
//        //TODO (1) generate BAK file (2) on success transition to committed state
//        //TODO this is perhaps a long runner, need some other feedback mechanism
//        return String.format("Version '%s' committed and locked (further editing is disabled, can only be viewed). "
//                + "A BAK file was generated and made available for download.", name);
//    }
//    @MemberSupport public String disableCommitAndGenerateBAK() {
//        return guardAgainstDeleted() // just in case
//                .orElse("TODO: will be implemented");
//    }

    // -- [4] FOOD DESCRIPTION MODEL

    @Action
    @ActionLayout(
            sequence = "4.1",
            describedAs = "Food Description Model as YAML.",
            fieldSetName="Details",
            position = Position.PANEL)
    public Blob downloadFoodDescriptionModel() {
        return versionsExportService.getFoodDescriptionModelAsYaml(this);
    }

    @Action
    @ActionLayout(
            sequence = "4.2",
            describedAs = "Zip file containing QMAPS as YAML.",
            fieldSetName="Details",
            position = Position.PANEL)
    public Blob downloadSpecialDayAndOthers() {
        return versionsExportService.getSpecialDayAndOthers(this);
    }

    // -- [5] BAK DOWNLOAD

    @Action
    @ActionLayout(
            sequence = "5",
            describedAs = "MS-SQL Server backup file, that can be imported with the GloboDiet client application.",
            fieldSetName="Details",
            position = Position.PANEL)
    public Blob downloadBAK() {
        return versionsService.getBAK(this);
    }

    // -- [6] DELETE

    @Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            sequence = "6",
            describedAs = "Does not actually delete from blob-store, just changes the manifest, "
                    + "such that given version no longer appears in the user interface.",
            fieldSetName="Details",
            position = Position.PANEL)
    public String delete() {
        versionsService.delete(this);
        return String.format("Version '%s' was deleted, that is, it no longer appears in the user interface. "
                + "However, it can be restored by an administrator.", name);
    }
    @MemberSupport public String disableDelete() {
        return guardAgainstDeleted() // just in case
                .or(()->guardAgainstSticky("This version is marked STICKY by an administrator, hence cannot be deleted."))
                .orElse(null);
    }

    // -- UTILITY

    @Programmatic
    void writeManifest(final @NonNull File dir) {
        var dataSink = DataSink.ofFile(new File(dir, "manifest.yml"));
        YamlUtils.write(this, dataSink);
    }

    // -- HELPER

    Optional<String> guardAgainstDeleted() {
        return isDeleted()
            ? Optional.of("This version was deleted.")
            : Optional.empty();
    }

    Optional<String> guardAgainstSticky(final String message) {
        return isSticky()
            ? Optional.of(message)
            : Optional.empty();
    }

}
