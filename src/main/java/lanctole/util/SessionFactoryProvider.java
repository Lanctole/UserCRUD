package lanctole.util;

import lanctole.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
public class SessionFactoryProvider {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration();
            config.addAnnotatedClass(User.class);
            log.debug("Configuring Hibernate SessionFactory");
            return config.buildSessionFactory();
        } catch (HibernateException ex) {
            log.error("Initial SessionFactory creation failed: {}", String.valueOf(ex));
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
            log.debug("SessionFactory closed");
        }
    }
}
