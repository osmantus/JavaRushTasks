package com.javarush.task.task32.task3212;

import com.javarush.task.task32.task3212.contex.InitialContext;
import com.javarush.task.task32.task3212.service.Service;
import com.javarush.task.task32.task3212.service.impl.EJBServiceImpl;
import com.javarush.task.task32.task3212.service.impl.JMSServiceImpl;


public class ServiceLocator {
    private static Cache cache;

    static {
        cache = new Cache();
    }

    /**
     * First check the service object available in cache
     * If service object not available in cache do the lookup using
     * JNDI initial context and get the service object. Add it to
     * the cache for future use.
     *
     * @param jndiName The name of service object in context
     * @return Object mapped to name in context
     */
    public static Service getService(String jndiName) {

        Service service = cache.getService(jndiName);
        if (service == null)
        {
            InitialContext context = new InitialContext();
            Object serviceObj = context.lookup(jndiName);

            if (serviceObj != null)
            {
                if (serviceObj instanceof JMSServiceImpl)
                    service = (JMSServiceImpl) serviceObj;
                else if (serviceObj instanceof EJBServiceImpl)
                    service = (EJBServiceImpl) serviceObj;
                else
                    service = (Service) serviceObj;

                if (service != null)
                    cache.addService(service);
            }
        }
        return service;
    }
}
