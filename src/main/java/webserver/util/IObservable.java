package webserver.util;

import webserver.util.IObserver;

public interface IObservable
{
    public void addObserver(IObserver observer);
    public void removeObserver(IObserver observer);
    public void notifyObservers(Object update);
}