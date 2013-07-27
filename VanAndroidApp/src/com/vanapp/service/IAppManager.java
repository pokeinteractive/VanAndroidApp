package com.vanapp.service;


public interface IAppManager {
	

	public void stopGPSLocation() ;
	public void sendGPSLocaiton(String driverPhone) ;

	public boolean isRunningGPSSender() ;
	
	public void exitAll();
	
}
