/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.ui.validation;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import com.puppetlabs.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

/**
 * A potential problems advisor based on preference settings.
 * Note, that the preferences are read when this advisor is instantiated. Get a new instance when a preference has
 * changed.
 * 
 * 
 */
public class PreferenceBasedPotentialProblemsAdvisor implements IPotentialProblemsAdvisor {

	private PPPreferencesHelper preferences;

	@Inject
	public PreferenceBasedPotentialProblemsAdvisor(PPPreferencesHelper preferences) {
		this.preferences = preferences;
	}

	@Override
	public ValidationPreference assignmentToVarNamedString() {
		return preferences.getAssignmentToVariableNamedString();
	}

	@Override
	public ValidationPreference assignmentToVarNamedTrusted() {
		return preferences.getAssignmentToVariableNamedTrusted();
	}

	@Override
	public ValidationPreference booleansInStringForm() {
		return preferences.getBooleansInStringForm();
	}

	@Override
	public ValidationPreference caseDefaultShouldAppearLast() {
		return preferences.getCaseDefaultShouldAppearLast();
	}

	@Override
	public ValidationPreference circularDependencyPreference() {
		return preferences.getcircularDependencyPreference();
	}

	@Override
	public ValidationPreference dqStringNotRequired() {
		return preferences.getDqStringNotRequired();
	}

	@Override
	public ValidationPreference dqStringNotRequiredVariable() {
		return preferences.getDqStringNotRequiredVar();
	}

	/**
	 * @see com.puppetlabs.geppetto.pp.dsl.validation.IStylisticProblemsAdvisor#ensureShouldAppearFirstInResource()
	 */
	@Override
	public ValidationPreference ensureShouldAppearFirstInResource() {
		return preferences.getEnsureShouldAppearFirst();
	}

	@Override
	public ValidationPreference interpolatedNonBraceEnclosedHyphens() {
		return preferences.getInterpolatedNonBraceEnclosedHypens();
	}

	@Override
	public ValidationPreference missingDefaultInSelector() {
		return preferences.getMissingDefaultInSwitch();
	}

	@Override
	public ValidationPreference mlComments() {
		return preferences.getMLCommentsValidationPreference();
	}

	@Override
	public ValidationPreference rightToLeftRelationships() {
		return preferences.getRightToLeftRelationships();
	}

	@Override
	public ValidationPreference selectorDefaultShouldAppearLast() {
		return preferences.getSelectorDefaultShouldAppearLast();
	}

	@Override
	public ValidationPreference unbracedInterpolation() {
		return preferences.getUnbracedInterpolation();
	}

	@Override
	public ValidationPreference unquotedResourceTitles() {
		return preferences.getUnquotedResourceTitles();
	}

}
