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
        this.bid = bid; // set bid to bid
        this.bidder = inet.getHostAddress(); // set bidder with highest bid
    }

    public String getItemName(){
        return this.name; // return item name
    }

    public double getBid(){
        return this.bid; // return item bid
    }

    public String getBidder(){
        return this.bidder; // return current bidder
    }

    // override toString method
    @Override
    public String toString() {
        // if there is a bidder for the item
        if(bidder != null){
            // string to display if there is a bidder
            return this.name + " : " + Double.toString(this.bid) + " : " + this.bidder;
        } else {
            // string to display if there is not a bidder for the item
            return this.name + " : " + Double.toString(this.bid) + " : <no bids>";
        }
    }
}