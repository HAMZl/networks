import java.io.*;
import java.net.*;

public class BidItem {
    private String name; // item name
    private double bid; // item bid
    private String bidder; // item bidder
    
    // constructor
    public BidItem(String name){
        this.name = name; // set item name
        this.bid = 0.0; // set item bid to 0.0
        bidder = null; // no bidders
    }

    public void setBidder(double bid, InetAddress inet){
        this.bid = bid;
        this.bidder = inet.getHostAddress();
    }

    public String getItemName(){
        return this.name;
    }

    public double getBid(){
        return this.bid;
    }

    public String getBidder(){
        return this.bidder;
    }

    // override toString method
    @Override
    public String toString() {
        if(bidder != null){
            // string to display if there is a bidder
            return this.name + " : " + Double.toString(this.bid) + " : " + this.bidder;
        } else {
            // string to display if there is not a bidder for the item
            return this.name + " : " + Double.toString(this.bid) + " : <no bids>";
        }
    }
}