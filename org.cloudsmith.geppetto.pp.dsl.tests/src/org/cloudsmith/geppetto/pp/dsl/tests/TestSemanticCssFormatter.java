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
package org.cloudsmith.geppetto.pp.dsl.tests;

import java.util.EnumSet;
import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPStylesheetProvider;
import org.cloudsmith.geppetto.pp.dsl.ppformatting.PPIndentationInformation;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;
import org.cloudsmith.xtext.dommodel.RegionMatch;
import org.cloudsmith.xtext.dommodel.formatter.CSSDomFormatter;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor.CommentFormattingOptions;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentContainerInformation;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentContainerInformation.JavaLikeMLCommentContainer;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.serializer.DomBasedSerializer;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.cloudsmith.xtext.textflow.CharSequences.Fixed;
import org.cloudsmith.xtext.textflow.IMetrics;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.MeasuredTextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.cloudsmith.xtext.textflow.TextFlowRecording;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.IHiddenTokenSequencer;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.util.Triple;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;

/**
 * Tests CSS formatting using the new DomFormatter.
 * (TODO: Not generic - requires PP to have a grammar / rules to test).
 */
public class TestSemanticCssFormatter extends AbstractPuppetTests {
	public static class DebugFormatter extends CSSDomFormatter {

		@Inject
		public DebugFormatter(Provider<DomCSS> domProvider, DomNodeLayoutFeeder feeder) {
			super(domProvider, feeder);
		}

		@Override
		public ReplaceRegion format(IDomNode dom, ITextRegion regionToFormat, IFormattingContext formattingContext,
				Acceptor errors) {
			// System.err.println("TestSemanticCssFormatter.DebugFormatter");
			// System.err.println(DomModelUtils.compactDump(dom, true));
			return super.format(dom, regionToFormat, formattingContext, errors);
		}

	}

	public static class TestSetup extends PPTestSetup {
		public static class TestModule extends PPTestModule {

			@Override
			public void configure(Binder binder) {
				super.configure(binder);
				binder.bind(ISerializer.class).to(DomBasedSerializer.class);
				binder.bind(IDomModelFormatter.class).to(DebugFormatter.class);
				// Want serializer to insert empty WS even if there is no node model
				binder.bind(IHiddenTokenSequencer.class).to(
					org.cloudsmith.xtext.serializer.acceptor.HiddenTokenSequencer.class);

				// Bind the default style sheet (TODO: should use a test specific sheet)
				binder.bind(DomCSS.class).toProvider(PPStylesheetProvider.class);
				// specific to pp (2 spaces).
				binder.bind(IIndentationInformation.class).to(PPIndentationInformation.class);
				// binder.bind(IIndentationInformation.class).to(IIndentationInformation.Default.class);

				binder.bind(ILayoutManager.class).annotatedWith(Names.named("Default")).to(PPSemanticLayout.class);
			}
		}

		@Override
		public Injector createInjector() {
			return Guice.createInjector(Modules.override(new org.cloudsmith.geppetto.pp.dsl.PPRuntimeModule()).with(
				new TestModule()));
		}
	}

	private void appendSampleFlow(ITextFlow flow) {
		flow.appendText("123456");
		flow.appendBreak();
		flow.appendText("123456789");
		flow.changeIndentation(1);
		flow.appendBreak();
		flow.appendText("123");
		flow.changeIndentation(-1);
		flow.appendBreak();
		flow.appendBreak();
	}

	/**
	 * @param flow
	 */
	private void assertFlowOneLineNoBreak(IMetrics flow) {
		assertFalse("ends with break", flow.endsWithBreak());
		assertEquals("Height", 1, flow.getHeight());
		assertEquals("Last used indent", 0, flow.getLastUsedIndentation());
		assertEquals("Current indent", 0, flow.getIndentation());
		assertEquals("Width", 3, flow.getWidth());
		assertEquals("Width of last line", 3, flow.getWidthOfLastLine());
		assertFalse("Empty", flow.isEmpty());

	}

	/**
	 * @param flow
	 */
	private void assertSampleFlowMetrics(IMetrics flow) {
		assertTrue("ends with break notTrue", flow.endsWithBreak());
		assertEquals("Height", 4, flow.getHeight());
		assertEquals("Last used indent", 1, flow.getLastUsedIndentation());
		assertEquals("Current indent", 0, flow.getIndentation());
		assertEquals("Width", 9, flow.getWidth());
		assertEquals("Width of last line", 5, flow.getWidthOfLastLine());
		assertFalse("Not empty", flow.isEmpty());
	}

