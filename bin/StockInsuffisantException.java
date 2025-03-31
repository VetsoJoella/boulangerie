package com.exception.stock;

import com.model.stock.Stock;

public class StockInsuffisantException extends Exception{
    
    Stock stock ;

    public Stock getStock() {
        return this.stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public StockInsuffisantException() {
        super();
    }

    public StockInsuffisantException(String message) {
        super(message);
    }

    public StockInsuffisantException(String message, Stock stock) {
        super(message);
        setStock(stock);
    }
}
