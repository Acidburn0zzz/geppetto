/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.junit.Test;

public class ModuleUtilsTest extends AbstractForgeTest {

	@Test
	public void loadModule() {
		try {
			Metadata md = getForge().createFromModuleDir(Activator.getTestData("puppetlabs-apache"), null, null);
			assertEquals("Unexpected module name", new ModuleName("puppetlabs-apache"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadModule2() {
		try {
			Metadata md = getForge().createFromModuleDir(Activator.getTestData("DavidSchmitt-collectd"), null, null);
			assertEquals("Unexpected module name", new ModuleName("davidschmitt-collectd"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadModule3() {
		try {
			Metadata md = getForge().createFromModuleDir(Activator.getTestData("bobsh-iptables"), null, null);
			assertEquals("Unexpected module name", new ModuleName("bobsh/iptables"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadModule4() {
		try {
			Metadata md = getForge().createFromModuleDir(Activator.getTestData("ghoneycutt-rsync"), null, null);
			assertEquals("Unexpected module name", new ModuleName("ghoneycutt-RSync"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadModule5() {
		try {
			Metadata md = getForge().createFromModuleDir(Activator.getTestData("lab42-common"), null, null);
			assertEquals("Unexpected module name", new ModuleName("lab42-common"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void parseModuleFile__File() {
		try {
			Metadata md = ModuleUtils.parseModulefile(Activator.getTestData("puppetlabs-apache/Modulefile"));
			assertEquals("Unexpected module name", new ModuleName("puppetlabs-apache"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

}