	@Override
	protected boolean shouldTestSerializer(XtextResource resource) {
		// true here (the default), just makes testing slower and it intermittently fails ?!?
		return false;
	}

	@Test
	public void test_CharSequences_trim() {
		assertEquals("Should have trimmed", "abc", CharSequences.trim("   abc   ", 3, 9).toString());
		assertEquals("Should have trimmed 1 left", "  abc", CharSequences.trim("   abc   ", 1, 9).toString());
		assertEquals("Should have trimmed all", "", CharSequences.trim("         ", 1, 9).toString());
		assertEquals("Empty string should trim to empty string", "", CharSequences.trim("", 1, 9).toString());
		assertEquals("Empty string should trim to single space", " ", CharSequences.trim("   ", 1, 1).toString());
	}

	@Test
	public void test_CommentProcessor() {
		CommentFormattingOptions options = new CommentFormattingOptions(
			new ICommentFormatterAdvice.DefaultCommentAdvice(), 80);
		JavaLikeMLCommentContainer in = new ICommentContainerInformation.JavaLikeMLCommentContainer(0);
		CommentProcessor cp = new CommentProcessor();
		String source = "/* the\nquick\n     *brown\n * fox\n   \n\n*/ ";
		IFormattingContext fmtCtx = get(IFormattingContext.class);
		TextFlow s = cp.formatComment(source, in, in, options, fmtCtx);
		String expected = "/* the\n * quick\n * brown\n * fox\n */ ";
		assertEquals("Should produce expected result", expected, s.getText().toString());
	}

	@Test
	public void test_CommentProcessor_bannerfolding() {
		CommentFormattingOptions options = new CommentFormattingOptions(
			new ICommentFormatterAdvice.DefaultCommentAdvice(), 24);
		JavaLikeMLCommentContainer in = new ICommentContainerInformation.JavaLikeMLCommentContainer(0);
		CommentProcessor cp = new CommentProcessor();

		String source = //
		"/*******************************************\n"//
				+ "* 0123456789 0123456789 0123456789 0123456789\n"//
				+ "* abc\n" //
				+ "* 0123456789 0123456789 0123456789 0123456789\n"//
				+ "*/";
		String expected = //
		"/***********************\n"//
				+ " * 0123456789 0123456789\n * 0123456789 0123456789\n"//
				+ " * abc\n" //
				+ " * 0123456789 0123456789\n * 0123456789 0123456789\n"//
				+ " */";
		IFormattingContext fmtCtx = get(IFormattingContext.class);
		TextFlow s = cp.formatComment(source, in, in, options, fmtCtx);
		assertEquals("Should produce expected result", expected, s.getText().toString());
	}

	@Test
	public void test_CommentProcessor_folding() {
		CommentFormattingOptions options = new CommentFormattingOptions(
			new ICommentFormatterAdvice.DefaultCommentAdvice(), 24);
		JavaLikeMLCommentContainer in = new ICommentContainerInformation.JavaLikeMLCommentContainer(0);
		CommentProcessor cp = new CommentProcessor();

		String source = //
		"/* 0123456789 0123456789 0123456789 0123456789\n"//
				+ "* abc\n" //
				+ "* 0123456789 0123456789 0123456789 0123456789\n"//
				+ "*/";
		String expected = //
		"/* 0123456789 0123456789\n * 0123456789 0123456789\n"//
				+ " * abc\n" //
				+ " * 0123456789 0123456789\n * 0123456789 0123456789\n"//
				+ " */";

		IFormattingContext fmtCtx = get(IFormattingContext.class);
		TextFlow s = cp.formatComment(source, in, in, options, fmtCtx);
		assertEquals("Should produce expected result", expected, s.getText().toString());
	}

	@Test
	public void test_CommentProcessor_folding_indent() {
		CommentFormattingOptions options = new CommentFormattingOptions(
			new ICommentFormatterAdvice.DefaultCommentAdvice(), 26);
		JavaLikeMLCommentContainer in = new ICommentContainerInformation.JavaLikeMLCommentContainer(0);
		CommentProcessor cp = new CommentProcessor();

		String source = //
		"/*   0123456789 0123456789 0123456789 0123456789\n"//
				+ "* abc\n" //
				+ "* 0123456789 0123456789 0123456789 0123456789\n"//
				+ "*/";
		String expected = //
		"/*   0123456789 0123456789\n *   0123456789 0123456789\n"//
				+ " * abc\n" //
				+ " * 0123456789 0123456789\n * 0123456789 0123456789\n"//
				+ " */";
		IFormattingContext fmtCtx = get(IFormattingContext.class);
		TextFlow s = cp.formatComment(source, in, in, options, fmtCtx);
		assertEquals("Should produce expected result", expected, s.getText().toString());
	}

