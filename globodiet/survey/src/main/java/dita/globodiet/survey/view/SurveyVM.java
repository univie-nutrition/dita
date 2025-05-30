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
import org.apache.causeway.applib.graph.tree.MasterDetailTreeView;
import org.apache.causeway.applib.graph.tree.TreeAdapter;
import org.apache.causeway.applib.graph.tree.TreePath;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.Getter;

import dita.globodiet.survey.DitaModuleGdSurvey;
import dita.globodiet.survey.dom.Survey;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.MemorizedFood24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Respondent24;

@Named(DitaModuleGdSurvey.NAMESPACE + ".SurveyViewModel")
@DomainObject(
        nature=Nature.VIEW_MODEL)
@DomainObjectLayout(
        named = "Survey Introspection")
public class SurveyVM extends MasterDetailTreeView<RecallNode24, SurveyVM> {

    public final static String PATH_DELIMITER = ".";

    // -- FACTORIES

    public static SurveyVM forRoot(
            final InspectionContext inspectionContext,
            final RecallNode24 rootNode) {
        return forTreePath(inspectionContext, rootNode, TreePath.root());
    }

    public static SurveyVM forTreePath(
        final InspectionContext inspectionContext,
            final RecallNode24 rootNode,
            final TreePath treePath) {
        return new SurveyVM(new ViewModelMemento(inspectionContext, treePath), rootNode);
    }

    // -- RECONSTRUCTION

