package dsg.rmi.auction;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface DSGAuctionService extends Remote {

	/**
	 * Registers a new auction.
	 *
	 * @param auction The auction to register duration Time in seconds until the auction ends
	 *        handler The handler to notify when the auction has ended
	 * @throws DSGAuctionException if the duration is negative or an auction with the same name
	 *         already exists
	 */
	public void registerAuction(DSGAuction auction, int duration, DSGAuctionEventHandler handler)
			throws DSGAuctionException, RemoteException;

	/**
	 * Retrieves all auctions currently in progress.
	 *
	 * @return The auctions currently in progress.
	 */
	public DSGAuction[] getAuctions() throws RemoteException;

	/**
	 * Places a new bid.
	 *
	 * @param userName The name of the bidder
	 * @param auctionName The name of the auction
	 * @param price The bid
	 * @param handler The handler to notify if another bid is placed that is higher than this bid
	 * @return <tt>true</tt> if the bid has been placed, otherwise <tt>false</tt>
	 * @throws DSGAuctionException if no auction with the specified name is currently in progress
	 */
	public boolean placeBid(String userName, String auctionName, int price,
			DSGAuctionEventHandler handler) throws DSGAuctionException, RemoteException;

}
