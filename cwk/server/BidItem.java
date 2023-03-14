import java.io.*;
import java.net.*;

public class BidItem {
    private String name;
    private double bid;
    private String bidder;
    
    public BidItem(String name){
        this.name = name;
        this.bid = 0.0;
        bidder = null;
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

    @Override
    public String toString() {
        if(bidder != null){
            return this.name + " : " + Double.toString(this.bid) + " : " + this.bidder;
        } else {
            return this.name + " : " + Double.toString(this.bid) + " : <no bids>";
        }
    }
}