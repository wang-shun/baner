package com.ztkx.transplat.platformutil.log;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class CBPAYPatternLayout extends PatternLayout {
	@Override
	protected PatternParser createPatternParser(String pattern){
		return new CBPAYPatternParser(pattern);
	}
}
