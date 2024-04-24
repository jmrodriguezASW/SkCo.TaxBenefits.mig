package co.oldmutual.taxbenefit.util.web;

import co.oldmutual.taxbenefit.util.DataSourceWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private ServletContext context = null;

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        Map mapa    =   new HashMap();
        mapa.put(WebContract.JNDI, event.getServletContext().getInitParameter(WebContract.JNDI));
        mapa.put(WebContract.PROVIDER_URL, event.getServletContext().getInitParameter(WebContract.PROVIDER_URL));
        mapa.put(WebContract.INITIAL_CONTEXT_FACTORY, event.getServletContext().getInitParameter(WebContract.INITIAL_CONTEXT_FACTORY));
        
        DataSourceWrapper.getInstance(mapa);
    }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.getServletContext();
    }
}