    private record ViewModelMemento(
            InspectionContext inspectionContext,
            TreePath treePath) {
        static ViewModelMemento empty() {
            return new ViewModelMemento(InspectionContext.empty(), TreePath.root());
        }
        static ViewModelMemento parse(final String viewModelMemento) {
            var parts = _Strings.splitThenStream(viewModelMemento, "|")
                    .collect(Can.toCan());
            if(parts.size()!=3) {
                return ViewModelMemento.empty();
            }
            parts = parts.map(_Strings::base64UrlDecode); // might throw on encoding errors
            return new ViewModelMemento(
                        new InspectionContext(parts.getElseFail(0), parts.getElseFail(1)),
                        TreePath.parse(parts.getElseFail(2), PATH_DELIMITER));
        }
        String stringify() {
            return _Strings.base64UrlEncode(inspectionContext.surveyCode())
                    + "|" + _Strings.base64UrlEncode(inspectionContext.respondentFilterCode())
                    + "|" + _Strings.base64UrlEncode(treePath.stringify(PATH_DELIMITER));
        }
        public ViewModelMemento parent() {
            return new ViewModelMemento(inspectionContext, treePath.parent().orElse(null));
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
        this(viewModelMemento, helper.root(viewModelMemento.inspectionContext()));
    }

    SurveyVM(final ViewModelMemento viewModelMemento, final RecallNode24 rootNode) {
        this(viewModelMemento, rootNode, viewModelMemento.treePath());
    }

    SurveyVM(
            final ViewModelMemento viewModelMemento,
            final RecallNode24 rootNode,
            final TreePath treePath) {
        super(RecallNode24.class, rootNode, treePath);
        this.viewModelMemento = viewModelMemento;
    }

    // -- OBJECT SUPPORT & PROPERTIES

    @Property
    @PropertyLayout(navigable=Navigable.PARENT, hidden=Where.EVERYWHERE)
    public Object getParent() {
        return activeTreePath().parent()
                .<Object>map(_->new SurveyVM(viewModelMemento.parent(), rootNode()))
                .orElseGet(this::getSurvey);
    }

    @PropertyLayout(hidden=Where.EVERYWHERE)
    @Getter(lazy = true)
    private final Survey survey = SurveyTreeHelperService.instance()
        .survey(viewModelMemento.inspectionContext())
        .orElse(null);

    private InterviewSet24 interviewSet() {
        return (InterviewSet24) rootNode();
    }

    @ObjectSupport public String title() {
        var node = activeNode();
        return switch (node) {
        case InterviewSet24 interviewSet -> SurveyTreeNodeContentFactory.title(interviewSet, getSurvey());
        case Respondent24 respondent -> SurveyTreeNodeContentFactory.title(respondent, DataUtil.messages(interviewSet()));
        case Interview24 interview -> SurveyTreeNodeContentFactory.title(interview);
        case Meal24 meal -> SurveyTreeNodeContentFactory.title(meal);
        case MemorizedFood24 mem -> SurveyTreeNodeContentFactory.title(mem);
        case Record24 rec -> SurveyTreeNodeContentFactory.title(rec);
        };
    }

    @SuppressWarnings("unused")
    @ObjectSupport public FontAwesomeLayers iconFaLayers() {
        var node = activeNode();
        return FontAwesomeLayers.fromQuickNotation(
            switch (node) {
            case InterviewSet24 interviewSet -> SurveyTreeNodeContentFactory.icon(interviewSet);
            case Respondent24 respondent -> SurveyTreeNodeContentFactory.icon(respondent, DataUtil.messages(interviewSet()));
            case Interview24 interview -> SurveyTreeNodeContentFactory.icon(interview);
            case Meal24 meal -> SurveyTreeNodeContentFactory.icon(meal);
            case MemorizedFood24 mem -> "regular lightbulb";
            case Record24 rec -> "regular file-lines";
//            case Ingredient24.Dto ingr -> "solid plate-wheat";
            });
    }

    @Property
    @PropertyLayout(labelPosition = LabelPosition.NONE, fieldSetId = "detail", sequence = "2")
    public AsciiDoc getContent() {
        var node = activeNode();
        return switch (node) {
            case InterviewSet24 interviewSet -> SurveyTreeNodeContentFactory.content(interviewSet, getSurvey());
            case Respondent24 respondent -> SurveyTreeNodeContentFactory.content(respondent);
            case Interview24 interview -> SurveyTreeNodeContentFactory.content(interview);
            case Meal24 meal -> SurveyTreeNodeContentFactory.content(meal);
            case MemorizedFood24 mem -> SurveyTreeNodeContentFactory.content(mem);
            case Record24 rec -> SurveyTreeNodeContentFactory.content(rec);
        };
    }

    // -- TREENODE-VM STUFF

    @Override
    protected SurveyVM viewModel(final RecallNode24 node, final SurveyVM parentNode, final int siblingIndex) {
        return node instanceof InterviewSet24
                ? SurveyVM.forRoot(viewModelMemento.inspectionContext(), rootNode())
                : SurveyVM.forTreePath(viewModelMemento.inspectionContext(), rootNode(),
                        parentNode.activeTreePath().append(siblingIndex));
    }

    public static class SurveyTreeAdapter implements TreeAdapter<RecallNode24> {
        @Override public int childCountOf(final RecallNode24 node) {
            return switch (node) {
            case InterviewSet24 interviewSet -> interviewSet.respondents().size();
            case Respondent24 respondent -> respondent.interviews().size();
            case Interview24 interview -> interview.meals().size();
            case Meal24 meal -> meal.memorizedFood().size();
            case MemorizedFood24 mem -> mem.topLevelRecords().size();
            case Record24.Composite comp -> comp.subRecords().size();
            default -> 0;
            };
        }
        @Override public Stream<RecallNode24> childrenOf(final RecallNode24 node) {
            return switch (node) {
            case InterviewSet24 interviewSet -> interviewSet.respondents().stream().map(RecallNode24.class::cast);
            case Respondent24 respondent -> respondent.interviews().stream().map(RecallNode24.class::cast);
            case Interview24 interview -> interview.meals().stream().map(RecallNode24.class::cast);
            case Meal24 meal -> meal.memorizedFood().stream().map(RecallNode24.class::cast);
            case MemorizedFood24 mem -> mem.topLevelRecords().stream().map(RecallNode24.class::cast);
            case Record24.Composite comp -> comp.subRecords().stream().map(RecallNode24.class::cast);
            default -> Stream.empty();
            };
        }
    }

    @Override
    protected TreeAdapter<RecallNode24> treeAdapter() {
        return new SurveyTreeAdapter();
    }

    RecallNode24 recallNode() {
        return activeNode();
    }

}
