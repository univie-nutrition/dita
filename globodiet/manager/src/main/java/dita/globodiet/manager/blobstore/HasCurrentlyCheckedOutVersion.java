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
package dita.globodiet.manager.blobstore;

import java.util.Optional;

import org.apache.causeway.applib.annotation.Programmatic;

import lombok.val;

/**
 * Marker interface for the corresponding mixin to kick in.
 */
public interface HasCurrentlyCheckedOutVersion {

    @Programmatic
    default Optional<String> guardAgainstCannotEditVersion(final BlobStore blobStore){
        val currentVersion = blobStore.getCurrentlyCheckedOutVersion();
        return currentVersion==null
                ? Optional.of("Editing disabled, because currently there is no version checked out.")
                : currentVersion.isCommitted()
                        ? Optional.of("Editing disabled, because the currently checked out version is already committed.")
                        : Optional.empty();
    }

}
