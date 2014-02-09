package com.adp.clocks.synergy2416.demo;

import com.adp.clocks.synergy2416.demo.ClockEventDispatcher.CLOCK_STATUS;

public interface ClockStatusListener {
	//This is actually similar to the Signal portion of QT's signal-slot idiom...
	public void clockStatusChanged(CLOCK_STATUS cs);
}
