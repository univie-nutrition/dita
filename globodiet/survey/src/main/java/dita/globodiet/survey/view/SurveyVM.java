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
import org.apache.causeway.applib.graph.tree.TreePath;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.Getter;

import dita.globodiet.survey.DitaModuleGdSurvey;
import dita.globodiet.survey.dom.Campaign;
import dita.recall24.immutable.Ingredient;
import dita.recall24.immutable.Interview;
import dita.recall24.immutable.InterviewSet;
import dita.recall24.immutable.Meal;
import dita.recall24.immutable.MemorizedFood;
import dita.recall24.immutable.RecallNode;
import dita.recall24.immutable.Record;
import dita.recall24.immutable.Respondent;
import io.github.causewaystuff.treeview.applib.viewmodel.TreeNodeVm;

@Named(DitaModuleGdSurvey.NAMESPACE + ".SurveyViewModel")
@DomainObject(
        nature=Nature.VIEW_MODEL)
@DomainObjectLayout(
        named = "Survey Introspection")
//@Log4j2
public class SurveyVM extends TreeNodeVm<RecallNode, SurveyVM> {

    public final static String PATH_DELIMITER = ".";

    // -- FACTORIES

    public static SurveyVM forRoot(
            final Campaign.SecondaryKey campaignSecondaryKey,
            final RecallNode rootNode) {
        return forTreePath(campaignSecondaryKey, rootNode, TreePath.root());
    }

    public static SurveyVM forTreePath(
            final Campaign.SecondaryKey campaignSecondaryKey,
            final RecallNode rootNode,
            final TreePath treePath) {
        return new SurveyVM(new ViewModelMemento(campaignSecondaryKey, treePath), rootNode);
    }

