package webserver.util;

import webserver.util.IObservable;

public interface IObserver 
{
    public void update(IObservable src, Object update);
}