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
package com.puppetlabs.geppetto.junitresult.util;

import com.puppetlabs.geppetto.junitresult.AbstractAggregatedTest;
import com.puppetlabs.geppetto.junitresult.Failure;
import com.puppetlabs.geppetto.junitresult.JunitresultPackage;
import com.puppetlabs.geppetto.junitresult.NegativeResult;
import com.puppetlabs.geppetto.junitresult.Property;
import com.puppetlabs.geppetto.junitresult.Skipped;
import com.puppetlabs.geppetto.junitresult.JunitResult;
import com.puppetlabs.geppetto.junitresult.Testcase;
import com.puppetlabs.geppetto.junitresult.Testrun;
import com.puppetlabs.geppetto.junitresult.Testsuite;
import com.puppetlabs.geppetto.junitresult.Testsuites;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * 
 * @see com.puppetlabs.geppetto.junitresult.JunitresultPackage
 * @generated
 */
public class JunitresultAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static JunitresultPackage modelPackage;

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected JunitresultSwitch<Adapter> modelSwitch = new JunitresultSwitch<Adapter>() {
		@Override
		public Adapter caseAbstractAggregatedTest(AbstractAggregatedTest object) {
			return createAbstractAggregatedTestAdapter();
		}

		@Override
		public Adapter caseError(com.puppetlabs.geppetto.junitresult.Error object) {
			return createErrorAdapter();
		}

		@Override
		public Adapter caseFailure(Failure object) {
			return createFailureAdapter();
		}

		@Override
		public Adapter caseJunitResult(JunitResult object) {
			return createJunitResultAdapter();
		}

		@Override
		public Adapter caseNegativeResult(NegativeResult object) {
			return createNegativeResultAdapter();
		}

		@Override
		public Adapter caseProperty(Property object) {
			return createPropertyAdapter();
		}

		@Override
		public Adapter caseSkipped(Skipped object) {
			return createSkippedAdapter();
		}

		@Override
		public Adapter caseTestcase(Testcase object) {
			return createTestcaseAdapter();
		}

		@Override
		public Adapter caseTestrun(Testrun object) {
			return createTestrunAdapter();
		}

		@Override
		public Adapter caseTestsuite(Testsuite object) {
			return createTestsuiteAdapter();
		}

		@Override
		public Adapter caseTestsuites(Testsuites object) {
			return createTestsuitesAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public JunitresultAdapterFactory() {
		if(modelPackage == null) {
			modelPackage = JunitresultPackage.eINSTANCE;
		}
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.AbstractAggregatedTest
	 * <em>Abstract Aggregated Test</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.AbstractAggregatedTest
	 * @generated
	 */
	public Adapter createAbstractAggregatedTestAdapter() {
		return null;
	}

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.Error <em>Error</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.Error
	 * @generated
	 */
	public Adapter createErrorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.Failure <em>Failure</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.Failure
	 * @generated
	 */
	public Adapter createFailureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.JunitResult <em>Junit Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.JunitResult
	 * @generated
	 */
	public Adapter createJunitResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.NegativeResult <em>Negative Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.NegativeResult
	 * @generated
	 */
	public Adapter createNegativeResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.Skipped <em>Skipped</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.Skipped
	 * @generated
	 */
	public Adapter createSkippedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.Testcase <em>Testcase</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.Testcase
	 * @generated
	 */
	public Adapter createTestcaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.Testrun <em>Testrun</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.Testrun
	 * @generated
	 */
	public Adapter createTestrunAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.Testsuite <em>Testsuite</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.Testsuite
	 * @generated
	 */
	public Adapter createTestsuiteAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.junitresult.Testsuites <em>Testsuites</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.junitresult.Testsuites
	 * @generated
	 */
	public Adapter createTestsuitesAdapter() {
		return null;
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if(object == modelPackage) {
			return true;
		}
		if(object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

} // JunitresultAdapterFactory
