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
package dita.globodiet.survey.view;

import java.util.Optional;
import java.util.stream.Stream;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.LabelPosition;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.fa.FontAwesomeLayers;
import org.apache.causeway.applib.graph.tree.TreeAdapter;
import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.applib.graph.tree.TreePath;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import dita.globodiet.survey.DitaModuleGdSurvey;

@Named(DitaModuleGdSurvey.NAMESPACE + ".SurveyViewModel")
@DomainObject(
        nature=Nature.VIEW_MODEL)
@DomainObjectLayout(
        named = "Survey Introspection")
@Log4j2
public class SurveyVM implements ViewModel {

    public final static String PATH_DELIMITER = ".";

    @Getter @Programmatic
    private final SurveyTreeNode rootNode;

    @Getter @Programmatic
    private final SurveyTreeNode activeNode;

    public static SurveyVM forRoot(final SurveyTreeNode rootNode) {
        return new SurveyVM(rootNode, rootNode);
    }

    @Inject
    public SurveyVM(final SurveyTreeNode rootNode, final String rootPathMemento) {
        this(rootNode, TreePath.parse(rootPathMemento, PATH_DELIMITER));
    }

    SurveyVM(final SurveyTreeNode rootNode, final TreePath treePath) {
        this(rootNode, SurveyTreeNode
                .lookup(rootNode, treePath)
                .orElseGet(()->{
                    log.warn("could not resolve survey node {}", treePath);
                    return rootNode;
                }));
    }

    SurveyVM(final SurveyTreeNode rootNode, final SurveyTreeNode activeNode) {
        this.rootNode = rootNode;
        this.activeNode = activeNode;
    }

    @ObjectSupport public String title() {
        return activeNode.title();
    }

    @ObjectSupport public FontAwesomeLayers iconFaLayers() {
        return activeNode.faLayers();
    }

    @Override
    public String viewModelMemento() {
        return activeNode.path().stringify(PATH_DELIMITER);
    }

    public static class SurveyTreeAdapter implements TreeAdapter<SurveyVM> {
        @Override
        public Optional<SurveyVM> parentOf(final SurveyVM value) {
            return Optional.ofNullable(value.getParent());
        }
        @Override
        public int childCountOf(final SurveyVM value) {
            return value.activeNode.children().size();
        }
        @Override
        public Stream<SurveyVM> childrenOf(final SurveyVM value) {
            return value.activeNode.children().stream()
                    .map(childNode->new SurveyVM(value.rootNode, childNode));
        }
    }

    @Property
    @PropertyLayout(labelPosition = LabelPosition.NONE, fieldSetId = "tree", sequence = "1")
    public TreeNode<SurveyVM> getTree() {
        final TreeNode<SurveyVM> tree = TreeNode.lazy(
                SurveyVM.forRoot(rootNode), SurveyTreeAdapter.class);

        // expand the current node
        activeNode.path().streamUpTheHierarchyStartingAtSelf()
            .forEach(tree::expand);

        // mark active node as selected
        tree.select(activeNode.path());

        return tree;
    }

    @Property
    @PropertyLayout(navigable=Navigable.PARENT, hidden=Where.EVERYWHERE, fieldSetId = "detail", sequence = "1")
    public SurveyVM getParent() {
        return Optional.ofNullable(activeNode.path().getParentIfAny())
                .map(parentPath->new SurveyVM(rootNode, parentPath.toString()))
                .orElse(null);
    }

    @Property
    @PropertyLayout(labelPosition = LabelPosition.NONE, fieldSetId = "detail", sequence = "2")
    @Getter(lazy = true)
    private final AsciiDoc content = activeNode.content();

}