	@Test
	public void test_CommentProcessor_Indented() {
		JavaLikeMLCommentContainer in = new ICommentContainerInformation.JavaLikeMLCommentContainer(2);
		CommentProcessor cp = new CommentProcessor();
		CommentFormattingOptions options = new CommentFormattingOptions(
			new ICommentFormatterAdvice.DefaultCommentAdvice(), 80);
		String source = "/* the\n  quick\n       *brown\n   * fox\n     \n  \n  */ ";
		IFormattingContext fmtCtx = get(IFormattingContext.class);
		TextFlow s = cp.formatComment(source, in, in, options, fmtCtx);
		// pad expected and result with 2 spaces to emulate the inserting of the result
		// (makes comparison look nicer if test fails)
		String expected = "  /* the\n   * quick\n   * brown\n   * fox\n   */ ";
		assertEquals("Should produce expected result", expected, "  " + CharSequences.trimLeft(s.getText()).toString());
	}

	@Test
	public void test_MeasuringTextStream() {
		MeasuredTextFlow flow = this.getInjector().getInstance(MeasuredTextFlow.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);
	}

	@Test
	public void test_MeasuringTextStreamEmpty() {
		MeasuredTextFlow flow = this.getInjector().getInstance(MeasuredTextFlow.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);
	}

	@Test
	public void test_MeasuringTextStreamOneLineNoBreak() {
		MeasuredTextFlow flow = this.getInjector().getInstance(MeasuredTextFlow.class);
		flow.appendText("123");
		assertFlowOneLineNoBreak(flow);
	}