    // -- RECONSTRUCTION

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
    }

    @Override
    public String viewModelMemento() {
        _Assert.assertEquals(activeTreePath().stringify(PATH_DELIMITER), viewModelMemento.treePath().stringify(PATH_DELIMITER));
        return viewModelMemento.stringify();
    }

    // -- CONSTRUCTION

    @Getter @Programmatic
    private final ViewModelMemento viewModelMemento;

    @Inject
    public SurveyVM(
            final SurveyTreeHelperService helper,
            final String viewModelMementoString) {
        this(helper, ViewModelMemento.parse(viewModelMementoString));
    }

    SurveyVM(
            final SurveyTreeHelperService helper,
            final ViewModelMemento viewModelMemento) {
        this(viewModelMemento, helper.root(viewModelMemento.campaignSecondaryKey()));
    }

    SurveyVM(final ViewModelMemento viewModelMemento, final RecallNode rootNode) {
        this(viewModelMemento, rootNode, viewModelMemento.treePath());
    }

    SurveyVM(
            final ViewModelMemento viewModelMemento,
            final RecallNode rootNode,
            final TreePath treePath) {
        super(RecallNode.class, rootNode, treePath);
        this.viewModelMemento = viewModelMemento;
    }

    // -- OBJECT SUPPORT & PROPERTIES

    @Property
    @PropertyLayout(navigable=Navigable.PARENT, hidden=Where.EVERYWHERE)
    public Object getParent() {
        return Optional.ofNullable(activeTreePath().getParentIfAny())
                .<Object>map(parentPath->new SurveyVM(viewModelMemento.parent(), rootNode()))
                .orElseGet(this::getCampaign);
    }

    @PropertyLayout(hidden=Where.EVERYWHERE)
    @Getter(lazy = true)
    private final Campaign campaign = SurveyTreeHelperService.instance()
        .campaign(viewModelMemento.campaignSecondaryKey());

    @ObjectSupport public String title() {
        var node = activeNode();
        return switch (node) {
        case InterviewSet interviewSet -> SurveyTreeNodeContentFactory.title(interviewSet, getCampaign());
        case Respondent respondent -> SurveyTreeNodeContentFactory.title(respondent);
        case Interview interview -> SurveyTreeNodeContentFactory.title(interview);
        case Meal meal -> SurveyTreeNodeContentFactory.title(meal);
        case MemorizedFood mem -> SurveyTreeNodeContentFactory.title(mem);
        case Record rec -> SurveyTreeNodeContentFactory.title(rec);
        case Ingredient ingr -> SurveyTreeNodeContentFactory.title(ingr);
        };
    }

    @ObjectSupport public FontAwesomeLayers iconFaLayers() {
        var node = activeNode();
        return FontAwesomeLayers.fromQuickNotation(
            switch (node) {
            case InterviewSet interviewSet -> SurveyTreeNodeContentFactory.icon(interviewSet);
            case Respondent respondent -> SurveyTreeNodeContentFactory.icon(respondent);
            case Interview interview -> SurveyTreeNodeContentFactory.icon(interview);
            case Meal meal -> SurveyTreeNodeContentFactory.icon(meal);
            case MemorizedFood mem -> "regular lightbulb";
            case Record rec -> "regular file-lines";
            case Ingredient ingr -> "solid plate-wheat";
            });
    }

    @Property
    @PropertyLayout(labelPosition = LabelPosition.NONE, fieldSetId = "detail", sequence = "2")
    public AsciiDoc getContent() {
        var node = activeNode();
        return switch (node) {
        case InterviewSet interviewSet -> SurveyTreeNodeContentFactory.content(interviewSet, getCampaign());
        case Respondent respondent -> SurveyTreeNodeContentFactory.content(respondent);
        case Interview interview -> SurveyTreeNodeContentFactory.content(interview);
        case Meal meal -> SurveyTreeNodeContentFactory.content(meal);
        case MemorizedFood mem -> SurveyTreeNodeContentFactory.content(mem);
        case Record rec -> SurveyTreeNodeContentFactory.content(rec);
        case Ingredient ingr -> SurveyTreeNodeContentFactory.content(ingr);
        };
    }

    // -- TREENODE-VM STUFF

    @Override
    protected SurveyVM getViewModel(final RecallNode node, final SurveyVM parentNode, final int siblingIndex) {
        return node instanceof InterviewSet
                ? SurveyVM.forRoot(viewModelMemento.campaignSecondaryKey(), rootNode())
                : SurveyVM.forTreePath(viewModelMemento.campaignSecondaryKey(), rootNode(),
                        parentNode.activeTreePath().append(siblingIndex));
    }

    public static class SurveyTreeAdapter implements TreeAdapter<RecallNode> {
        @Override public int childCountOf(final RecallNode node) {
            return switch (node) {
            case InterviewSet interviewSet -> interviewSet.respondents().size();
            case Respondent respondent -> respondent.interviews().size();
            case Interview interview -> interview.meals().size();
            case Meal meal -> meal.memorizedFood().size();
            case MemorizedFood mem -> mem.topLevelRecords().size();
            case Record rec -> rec.ingredients().size();
            case Ingredient ingr -> 0;
            };
        }
        @Override public Stream<RecallNode> childrenOf(final RecallNode node) {
            return switch (node) {
            case InterviewSet interviewSet -> interviewSet.respondents().stream().map(RecallNode.class::cast);
            case Respondent respondent -> respondent.interviews().stream().map(RecallNode.class::cast);
            case Interview interview -> interview.meals().stream().map(RecallNode.class::cast);
            case Meal meal -> meal.memorizedFood().stream().map(RecallNode.class::cast);
            case MemorizedFood mem -> mem.topLevelRecords().stream().map(RecallNode.class::cast);
            case Record rec -> rec.ingredients().stream().map(RecallNode.class::cast);
            case Ingredient ingr -> Stream.empty();
            };
        }
    }

    @Override
    protected TreeAdapter<RecallNode> getTreeAdapter() {
        return new SurveyTreeAdapter();
    }

}
