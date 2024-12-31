package com.exception.stock;

public class StockException extends Exception{
    
    StockInsuffisantException[] stock ;

    public StockInsuffisantException[] getStock() {
        return this.stock;
    }

    public void setStock(StockInsuffisantException[] stock) {
        this.stock = stock;
    }

    public StockException() {
        super();
        setStock(new StockInsuffisantException[0]);
    }

    public StockException(String message) {
        super(message);
        setStock(new StockInsuffisantException[0]);

    }

    public StockException(String message, StockInsuffisantException[] stock) {
        super(message);
        setStock(stock);
    }
}
