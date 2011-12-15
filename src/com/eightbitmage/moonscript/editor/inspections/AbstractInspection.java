/*
 * Copyright 2010 Jon S Akhtar (Sylvanaar)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.eightbitmage.moonscript.editor.inspections;

import com.eightbitmage.moonscript.lang.psi.statements.MoonStatementElement;
import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.codeInsight.daemon.HighlightDisplayKey;
import com.intellij.codeInsight.daemon.impl.actions.SuppressByCommentFix;
import com.intellij.codeInspection.CustomSuppressableInspectionTool;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.SuppressIntentionAction;
import com.intellij.codeInspection.SuppressionUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Jun 12, 2010
 * Time: 7:28:23 AM
 */
public abstract class AbstractInspection extends LocalInspectionTool implements CustomSuppressableInspectionTool {
    private static final SuppressIntentionAction[] EMPTY_ARRAY = new SuppressIntentionAction[0];


    protected static final String ASSIGNMENT_ISSUES = "Assignment issues";
    protected static final String CONFUSING_CODE_CONSTRUCTS = "Potentially confusing code constructs";
    protected static final String CONTROL_FLOW = "Control Flow";
    protected static final String PROBABLE_BUGS = "Probable bugs";
    protected static final String ERROR_HANDLING = "Error handling";
    protected static final String GPATH = "GPath inspections";
    protected static final String METHOD_METRICS = "Method Metrics";
    protected static final String PERFORMANCE_ISSUES = "Performance issues";
    protected static final String VALIDITY_ISSUES = "Validity issues";
    protected static final String ANNOTATIONS_ISSUES = "Annotations verifying";
    
    private static Pattern SUPPRESS_IN_LINE_COMMENT_PATTERN = Pattern.compile("--" + SuppressionUtil.COMMON_SUPPRESS_REGEXP);;

    @NotNull
    @Override
    public String[] getGroupPath() {
        return new String[]{"Lua", getGroupDisplayName()};
    }

    private final String m_shortName = null;

    @NotNull
    public String getShortName() {
        if (m_shortName == null) {
            final Class<? extends AbstractInspection> aClass = getClass();
            @NonNls final String name = aClass.getName();
            return name.substring(name.lastIndexOf((int) '.') + 1,
                    name.length() - "Inspection".length());
        }
        return m_shortName;
    }


//  @Nullable BaseInspectionVisitor buildLuaVisitor(@NotNull ProblemsHolder problemsHolder, boolean onTheFly) {
//    final BaseInspectionVisitor visitor = buildVisitor();
//    visitor.setProblemsHolder(problemsHolder);
//    visitor.setOnTheFly(onTheFly);
//    visitor.setInspection(this);
//    return visitor;
//  }
//  protected abstract BaseInspectionVisitor buildVisitor();
    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @NotNull
    public HighlightDisplayLevel getDefaultLevel() {
        return HighlightDisplayLevel.WEAK_WARNING;
    }

    public boolean isSuppressedFor(PsiElement element) {
        return  getElementToolSuppressedIn(element, getShortName()) != null;
    }

    public SuppressIntentionAction[] getSuppressActions(@Nullable PsiElement element) {
       return new  SuppressIntentionAction[] {
               new SuppressByCommentFix(HighlightDisplayKey.find(getShortName()), MoonStatementElement.class)
       };
    }

  @Nullable
  public static PsiElement getStatementToolSuppressedIn(final PsiElement place,
                                                        final String toolId,
                                                        final Class<? extends PsiElement> statementClass) {
    return SuppressionUtil.getStatementToolSuppressedIn(place, toolId, statementClass,
            SUPPRESS_IN_LINE_COMMENT_PATTERN);
  }

  @Nullable
  public PsiElement getElementToolSuppressedIn(@NotNull final PsiElement place, final String toolId) {
    return ApplicationManager.getApplication().runReadAction(new Computable<PsiElement>() {
      @Nullable
      public PsiElement compute() {
        final PsiElement statement = getStatementToolSuppressedIn(place, toolId, MoonStatementElement.class);
        if (statement != null) {
          return statement;
        }

        return null;
      }
    });
  }


    @Nls
    @NotNull
    public String getGroupDisplayName() {
        return "Moon";
    }

//    @Nullable
//    protected String buildErrorString(Object... args) {
//        return null;
//    }
//
//    protected boolean buildQuickFixesOnlyForOnTheFlyErrors() {
//        return false;
//    }
//
    @Nullable
    protected MoonFix buildFix(PsiElement location) {
        return null;
    }
//
//    @Nullable
//    protected MoonFix[] buildFixes(PsiElement location) {
//        return null;
//    }
    
}