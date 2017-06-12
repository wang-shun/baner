package com.ztkx.transplat.platformutil.log;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;
/**
 * @author zhangxiaoyun
 * 2016年1月13日 下午1:35:01
 * <p>Company:ztkx</p>
 */
public class CBPAYPatternParser extends PatternParser {
	public CBPAYPatternParser(String pattern) {
		super(pattern);
	}

	protected void finalizeConverter(char c) {
		PatternConverter pc = null;
		switch (c) {
		case 'f':
			pc = new FlowPatternConverter(formattingInfo);
			currentLiteral.setLength(0);
			addConverter(pc);
			break;
		default:
			super.finalizeConverter(c);
		}
	}

	private static class FlowPatternConverter extends PatternConverter {
		public FlowPatternConverter(FormattingInfo formattingInfo) {
			super(formattingInfo);
		}

		@Override
		protected String convert(LoggingEvent event) {
			return FlowNoContainer.getFlowNo();
		}
	}
}
