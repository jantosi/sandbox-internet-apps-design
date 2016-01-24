package pl.lodz.p.ftims.pai.service;

/**
 * Created by antosikj (Jakub Antosik) on 21/01/16.
 */
public class DbSynchronizationException extends Exception {

    public DbSynchronizationException(){
        super();
    }

    public DbSynchronizationException(String msg) {
        super(msg);
    }

    public DbSynchronizationException(String msg, Throwable cause){
        super(msg, cause);
    }
}