	@Test
	public void test_Recording() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlowRecording.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);
	}

	@Test
	public void test_RecordingOneLineNoBreak() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlowRecording.class);
		flow.appendText("123");
		assertFlowOneLineNoBreak(flow);
	}

	@Test
	public void test_TextFlow() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlow.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);

	}

	@Test
	public void test_TextFlow_pendingIndent() {
		TextFlow flow = this.getInjector().getInstance(TextFlow.class);
		flow.changeIndentation(1);
		flow.appendBreaks(1);
		flow.appendText("123");
		assertEquals("\n  123", new StringBuilder(flow.getText()).toString());

	}

	@Test
	public void test_TextFlow_PendingMeasures() {
		TextFlow flow = this.getInjector().getInstance(TextFlow.class);
		flow.appendText("1234");
		assertEquals(4, flow.getWidthOfLastLine());
		assertEquals(1, flow.getHeight());
		flow.appendBreak();
		assertEquals(4, flow.getWidthOfLastLine());
		assertEquals(1, flow.getHeight());
		flow.changeIndentation(1);
		flow.appendBreak();
		flow.appendText("3");
		flow.appendText("45");
		assertEquals(5, flow.getWidthOfLastLine());
	}

	@Test
	public void test_TextFlowEmpty() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlow.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);
	}

	@Test
	public void test_TextFlowOneLineNoBreak() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlow.class);
		flow.appendText("123");
		assertFlowOneLineNoBreak(flow);
	}

	@Test
	public void test_TextIndentFirst() {
		TextFlow flow = this.getInjector().getInstance(TextFlow.class);
		flow.setIndentation(1);
		flow.setIndentFirstLine(true);
		flow.appendText("1234");
		assertEquals("  1234", new StringBuilder(flow.getText()).toString());

	}

	@Test
	public void test_TextLinewrap() {
		// default is 132 characters before a wrap and 0 wrap indent
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlow.class);
		Fixed stars = new CharSequences.Fixed('*', 22);
		for(int i = 0; i < 24; i++) {
			flow.appendText(stars);
			flow.appendSpaces(0);
		}
		assertEquals(4, flow.getHeight());
		assertEquals(132, flow.getWidth());

		flow = this.getInjector().getInstance(TextFlow.class);
		for(int i = 0; i < 24; i++) {
			flow.appendText(stars);
			flow.appendSpaces(1);
		}
		assertEquals(5, flow.getHeight());
		assertEquals(115, flow.getWidth());
	}

	@Test
	public void test_TextRecordingEmpty() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlowRecording.class);
		assertFalse("ends with break", flow.endsWithBreak());
		assertEquals("Height", 0, flow.getHeight());
		assertEquals("Last used indent", 0, flow.getLastUsedIndentation());
		assertEquals("Current indent", 0, flow.getIndentation());
		assertEquals("Width", 0, flow.getWidth());
		assertEquals("Width of last line", 0, flow.getWidthOfLastLine());
		assertTrue("Empty", flow.isEmpty());

	}

	@Test
	public void testBasicEnumSets() {
		// assert that containsAll of empty set is true
		Set<NodeType> none = EnumSet.noneOf(NodeType.class);
		Set<NodeType> ws = EnumSet.of(NodeType.WHITESPACE);
		assertTrue(ws.containsAll(none));
	}

	@Test
	public void testRegionMatcher_after() {
		RegionMatch match = new RegionMatch("abc", 5, new TextRegion(3, 2));
		assertEquals("Should be AFTER", RegionMatch.IntersectionType.AFTER, match.getIntersectionType());
		Triple<CharSequence, CharSequence, CharSequence> applied = match.apply();
		assertEquals("", applied.getFirst().toString());
		assertEquals("", applied.getSecond().toString());
		assertEquals("abc", applied.getThird().toString());
	}

	@Test
	public void testRegionMatcher_before() {
		RegionMatch match = new RegionMatch("abc", 0, new TextRegion(3, 2));
		assertEquals("Should be BEFORE", RegionMatch.IntersectionType.BEFORE, match.getIntersectionType());
		Triple<CharSequence, CharSequence, CharSequence> applied = match.apply();
		assertEquals("", applied.getFirst().toString());
		assertEquals("abc", applied.getSecond().toString());
		assertEquals("", applied.getThird().toString());
	}

	@Test
	public void testRegionMatcher_contained() {
		// left aligned in region
		RegionMatch match = new RegionMatch("abc", 0, new TextRegion(0, 5));
		assertEquals("Should be CONTAINED left", RegionMatch.IntersectionType.CONTAINED, match.getIntersectionType());
		Triple<CharSequence, CharSequence, CharSequence> applied = match.apply();
		assertEquals("abc", applied.getFirst().toString());
		assertEquals("", applied.getSecond().toString());
		assertEquals("", applied.getThird().toString());

		// "centered" in region
		match = new RegionMatch("abc", 2, new TextRegion(0, 6));
		assertEquals(
			"Should be CONTAINED centered", RegionMatch.IntersectionType.CONTAINED, match.getIntersectionType());
		applied = match.apply();
		assertEquals("abc", applied.getFirst().toString());
		assertEquals("", applied.getSecond().toString());
		assertEquals("", applied.getThird().toString());

		// right aligned in region
		match = new RegionMatch("abc", 2, new TextRegion(0, 5));
		assertEquals("Should be CONTAINED right", RegionMatch.IntersectionType.CONTAINED, match.getIntersectionType());
		applied = match.apply();
		assertEquals("abc", applied.getFirst().toString());
		assertEquals("", applied.getSecond().toString());
		assertEquals("", applied.getThird().toString());

	}

	@Test
	public void testRegionMatcher_firstPartInside() {
		RegionMatch match = new RegionMatch("abc", 4, new TextRegion(0, 5));
		assertEquals(
			"Should be FIRSTPART_INSIDE", RegionMatch.IntersectionType.FIRSTPART_INSIDE, match.getIntersectionType());
		Triple<CharSequence, CharSequence, CharSequence> applied = match.apply();
		assertEquals("a", applied.getFirst().toString());
		assertEquals("", applied.getSecond().toString());
		assertEquals("bc", applied.getThird().toString());

	}

	@Test
	public void testRegionMatcher_lastPartInside() {
		RegionMatch match = new RegionMatch("abc", 0, new TextRegion(2, 5));
		assertEquals(
			"Should be LASTPART_INSIDE", RegionMatch.IntersectionType.LASTPART_INSIDE, match.getIntersectionType());
		Triple<CharSequence, CharSequence, CharSequence> applied = match.apply();
		assertEquals("c", applied.getFirst().toString());
		assertEquals("ab", applied.getSecond().toString());
		assertEquals("", applied.getThird().toString());

	}

	@Test
	public void testRegionMatcher_midPartInside() {
		RegionMatch match = new RegionMatch("abc", 0, new TextRegion(1, 1));
		assertEquals(
			"Should be MIDPART_INSIDE", RegionMatch.IntersectionType.MIDPART_INSIDE, match.getIntersectionType());
		Triple<CharSequence, CharSequence, CharSequence> applied = match.apply();
		assertEquals("b", applied.getFirst().toString());
		assertEquals("a", applied.getSecond().toString());
		assertEquals("c", applied.getThird().toString());

	}
}
