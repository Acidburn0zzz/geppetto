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
package com.puppetlabs.geppetto.pp.dsl;

import java.util.Map;

import com.puppetlabs.geppetto.pp.dsl.pptp.PptpRubyRuntimeModule;
import com.puppetlabs.geppetto.pp.dsl.pptp.PptpRuntimeModule;
import com.puppetlabs.geppetto.ruby.resource.PptpRubyResourceFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Initialization support for running Xtext languages
 * without equinox extension registry
 */
public class PPStandaloneSetup extends PPStandaloneSetupGenerated {

	public static void doSetup() {
		new PPStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	private Injector pptpRubyInjector;

	private Injector pptpInjector;

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// These must be registered when running in a non-OSGi environment
		Registry registry = EPackage.Registry.INSTANCE;
		if(!registry.containsKey(org.eclipse.xtext.XtextPackage.eNS_URI))
			registry.put(org.eclipse.xtext.XtextPackage.eNS_URI, org.eclipse.xtext.XtextPackage.eINSTANCE);

		if(!registry.containsKey(com.puppetlabs.geppetto.pp.PPPackage.eNS_URI))
			registry.put(com.puppetlabs.geppetto.pp.PPPackage.eNS_URI, com.puppetlabs.geppetto.pp.PPPackage.eINSTANCE);

		if(!registry.containsKey(com.puppetlabs.geppetto.pp.pptp.PPTPPackage.eNS_URI))
			registry.put(
				com.puppetlabs.geppetto.pp.pptp.PPTPPackage.eNS_URI,
				com.puppetlabs.geppetto.pp.pptp.PPTPPackage.eINSTANCE);

		Map<String, Object> factoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
		if(!factoryMap.containsKey("pptp"))
			factoryMap.put("pptp", new org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl());

		if(!factoryMap.containsKey("rb"))
			factoryMap.put("rb", new PptpRubyResourceFactory());

		Injector mainInjector = super.createInjectorAndDoEMFRegistration();

		doPptpSetup();
		return mainInjector;
	}

	public void doPptpSetup() {
		// two injectors needed - one for pptp model, and one for ruby
		pptpRubyInjector = Guice.createInjector(getPptpRubyModule());
		pptpInjector = Guice.createInjector(getPptpModule());

		// register rb
		// expects the PptpRubyResourceFactory to have been registered via a Eclipse Extension
		// register the resource service provider (in a UI scnario this is registered by the Activator)
		org.eclipse.xtext.resource.IResourceServiceProvider pptpRubyServiceProvider = pptpRubyInjector.getInstance(org.eclipse.xtext.resource.IResourceServiceProvider.class);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(
			"rb", pptpRubyServiceProvider);
		// register pptp
		// Expect the pptp resource factory (a default XMI resource) to be available by default
		// register the resource service provider (in a UI scenario this is registered by the Activator).
		org.eclipse.xtext.resource.IResourceServiceProvider pptpServiceProvider = pptpInjector.getInstance(org.eclipse.xtext.resource.IResourceServiceProvider.class);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(
			"pptp", pptpServiceProvider);
	}

	public Injector getPptpInjector() {
		return pptpInjector;
	}

	protected Module getPptpModule() {
		return new PptpRuntimeModule();
	}

	public Injector getPptpRubyInjector() {
		return pptpRubyInjector;
	}

	protected Module getPptpRubyModule() {
		return new PptpRubyRuntimeModule();
	}

}
