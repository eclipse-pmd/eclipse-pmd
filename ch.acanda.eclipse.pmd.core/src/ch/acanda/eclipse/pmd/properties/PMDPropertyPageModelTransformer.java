// =====================================================================
//
// Copyright (C) 2012 - 2020, Philip Graf
//
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// =====================================================================

package ch.acanda.eclipse.pmd.properties;

import static ch.acanda.eclipse.pmd.domain.LocationContext.FILE_SYSTEM;
import static ch.acanda.eclipse.pmd.domain.LocationContext.PROJECT;
import static ch.acanda.eclipse.pmd.domain.LocationContext.REMOTE;
import static ch.acanda.eclipse.pmd.domain.LocationContext.WORKSPACE;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;

import ch.acanda.eclipse.pmd.builder.LocationResolver;
import ch.acanda.eclipse.pmd.domain.Location;
import ch.acanda.eclipse.pmd.domain.LocationContext;
import ch.acanda.eclipse.pmd.domain.RuleSetModel;
import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;

/**
 * Transforms the domain model to and from the PMD property page's view model.
 *
 * @author Philip Graf
 */
public final class PMDPropertyPageModelTransformer {

    /**
     * Maps a location context to a label of a rule set type and vice versa. The label is used as the value shown in the
     * "Type" column of the table in the PMD property page.
     */
    private static final Map<LocationContext, String> CONTEXT_TYPE_MAP = Map.of(WORKSPACE,
            "Workspace",
            PROJECT, "Project",
            FILE_SYSTEM, "File System",
            REMOTE, "Remote");

    private static final Map<String, LocationContext> TYPE_CONTEXT_MAP = CONTEXT_TYPE_MAP.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getValue(), e -> e.getKey()));

    private PMDPropertyPageModelTransformer() {
        // hide constructor of utility class
    }

    /**
     * Returns an iterable transforming a rule set domain model to a rule set view model on demand, i.e. the returned
     * iterable is a view on the supplied iterable.
     */
    public static Set<? extends RuleSetViewModel> toViewModels(final SortedSet<RuleSetModel> domainModels, final IProject project) {
        return domainModels.stream().map(model -> toViewModel(model, project)).collect(Collectors.toSet());
    }

    /**
     * Transforms a rule set domain model to a rule set view model.
     */
    public static RuleSetViewModel toViewModel(final RuleSetModel ruleSetModel, final IProject project) {
        final String name = ruleSetModel.getName();
        final String type = CONTEXT_TYPE_MAP.get(ruleSetModel.getLocation().getContext());
        final String location = ruleSetModel.getLocation().getPath();
        final boolean isValidLocation = LocationResolver.resolveIfExists(ruleSetModel.getLocation(), project).isPresent();
        final String resolvedLocation = LocationResolver.resolve(ruleSetModel.getLocation(), project);
        return new RuleSetViewModel(name, type, location, isValidLocation, resolvedLocation);
    }

    /**
     * Returns an iterable transforming a rule set view model to a rule set domain model.
     */
    public static List<? extends RuleSetModel> toDomainModels(final Collection<? extends RuleSetViewModel> viewModels) {
        return viewModels.stream().map(vm -> toDomainModel(vm)).collect(Collectors.toList());
    }

    /**
     * Transforms a rule set view model to a rule set domain model.
     */
    public static RuleSetModel toDomainModel(final RuleSetViewModel viewModel) {
        final String name = viewModel.getName();
        final String path = viewModel.getLocation();
        final LocationContext context = TYPE_CONTEXT_MAP.get(viewModel.getType());
        return new RuleSetModel(name, new Location(path, context));
    }

}
