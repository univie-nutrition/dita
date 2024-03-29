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
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedFunction;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import dita.globodiet.survey.DitaModuleGdSurvey;
import dita.globodiet.survey.dom.Campaign;

@Named(DitaModuleGdSurvey.NAMESPACE + ".SurveyViewModel")
@DomainObject(
        nature=Nature.VIEW_MODEL)
@DomainObjectLayout(
        named = "Survey Introspection")
@Log4j2
public class SurveyVM implements ViewModel {

    public final static String PATH_DELIMITER = ".";

    private record ViewModelMemento(
            Campaign.SecondaryKey campaignSecondaryKey,
            TreePath treePath) {
        static ViewModelMemento empty() {
            return new ViewModelMemento(new Campaign.SecondaryKey("", ""), TreePath.root());
        }
        static ViewModelMemento parse(final String viewModelMemento) {
            var parts = _Strings.splitThenStream(viewModelMemento, "|")
                    .collect(Can.toCan());
            if(parts.size()!=3) {
                return ViewModelMemento.empty();
            }
            parts = parts.map(_Strings::base64UrlDecode); // might throw on encoding errors
            return new ViewModelMemento(
                        new Campaign.SecondaryKey(parts.getElseFail(0), parts.getElseFail(1)),
                        TreePath.parse(parts.getElseFail(2), PATH_DELIMITER));
        }
        String stringify() {
            return _Strings.base64UrlEncode(campaignSecondaryKey.surveyCode())
                    + "|" + _Strings.base64UrlEncode(campaignSecondaryKey.code())
                    + "|" + _Strings.base64UrlEncode(treePath.stringify(PATH_DELIMITER));
        }
        public ViewModelMemento parent() {
            return new ViewModelMemento(campaignSecondaryKey, treePath.getParentIfAny());
        }
        public ViewModelMemento child(final int index) {
            return new ViewModelMemento(campaignSecondaryKey, treePath.append(index));
        }
    }


    @Getter @Programmatic
    private final SurveyTreeNode rootNode;

    @Getter @Programmatic
    private final SurveyTreeNode activeNode;

    @Getter @Programmatic
    private final ViewModelMemento viewModelMemento;

    public static SurveyVM forRoot(final Campaign.SecondaryKey campaignSecondaryKey, final SurveyTreeNode rootNode) {
        return new SurveyVM(new ViewModelMemento(campaignSecondaryKey, TreePath.root()), rootNode, rootNode);
    }

    @Inject
    public SurveyVM(
            final SurveyTreeRootNodeHelperService helper,
            final String viewModelMementoString) {
        this.viewModelMemento = ViewModelMemento.parse(viewModelMementoString);
        this.rootNode = helper.root(viewModelMemento.campaignSecondaryKey());
        this.activeNode = SurveyTreeNode
                .lookup(rootNode, viewModelMemento.treePath())
                .orElseGet(()->{
                    log.warn("could not resolve survey node {}", viewModelMemento.treePath());
                    return rootNode;
                });
    }

    public SurveyVM(final ViewModelMemento viewModelMemento, final SurveyTreeNode rootNode) {
        this(viewModelMemento, rootNode, SurveyTreeNode
                .lookup(rootNode, viewModelMemento.treePath())
                .orElseGet(()->{
                    log.warn("could not resolve survey node {}", viewModelMemento.treePath());
                    return rootNode;
                }));
    }

    SurveyVM(final ViewModelMemento viewModelMemento, final SurveyTreeNode rootNode, final SurveyTreeNode activeNode) {
        this.viewModelMemento = viewModelMemento;
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
        _Assert.assertEquals(activeNode.path().stringify(PATH_DELIMITER), viewModelMemento.treePath().stringify(PATH_DELIMITER));
        return viewModelMemento.stringify();
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
                .map(IndexedFunction.zeroBased((i, childNode)->
                    new SurveyVM(value.getViewModelMemento().child(i), value.rootNode, childNode)));
        }
    }

    @Property
    @PropertyLayout(labelPosition = LabelPosition.NONE, fieldSetId = "tree", sequence = "1")
    public TreeNode<SurveyVM> getTree() {
        final TreeNode<SurveyVM> tree = TreeNode.lazy(
                SurveyVM.forRoot(viewModelMemento.campaignSecondaryKey(), rootNode), SurveyTreeAdapter.class);

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
                .map(parentPath->new SurveyVM(viewModelMemento.parent(), rootNode))
                .orElse(null);
    }

    @Property
    @PropertyLayout(labelPosition = LabelPosition.NONE, fieldSetId = "detail", sequence = "2")
    @Getter(lazy = true)
    private final AsciiDoc content = activeNode.content();

}
