package dsg.rmi.auction;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface DSGAuctionEventHandler extends Remote {

	/**
	 * Notifies the event handler about an auction event.
	 *
	 * @param event The type of the event
	 * @param auction The auction
	 */
	public void handleEvent(DSGAuctionEventType event, DSGAuction auction) throws RemoteException;

}
